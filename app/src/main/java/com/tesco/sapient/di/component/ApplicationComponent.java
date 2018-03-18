package com.tesco.sapient.di.component;

import android.app.Application;
import android.content.Context;

import com.tesco.sapient.MyApplication;
import com.tesco.sapient.db.DatabaseHandler;
import com.tesco.sapient.di.AppScope;
import com.tesco.sapient.di.ApplicationContext;
import com.tesco.sapient.di.module.ApplicationModule;
import com.tesco.sapient.db.DataManager;

import dagger.Component;

/**
 * Application Component
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
@AppScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MyApplication myApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    DatabaseHandler getDbHelper();
}
