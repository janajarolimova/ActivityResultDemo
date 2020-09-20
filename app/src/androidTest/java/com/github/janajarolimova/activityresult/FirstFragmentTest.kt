package com.github.janajarolimova.activityresult

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.launch
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.janajarolimova.activityresult.ui.FirstFragment
import com.github.janajarolimova.activityresult.ui.Food
import com.github.janajarolimova.activityresult.utils.getActivityResultRegistry
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FirstFragmentTest : TestCase() {

    @Test
    fun testTakePictureForResult() {
        // When
        val expectedResult = Bitmap.createBitmap(1, 1, Bitmap.Config.RGBA_F16)
        val testRegistry = getActivityResultRegistry(expectedResult)

        with(launchFragmentInContainer {
            FirstFragment(
                testRegistry
            )
        }) {
            onFragment { fragment ->
                // Then
                fragment.takePicture.launch()
                // Verify
                assertThat(fragment.resultData).isSameInstanceAs(expectedResult)
            }
        }
    }

    @Test
    fun testSelectPictureForResult() {
        // When
        val expectedResult = Uri.parse("test.jpg")
        val testRegistry = getActivityResultRegistry(expectedResult)

        with(launchFragmentInContainer {
            FirstFragment(
                testRegistry
            )
        }) {
            onFragment { fragment ->
                // Then
                fragment.selectPicture.launch("image/*")
                // Verify
                assertThat(fragment.resultData).isSameInstanceAs(expectedResult)
            }
        }
    }

    @Test
    fun testRequestPermissions() {
        // When
        val expectedResult = mapOf(
            "android.permission.READ_EXTERNAL_STORAGE" to true,
            "android.permission.ACCESS_FINE_LOCATION" to false
        )
        val testRegistry = getActivityResultRegistry(expectedResult)

        with(launchFragmentInContainer {
            FirstFragment(
                testRegistry
            )
        }) {
            onFragment { fragment ->
                // Then
                fragment.requestPermissions.launch(
                    listOf(
                        "android.permission.READ_EXTERNAL_STORAGE",
                        "android.permission.ACCESS_FINE_LOCATION"
                    ).toTypedArray()
                )
                // Verify
                assertThat(fragment.resultData).isSameInstanceAs(expectedResult)
            }
        }
    }

    @Test
    fun testCreateDocument() {
        // When
        val expectedResult = Uri.parse("file.txt")
        val testRegistry = getActivityResultRegistry(expectedResult)

        with(launchFragmentInContainer {
            FirstFragment(
                testRegistry
            )
        }) {
            onFragment { fragment ->
                // Then
                fragment.createDocumentResult.launch("file.txt")
                // Verify
                assertThat(fragment.resultData).isSameInstanceAs(expectedResult)
            }
        }
    }

    @Test
    fun testCustomFoodItemContract() {
        // When
        val expectedResult = Food("Banana")
        val testRegistry = getActivityResultRegistry(expectedResult)

        with(launchFragmentInContainer {
            FirstFragment(
                testRegistry
            )
        }) {
            onFragment { fragment ->
                // Then
                fragment.customActivityResult.launch()
                // Verify
                assertThat(fragment.resultData).isEqualTo(expectedResult)
            }
        }
    }
}