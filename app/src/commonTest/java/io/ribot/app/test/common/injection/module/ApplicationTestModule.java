package io.ribot.app.test.common.injection.module;

import static org.mockito.Mockito.mock;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import io.ribot.app.data.DataManager;
import io.ribot.app.data.remote.RibotService;
import io.ribot.app.injection.ApplicationContext;
import javax.inject.Singleton;

/**
 * Provides application-level dependencies for an app
 * running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {

    protected final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

    /************* MOCKS *************/

    // Module 内でモックした上で、 OBJ グラフに組み込む
    @Provides
    @Singleton
    DataManager providesDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    RibotService provideRibotService() {
        return mock(RibotService.class);
    }

    @Provides
    @Singleton
    AccountManager provideAccountManager() {
        return mock(AccountManager.class);
    }
}
