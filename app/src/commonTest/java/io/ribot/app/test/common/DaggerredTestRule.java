package io.ribot.app.test.common;

import android.accounts.AccountManager;
import android.content.Context;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.ribot.app.RibotApplication;
import io.ribot.app.data.DataManager;
import io.ribot.app.test.common.injection.component.DaggerTestComponent;
import io.ribot.app.test.common.injection.component.TestComponent;
import io.ribot.app.test.common.injection.module.ApplicationTestModule;

/**
 * ここで、 Dagger の Component と 契約を交わす。
 * Test クラスは Rule を通して OBJ グラフからインスタンスを取り出す
 */
public class DaggerredTestRule implements TestRule {

    // メフィストフェレス
    private TestComponent mTestComponent;
    private Context mContext;

    public DaggerredTestRule(Context context) {
        mContext = context;
    }

  private void setupDaggerTestComponentInApplication() {

    RibotApplication application = RibotApplication.get(mContext);

    // ここに、契約を交わす
    mTestComponent = DaggerTestComponent.builder()
        // モジュール
        .applicationTestModule(new ApplicationTestModule(application))
        .build();
    application.setComponent(mTestComponent);
  }

    public Context getContext() {
        return mContext;
    }

    // OBJグラフからインスタンスを取り出す
    public AccountManager getMockAccountManager() {
        return mTestComponent.accountManager();
    }

    public DataManager getMockDataManager() {
        return mTestComponent.dataManager();
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };
    }
}
