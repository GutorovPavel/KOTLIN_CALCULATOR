package com.example.lab3

//import org.mariuszgromada.math.mxparser.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.lab3.databinding.FragmentWorkSpaceBinding
import com.mpobjects.bdparsii.eval.Parser
import com.mpobjects.bdparsii.eval.Scope
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

fun fact(num: Int): BigDecimal? {
    if (num.equals(0)) return BigDecimal.ONE
    return fact(num - 1)?.multiply(num.toBigDecimal())
}

class WorkSpaceFragment : Fragment() {

    lateinit var binding: FragmentWorkSpaceBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentWorkSpaceBinding.inflate(inflater, container,false)

        val signs: String = "+-/*^"

        setFragmentResultListener("requestKey") { key, bundle ->
            val number = bundle.getString("bundleKey")
            var changeText = StringBuilder(binding.inputText.text.toString())
            var positionStart = binding.inputText.selectionStart
            var position = binding.inputText.selectionEnd

            if (number == "clear") {
                if (binding.inputText.length() == 0) binding.prevText.setText("")
                binding.inputText.text.clear()
                return@setFragmentResultListener
            }
            else if (number == "delete") {
                if (position != 0 && (positionStart != position)) {
                    changeText = changeText.delete(positionStart, position)
                    binding.inputText.setText(changeText)
                    binding.inputText.setSelection(positionStart)
                }
                else if (position != 0) {
                    changeText = changeText.deleteAt(position - 1)
                    binding.inputText.setText(changeText)
                    binding.inputText.setSelection(position - 1)
                }
                if (binding.inputText.text.isEmpty()) {
                    return@setFragmentResultListener
                }
                else if (binding.inputText.text[0] == '.')
                {
                    binding.inputText.setText("0${binding.inputText.text}")
                }
            }
            else if (number == ".") {
                changeText = changeText.apply { insert(position, number) }
                val list = changeText.toString().split('+', '-', '*', '/', '^', '(', ')')
                for (item in list) {
                    if(item.count( { it == '.' } ) > 1) {
                        Toast.makeText(activity, "Incorrect form: you already have point in number", Toast.LENGTH_SHORT).show()
                        return@setFragmentResultListener
                    }
                }
                binding.inputText.setText(changeText)
                binding.inputText.setSelection(position + 1)
            }
            else if (number?.let { signs.contains(it) } == true) {
                var pos = binding.inputText.selectionEnd
                if (pos == 0) {
                    return@setFragmentResultListener
                }
                if (pos != binding.inputText.length()) {
                    if (signs.contains(binding.inputText.text[pos])) {
                        Toast.makeText(activity, "2 signs in a row", Toast.LENGTH_SHORT).show()
                        return@setFragmentResultListener
                    }
                }
                if (binding.inputText.length() > 0) {
                    if (signs.contains(binding.inputText.text[pos-1])) {
                        Toast.makeText(activity, "2 signs in a row", Toast.LENGTH_SHORT).show()
                        return@setFragmentResultListener
                    }
                }
                changeText = changeText.apply { insert(position, number) }
                binding.inputText.setText(changeText)
                binding.inputText.setSelection(position + 1)
            }
            else if (number == ")") {
                var lCount = 0
                var rCount = 0
                for (element in changeText) {
                    if (element == '(') lCount++
                    else if (element == ')') rCount++
                }
                if (rCount >= lCount) {
                    Toast.makeText(activity, "There`s no openning bracket", Toast.LENGTH_SHORT).show()
                    return@setFragmentResultListener
                }
                changeText = changeText.apply { insert(position, number) }
                binding.inputText.setText(changeText)
                binding.inputText.setSelection(position + 1)
            }
            else if (number == "=") {
                var lCount = 0
                var rCount = 0
                for (element in changeText) {
                    if (element == '(') lCount++
                    else if (element == ')') rCount++
                }
                if (lCount > rCount) {
                    val count = lCount - rCount
                    binding.inputText.text.insert(binding.inputText.text.length, ")".repeat(count))
                    Toast.makeText(activity, "I`m put brackets in the end of line", Toast.LENGTH_SHORT).show()
                    return@setFragmentResultListener
                }

                try {
                    var list = changeText.toString().split(Regex("(?<=[()+/*-])|(?=[()+/*-])"))
                    var newList = ArrayList<String>()
                    for (item in list) {
                        if (item.contains('!')) {
                            if (item.dropLast(1).toInt() > 500) throw ArithmeticException()
                            newList.add(fact(item.dropLast(1).toInt()).toString())
                        }
                        else {
                            newList.add(item)
                        }
                    }
                    changeText = StringBuilder(TextUtils.join("", newList))
                }
                catch(e: ArithmeticException) {
                    Toast.makeText(activity, "Number is too big", Toast.LENGTH_SHORT).show()
                }
                catch(e: Exception) {
                    Toast.makeText(activity, "Incorrect form", Toast.LENGTH_SHORT).show()
                }

                val scope = Scope()
                    scope.mathContext = MathContext(1024)
                try {
                    val userExpression = changeText.toString()
                    val exp = Parser.parse(userExpression, scope)
                    //var exp = Parser.parse(userExpression)
                    val resultNumber = exp.evaluate().setScale(10, RoundingMode.HALF_UP).stripTrailingZeros()
                    val result = resultNumber.toPlainString()
                    if (result.length > 1029) throw ArithmeticException();
                    binding.prevText.setText(result)
                    binding.inputText.setText(result)
                    binding.inputText.setSelection(binding.inputText.text.length)
                }
                catch(e: ArithmeticException) {
                    Toast.makeText(activity, "Number is too big", Toast.LENGTH_SHORT).show()
                }
                catch(e: Exception) {
                    Toast.makeText(activity, "Incorrent form", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                val newPos = number?.length
                changeText = changeText.apply { insert(position, number) }
                binding.inputText.setText(changeText)
                binding.inputText.setSelection(position + newPos!!)
            }
        }

        if (savedInstanceState != null) {
            binding.inputText.setText(savedInstanceState.getString("inputTextData"))
            binding.prevText.setText(savedInstanceState.getString("prevTextData"))
        }

        if (binding.inputText.text != null) {
            binding.inputText.showSoftInputOnFocus = false
            binding.inputText.inputType = InputType.TYPE_CLASS_TEXT
        } else {
            binding.inputText.inputType = InputType.TYPE_NULL
        }

            binding.prevText.setMovementMethod(ScrollingMovementMethod())
        binding.inputText.setHorizontallyScrolling(true)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("inputTextData", binding.inputText.text.toString())
        outState.putString("prevTextData", binding.prevText.text.toString())
        super.onSaveInstanceState(outState)
    }

    companion object {
        fun newInstance() = WorkSpaceFragment()
    }
}