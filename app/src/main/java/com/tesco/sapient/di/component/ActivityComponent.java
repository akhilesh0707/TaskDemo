package com.tesco.sapient.di.component;

import com.tesco.sapient.di.module.ActivityModule;
import com.tesco.sapient.di.PerActivity;
import com.tesco.sapient.login.LoginActivityActivity;
import com.tesco.sapient.main.MainActivity;

import dagger.Component;

/**
 * Activity Component
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivityActivity loginActivity);

    void inject(MainActivity mainActivity);

}