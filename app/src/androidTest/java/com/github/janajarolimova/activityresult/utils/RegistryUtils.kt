package com.github.janajarolimova.activityresult.utils

import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityOptionsCompat

fun <O> getActivityResultRegistry(expectedResult: O): ActivityResultRegistry {
    return object : ActivityResultRegistry() {
        override fun <I, O> onLaunch(
            requestCode: Int,
            contract: ActivityResultContract<I, O>,
            input: I,
            options: ActivityOptionsCompat?
        ) {
            dispatchResult(requestCode, expectedResult)
        }
    }
}