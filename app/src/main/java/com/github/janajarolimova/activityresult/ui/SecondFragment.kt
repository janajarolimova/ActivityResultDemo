package com.github.janajarolimova.activityresult.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.github.janajarolimova.activityresult.databinding.FragmentSecondBinding

const val REQUEST_KEY = "request_key"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReturnResult.setOnClickListener {
            setFragmentResult(REQUEST_KEY, bundleOf("data" to binding.textInput.text.toString()))
            findNavController().navigateUp()
        }

        binding.buttonGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}