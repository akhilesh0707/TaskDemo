package com.tesco.sapient;

import android.app.Application;
import android.content.Context;

import com.tesco.sapient.di.component.ApplicationComponent;
import com.tesco.sapient.di.component.DaggerApplicationComponent;
import com.tesco.sapient.di.module.ApplicationModule;
import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.dto.UserDTO;

import javax.inject.Inject;

/**
 * Application class
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
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

    /**
     * ApplicationComponent to DI
     *
     * @return
     */
    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    /**
     * User object
     *
     * @return
     */
    public UserDTO getUser() {
        return userDTO;
    }

    /**
     * Set User or Initialize user object
     *
     * @param userDTO
     */
    public void setUser(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    /**
     * Get Application object
     *
     * @param context
     * @return
     */
    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

}