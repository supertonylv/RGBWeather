package com.info.tony.rgbweather.di.scope

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * Created by lvlu on 2018/3/14.
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class ActivityScoped