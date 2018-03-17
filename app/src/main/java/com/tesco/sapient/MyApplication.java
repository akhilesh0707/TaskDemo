package com.tesco.sapient;

import android.app.Application;
import android.content.Context;

import com.tesco.sapient.di.component.ApplicationComponent;
import com.tesco.sapient.di.component.DaggerApplicationComponent;
import com.tesco.sapient.di.module.ApplicationModule;
import com.tesco.sapient.db.DataManager;

import javax.inject.Inject;

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    protected ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

}