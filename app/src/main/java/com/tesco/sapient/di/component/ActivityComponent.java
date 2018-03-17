package com.tesco.sapient.di.component;

import com.tesco.sapient.di.module.ActivityModule;
import com.tesco.sapient.di.PerActivity;
import com.tesco.sapient.login.LoginActivity;
import com.tesco.sapient.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

}