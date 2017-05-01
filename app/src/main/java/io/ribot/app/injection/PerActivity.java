package io.ribot.app.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the DataManager to be memorised in the
 * correct component.
 *
 * Scope は アノテートした Component と 生成/廃棄 をともにする。
 * Module の @Provides にアノテートすることで対象インスタンスとなる。
 * Scope なインスタンスを使うには、コンシューマのインターフェイスにアノテートする
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
