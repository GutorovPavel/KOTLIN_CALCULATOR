package com.example.lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.lab3.databinding.FragmentScientificFunctionsBinding

class ScientificFunctionsFragment : Fragment() {

    lateinit var binding : FragmentScientificFunctionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScientificFunctionsBinding.inflate(inflater, container, false)

        binding.buttonSin.setOnClickListener {
            val result = "sin("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonCos.setOnClickListener {
            val result = "cos("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonTan.setOnClickListener {
            val result = "tan("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonASin.setOnClickListener {
            val result = "asin("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonACos.setOnClickListener {
            val result = "acos("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonATan.setOnClickListener {
            val result = "atan("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonLn.setOnClickListener {
            val result = "ln("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonLog2.setOnClickListener {
            val result = "log2("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonLog10.setOnClickListener {
            val result = "log("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonExp.setOnClickListener {
            val result = "exp("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonSqrt.setOnClickListener {
            val result = "sqrt("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonAbs.setOnClickListener {
            val result = "abs("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonPower.setOnClickListener {
            val result = "^("
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonPi.setOnClickListener {
            var result = "pi"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        binding.buttonFact.setOnClickListener {
            var result = "!"
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
        return binding.root
    }

    companion object {
        fun newInstance() = ScientificFunctionsFragment()
    }
}