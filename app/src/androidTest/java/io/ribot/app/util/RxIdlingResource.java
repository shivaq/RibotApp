package io.ribot.app.util;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

import timber.log.Timber;


/**
 * 本クラスの使われ方
 * 本クラスは、アイドル状態になったかどうか カウンターのようなもの。
 *
 * このカウンターを使うクラスにて本クラスをインスタンス化し、
 * Espresso.registerIdlingResources() に引数として渡せば、
 * アイドル状態になった際に、Espresso にその旨通知される
 */
public class RxIdlingResource implements IdlingResource {

    //AtomicInteger →対象変数を atomic にしておくと、increment して test するという処理が、
    // 事実上 一つの（atomic な）処理のように実行される。
    private final AtomicInteger mActiveSubscriptionsCount = new AtomicInteger(0);
    // ResourceCallback →IdlingResource によって、
    // Espresso に アイドル状態に遷移した旨通知するために登録される。
    private ResourceCallback mResourceCallback;

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return mActiveSubscriptionsCount.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }

    // 現在1名様使用中でございます、みたいな感じ。
    public void incrementActiveSubscriptionsCount() {
        int count = mActiveSubscriptionsCount.incrementAndGet();
        Timber.i("Active subscriptions count increased to %d", count);
    }

    // 現在、1名様使用終了されました、みたいな感じ。
    public void decrementActiveSubscriptionsCount() {
        int count = mActiveSubscriptionsCount.decrementAndGet();
        Timber.i("Active subscriptions count decreased to %d", count);
        // 誰も使ってない？ならばアイドルだ、みたいな感じ。
        if (isIdleNow()) {
            Timber.i("There is no active subscriptions, transitioning to Idle");
            // Espresso に アイドル状態に遷移した旨通知する
            mResourceCallback.onTransitionToIdle();
        }
    }
}
