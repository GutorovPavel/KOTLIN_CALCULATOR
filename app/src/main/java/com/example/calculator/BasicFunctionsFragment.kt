package com.example.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.calculator.databinding.FragmentBasicFunctionsBinding


class BasicFunctionsFragment : Fragment() {

    lateinit var binding : FragmentBasicFunctionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBasicFunctionsBinding.inflate(inflater, container, false)

        binding.button1.setOnClickListener {
            val result = "1"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button2.setOnClickListener {
            val result = "2"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button3.setOnClickListener {
            val result = "3"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button4.setOnClickListener {
            val result = "4"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button5.setOnClickListener {
            val result = "5"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button6.setOnClickListener {
            val result = "6"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button7.setOnClickListener {
            val result = "7"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button8.setOnClickListener {
            val result = "8"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button9.setOnClickListener {
            val result = "9"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.button0.setOnClickListener {
            val result = "0"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonPoint.setOnClickListener {
            val result = "."
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonDelete.setOnClickListener {
            val result = "delete"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonDelete.setOnLongClickListener {
            val result = "clear"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
            return@setOnLongClickListener true
        }
        binding.buttonAdd.setOnClickListener {
            val result = "+"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonSubstract.setOnClickListener {
            val result = "-"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonMultiply.setOnClickListener {
            val result = "*"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonDivide.setOnClickListener {
            val result = "/"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonLBracket.setOnClickListener {
            val result = "("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonRBracket.setOnClickListener {
            val result = ")"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonCount.setOnClickListener {
            var result = "="
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }

        return binding.root
    }

    companion object {
        fun newInstance() = BasicFunctionsFragment()
    }
}