package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResultListener
import com.example.calculator.databinding.FragmentWorkSpaceBinding
import com.mpobjects.bdparsii.eval.Parser
import com.mpobjects.bdparsii.eval.Scope
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


fun fact(num: Int): BigDecimal? {
    if (num == 0) return BigDecimal.ONE
    return fact(num - 1)?.multiply(num.toBigDecimal())
}

fun checkFunc(position: Int, changeText: StringBuilder, activity: FragmentActivity?): Boolean {
    if (changeText.length > 2 && (position < changeText.length) && position != 0 && !changeText.contains("log2") &&
        (
                (changeText[position].isLetter() && changeText[position + 1].isLetter() && changeText[position - 1].isLetter())
                        ||
                        (changeText[position].isLetter() && changeText[position - 1].isLetter())
                        ||
                        (changeText[position].isLetter() && changeText[position + 1] == '(')
                        ||
                        (changeText[position - 1].isLetter() && changeText[position] == '(')
                )
    ) {
        Toast.makeText(activity, "Cannot type in function name", Toast.LENGTH_SHORT).show()
        return false
    } else if (changeText.contains("log2") && changeText.length > 2 && (position < changeText.length) && position != 0 &&
        (
                (changeText[position].isLetter() && changeText[position + 1].isLetter() && changeText[position - 1].isLetter())
                        ||
                        (changeText[position].isLetter() && changeText[position - 1].isLetter())
                        ||
                        (changeText[position].isLetter() && changeText[position + 1] == '(')
                        ||
                        (changeText[position - 1].isLetter() && changeText[position] == '(')
                        ||
                        (changeText[position - 1] == 'g' && changeText[position] == '2')
                        ||
                        (changeText[position - 1] == '2' && changeText[position] == '(')
                ))
    {
        Toast.makeText(activity, "Cannot type in function name", Toast.LENGTH_SHORT).show()
        return false
    } else if (changeText.contains("^(")) {
        if (position != changeText.length && changeText[position] == '(' && changeText[position - 1] == '^') {
            Toast.makeText(activity, "Cannot type in function name", Toast.LENGTH_SHORT).show()
            return false
        }
    }
    return true
}


class WorkSpaceFragment : Fragment() {

    private lateinit var binding: FragmentWorkSpaceBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentWorkSpaceBinding.inflate(inflater, container, false)

        val signs = "+-/*^"

        setFragmentResultListener("requestKey") { _, bundle ->
            val number = bundle.getString("bundleKey")
            var changeText = StringBuilder(binding.inputText.text.toString())
            val positionStart = binding.inputText.selectionStart
            val position = binding.inputText.selectionEnd

            if (number == "clear") {
                if (binding.inputText.length() == 0) binding.prevText.text = ""
                binding.inputText.text.clear()
                return@setFragmentResultListener

            } else if (number == "delete") {

//                Toast.makeText(activity, "pos:$position", Toast.LENGTH_SHORT).show()

                if (position > 2 && changeText[position - 1] == 'g' && changeText[position] == '2') {
                    var counter = position
                    changeText = changeText.deleteAt(counter)
                    changeText = changeText.deleteAt(counter)
                    while(counter > 0 && changeText[counter - 1].isLetter()) {
                        changeText = changeText.deleteAt(counter - 1)
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(counter - 1)
                        counter--
                    }
                } else if (position > 3 && changeText[position - 1] == '2' && changeText[position - 2] == 'g') {
                    var counter = position
                    changeText = changeText.deleteAt(counter)
                    changeText = changeText.deleteAt(counter - 1)
                    counter--
                    while(counter > 0 && changeText[counter - 1].isLetter()) {
                        changeText = changeText.deleteAt(counter - 1)
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(counter - 1)
                        counter--
                    }
                }
                else if (position > 4 && changeText[position - 2] == '2' && changeText[position - 3] == 'g') {
                    var counter = position
                    changeText = changeText.deleteAt(counter - 1)
                    changeText = changeText.deleteAt(counter - 2)
                    counter -= 2
                    while(counter > 0 && changeText[counter - 1].isLetter()) {
                        changeText = changeText.deleteAt(counter - 1)
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(counter - 1)
                        counter--
                    }
                } else if (position > 2 && changeText[position - 1] == '(')
                {
                    var counter = position
                    changeText = changeText.deleteAt(counter - 1)
                    binding.inputText.setText(changeText)
                    binding.inputText.setSelection(counter - 1)
                    counter--
                    while(counter > 0 && changeText[counter - 1].isLetter()) {
                        changeText = changeText.deleteAt(counter - 1)
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(counter - 1)
                        counter--
                    }
                } else if (changeText.length > position && position > 1 && changeText[position] == '(' && changeText[position - 1].isLetter())
                {
                    var counter = position
                    changeText = changeText.deleteAt(counter)
                    binding.inputText.setText(changeText)
                    while(counter > 0 && changeText[counter - 1].isLetter()) {
                        changeText = changeText.deleteAt(counter - 1)
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(counter - 1)
                        counter--
                    }
                } else if (position > 0 && changeText[position - 1].isLetter() && changeText[position].isLetter()) {
                    var left = position
                    val right = position

                    while (changeText[right] != '(') {
                        changeText = changeText.deleteAt(right)
                    }

                    changeText = changeText.deleteAt(right)

                    while(left > 0 && changeText[left - 1].isLetter()) {
                        changeText = changeText.deleteAt(left - 1)
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(left - 1)
                        left--
                    }
                }

                else if (position != 0 && (positionStart != position)) {
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
                } else if (binding.inputText.text[0] == '.') {
                    binding.inputText.setText("0${binding.inputText.text}")
                }

            } else if (number == ".") {
                if (checkFunc(position, changeText, activity)) {
                    changeText = changeText.apply { insert(position, number) }
                    val list = changeText.toString().split('+', '-', '*', '/', '^', '(', ')')
                    for (item in list) {
                        if (item.count { it == '.' } > 1) {
                            Toast.makeText(activity, "Dot is already exists", Toast.LENGTH_SHORT).show()
                            return@setFragmentResultListener
                        }
                    }

                    if (position == 0) {
                        changeText.insert(position, 0)
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(2)
                    }
                    else if (!changeText[position - 1].isDigit()) {
                        changeText.insert(position, 0)
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(position + 2)
                    }
                    else {
                        binding.inputText.setText(changeText)
                        binding.inputText.setSelection(position + 1)
                    }
                }

            } else if (number?.let { signs.contains(it) } == true) {

                if (checkFunc(position, changeText, activity)) {
                    val pos = binding.inputText.selectionEnd
                    if (pos == 0) {
                        return@setFragmentResultListener
                    }
                    if (pos != binding.inputText.length()) {
                        if (signs.contains(binding.inputText.text[pos])) {
//                            Toast.makeText(activity, "2 signs in a row", Toast.LENGTH_SHORT).show()
                            return@setFragmentResultListener
                        }
                    }
                    if (binding.inputText.length() > 0) {
                        if (signs.contains(binding.inputText.text[pos - 1])) {
//                            Toast.makeText(activity, "2 signs in a row", Toast.LENGTH_SHORT).show()
                            return@setFragmentResultListener
                        }
                    }
                    changeText = changeText.apply { insert(position, number) }
                    binding.inputText.setText(changeText)
                    binding.inputText.setSelection(position + 1)
                }

            } else if (number == ")") {
                if (checkFunc(position, changeText, activity)){
                    var lCount = 0
                    var rCount = 0
                    for (element in changeText) {
                        if (element == '(') lCount++
                        else if (element == ')') rCount++
                    }
                    if (rCount >= lCount) {
//                        Toast.makeText(activity, "No opening bracket", Toast.LENGTH_SHORT).show()
                        return@setFragmentResultListener
                    }
                    changeText = changeText.apply { insert(position, number) }
                    binding.inputText.setText(changeText)
                    binding.inputText.setSelection(position + 1)
                }

            } else if (number == "=") {
                var lCount = 0
                var rCount = 0
                for (element in changeText) {
                    if (element == '(') lCount++
                    else if (element == ')') rCount++
                }
                if (lCount > rCount) {
                    val count = lCount - rCount
                    binding.inputText.text.insert(
                        binding.inputText.text.length,
                        ")".repeat(count)
                    )
                    return@setFragmentResultListener
                }

                try {
                    val list = changeText.toString().split(Regex("(?<=[()+/*-])|(?=[()+/*-])"))
                    val newList = ArrayList<String>()
                    for (item in list) {
                        if (item.contains('!')) {
//                            if (item.dropLast(1).toInt() > 460) throw ArithmeticException()
                            if (item.dropLast(1).toInt() > 460) {
                                Toast.makeText(
                                    activity,
                                    "Number is too big",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@setFragmentResultListener
                            }
                            newList.add(fact(item.dropLast(1).toInt()).toString())
                        } else {
                            newList.add(item)
                        }
                    }
                    changeText = StringBuilder(TextUtils.join("", newList))
                } catch (e: ArithmeticException) {
                    Toast.makeText(
                        activity,
                        e.toString().replace("java.lang.ArithmeticException: ", ""),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        activity,
                        e.toString().replace("ERROR   1:", ""),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val scope = Scope()
                scope.mathContext = MathContext(128)
                try {
                    val userExpression = changeText.toString()
                    val exp = Parser.parse(userExpression, scope)
                    val resultNumber =
                        exp.evaluate().setScale(20, RoundingMode.HALF_UP).stripTrailingZeros()
                    val result = resultNumber.toPlainString()
//                    if (result.length > 1029) throw ArithmeticException()
                    if (result.length > 1029) {
                        Toast.makeText(activity, "Number is too big", Toast.LENGTH_SHORT).show()
                        return@setFragmentResultListener
                    }
                    binding.prevText.text = result
                    binding.inputText.setText(result)
                    binding.inputText.setSelection(binding.inputText.text.length)
                } catch (e: ArithmeticException) {
                    Toast.makeText(
                        activity,
                        e.toString().replace("java.lang.ArithmeticException: ", ""),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        activity, e.toString().replace("ERROR   1: ", ""), Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (number == "0" && changeText.startsWith("0") && (position == 0 || position == 1)) {
//                Toast.makeText(
//                    activity, "Double zeros are not allowed", Toast.LENGTH_SHORT
//                ).show()
            } else if (number == "pi"){
                changeText = changeText.apply { insert(position,  "3.1415926535") }
                binding.inputText.setText(changeText)
                binding.inputText.setSelection(position + 12)
            } else {
                if (checkFunc(position, changeText, activity)) {
                    var newPos = number?.length
                    if (changeText.toString() == "0") {
                        changeText = changeText.deleteAt(0)
                        changeText = changeText.apply { insert(0, number) }
                        if (number != null) {
                            newPos = number.length - 1
                        }
                    } else {
                        changeText = changeText.apply { insert(position, number) }
                    }
                    binding.inputText.setText(changeText)
                    binding.inputText.setSelection(position + newPos!!)

                }
            }
        }

        if (savedInstanceState != null) {
            binding.inputText.setText(savedInstanceState.getString("inputTextData"))
            binding.prevText.text = savedInstanceState.getString("prevTextData")
        }

        binding.prevText.movementMethod = ScrollingMovementMethod()
        binding.inputText.setHorizontallyScrolling(true)
        binding.inputText.showSoftInputOnFocus = false

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