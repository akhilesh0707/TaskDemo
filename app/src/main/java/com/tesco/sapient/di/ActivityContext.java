package com.tesco.sapient.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Activity Context
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}