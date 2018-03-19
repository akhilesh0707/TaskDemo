package com.tesco.sapient.di.component;

import com.tesco.sapient.di.module.ActivityModule;
import com.tesco.sapient.di.PerActivity;
import com.tesco.sapient.login.LoginActivity;
import com.tesco.sapient.waste.WasteActivity;

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

    void inject(LoginActivity loginActivityActivity);

    void inject(WasteActivity wasteActivity);

}