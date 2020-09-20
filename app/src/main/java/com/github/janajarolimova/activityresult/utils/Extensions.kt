package com.github.janajarolimova.activityresult.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment


/**
 * Calls the appropriate [Fragment.registerForActivityResult], depending on whether [registry]
 * is null or not.
 */
fun <I, O> Fragment.registerForActivityResultInternal(
    contract: ActivityResultContract<I, O>,
    registry: ActivityResultRegistry?,
    callback: ActivityResultCallback<O>
) : ActivityResultLauncher<I> {
    return if (registry != null) {
        registerForActivityResult(contract, registry, callback)
    } else {
        registerForActivityResult(contract, callback)
    }
}

fun Uri.createDocument(context: Context) {
    with(context) {
        val cr = contentResolver.query(this@createDocument, null, null, null, null)?.apply {
            moveToFirst()
        }
        val fileName = cr?.getString(cr.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        cr?.close()
        Toast.makeText(
            this,
            "Created document with name: $fileName at path: ${this@createDocument.path}",
            Toast.LENGTH_LONG
        ).show()
    }
}