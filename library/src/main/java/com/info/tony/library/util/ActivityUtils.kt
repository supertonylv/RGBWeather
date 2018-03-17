package com.info.tony.library.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by lvlu on 2018/3/13.
 */
object ActivityUtils {
    fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }
}