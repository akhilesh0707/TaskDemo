package com.tesco.sapient.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Single Activity DI Scope
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}