package com.github.janajarolimova.activityresult.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.github.janajarolimova.activityresult.contracts.EXTRA_FOOD_ITEM
import com.github.janajarolimova.activityresult.contracts.GetFoodItemFromActivity
import com.github.janajarolimova.activityresult.databinding.ActivityFoodPickerBinding
import java.io.Serializable

/**
 * Activity of buttons with various food names, whose button text as [Food] is sent back to calling
 * activity with the [GetFoodItemFromActivity] contract.
 */
class FoodPickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            for (view in container.children) {
                if (view is Button) {
                    view.setOnClickListener {
                        Intent().apply {
                            setResult(Activity.RESULT_OK, this)
                            putExtra(EXTRA_FOOD_ITEM,
                                Food(
                                    view.text.toString()
                                )
                            )
                        }
                        finish()
                    }
                }
            }
        }
    }
}

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class Food(val food: String): Serializable