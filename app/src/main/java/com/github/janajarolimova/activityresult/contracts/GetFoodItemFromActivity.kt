package com.github.janajarolimova.activityresult.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.github.janajarolimova.activityresult.ui.Food
import com.github.janajarolimova.activityresult.ui.FoodPickerActivity

const val EXTRA_FOOD_ITEM = "extra_food_item"

/**
 *
 */
class GetFoodItemFromActivity : ActivityResultContract<Void?, Food?>() {

    override fun createIntent(context: Context, input: Void?) =
        Intent(context, FoodPickerActivity::class.java)

    override fun parseResult(resultCode: Int, result: Intent?) : Food? {
        /*if (resultCode != Activity.RESULT_OK) {
            return null
        }*/
        return result?.getSerializableExtra(EXTRA_FOOD_ITEM) as Food
    }
}