package com.tesco.sapient;

import android.app.Application;
import android.content.Context;

import com.tesco.sapient.di.component.ApplicationComponent;
import com.tesco.sapient.di.component.DaggerApplicationComponent;
import com.tesco.sapient.di.module.ApplicationModule;
import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.dto.UserDTO;

import javax.inject.Inject;

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    protected ApplicationComponent applicationComponent;
    protected UserDTO userDTO = null;
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

    public UserDTO getUser() {
        return userDTO;
    }

    public void setUser(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

}