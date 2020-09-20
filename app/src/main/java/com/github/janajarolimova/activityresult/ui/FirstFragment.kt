@file:Suppress("KDocUnresolvedReference")

package com.github.janajarolimova.activityresult.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.github.janajarolimova.activityresult.R
import com.github.janajarolimova.activityresult.contracts.GetFoodItemFromActivity
import com.github.janajarolimova.activityresult.databinding.FragmentFirstBinding
import com.github.janajarolimova.activityresult.utils.createDocument
import com.github.janajarolimova.activityresult.utils.getDrawable
import com.github.janajarolimova.activityresult.utils.registerForActivityResultInternal

/**
 * [Fragment] to demonstrate usages of Jetpack's new Activity Result APIs.
 * [resultRegistry] parameter is used for testing purposes, to override the .
 */
class FirstFragment(resultRegistry: ActivityResultRegistry? = null) : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    /**
     * Stores result returned from last request. Used here just for testing purposes.
     */
    var resultData: Any? = null

    /**
     * Request for multiple permissions, alternatively you can use [ActivityResultContracts.RequestPermission]
     * for one single permission request.
     */
    val requestPermissions =
        registerForActivityResultInternal(
            ActivityResultContracts.RequestMultiplePermissions(),
            resultRegistry,
            ActivityResultCallback { permissions ->
                resultData = permissions
                StringBuilder().apply {
                    for (permission in permissions) {
                        append(permission.key)
                        append(": ")
                        append(if (permission.value) "granted\n" else "not granted\n")
                    }
                }.also {
                    Toast.makeText(
                        requireContext(),
                        it.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

    /**
     * Launcher which opens camera to take picture and returns a bitmap as a result.
     */
    val takePicture =
        registerForActivityResultInternal(
            ActivityResultContracts.TakePicturePreview(),
            resultRegistry,
            ActivityResultCallback { bitmap: Bitmap? ->
                resultData = bitmap
                bitmap?.let {
                    setImageResult(
                        TypeBitmap(
                            bitmap
                        )
                    )
                } ?: Toast.makeText(requireContext(), "No result", Toast.LENGTH_LONG).show()
            })

    /**
     * Request for content of specified mime type defined in launch() when the result process is started.
     */
    val selectPicture =
        registerForActivityResultInternal(
            ActivityResultContracts.GetContent(),
            resultRegistry,
            ActivityResultCallback { uri ->
                resultData = uri
                uri?.getDrawable(requireContext())?.let {
                    setImageResult(
                        TypeDrawable(
                            it
                        )
                    )
                } ?: Toast.makeText(requireContext(), "No result", Toast.LENGTH_LONG).show()
            })

    /**
     * Launcher for user to select path for new document to be created.
     */
    val createDocumentResult =
        registerForActivityResultInternal(
            ActivityResultContracts.CreateDocument(),
            resultRegistry,
            ActivityResultCallback { uri ->
                resultData = uri
                uri?.createDocument(requireContext()) ?: Toast.makeText(
                    requireContext(),
                    "Nothing selected",
                    Toast.LENGTH_LONG
                )
                    .show()
            })

    /**
     * Launcher created with custom contract whose result is a nullable [Food] from [FoodPickerActivity].
     * [StartActivityForResult] could also be used here, but it would not provide type safety.
     */
    val customActivityResult =
        registerForActivityResultInternal(
            GetFoodItemFromActivity(),
            resultRegistry,
            ActivityResultCallback { foodItem ->
                resultData = foodItem
                Toast.makeText(requireContext(), "${foodItem?.food}", Toast.LENGTH_LONG).show()
            })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStartActivityForResult.setOnClickListener {
            // ActivityResultContracts.StartActivityForResult() cannot be used with the navigation component.
            // Data between fragments can be shared with a common viewmodel which is scoped to the activity,
            // or else by setting this result listener
            setFragmentResultListener(REQUEST_KEY) { key, bundle ->
                Toast.makeText(context, "$key: $bundle", Toast.LENGTH_LONG).show()
            }
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonPermissionRequest.setOnClickListener {
            // input is array of permissions to ask for
            requestPermissions.launch(
                listOf(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.ACCESS_FINE_LOCATION"
                ).toTypedArray()
            )
        }

        binding.buttonTakePicture.setOnClickListener {
            takePicture.launch()
        }

        binding.buttonSelectPhoto.setOnClickListener {
            // input is mime type user can select from
            selectPicture.launch("image/*")
        }

        binding.buttonCreateDocument.setOnClickListener {
            // input is name for new document
            createDocumentResult.launch("new_file.txt")
        }

        binding.buttonCustomIntentContract.setOnClickListener {
            customActivityResult.launch()
        }
    }

    private fun setImageResult(image: ImageType) {
        if (isAdded) {
            binding.resultContainer.removeAllViews()
            binding.resultContainer.addView(ImageView(requireContext()).apply {
                when (image) {
                    is TypeBitmap -> setImageBitmap(image.bitmap)
                    is TypeDrawable -> setImageDrawable(image.drawable)
                }
            })
        }
    }
}

sealed class ImageType
data class TypeBitmap(val bitmap: Bitmap) : ImageType()
data class TypeDrawable(val drawable: Drawable) : ImageType()