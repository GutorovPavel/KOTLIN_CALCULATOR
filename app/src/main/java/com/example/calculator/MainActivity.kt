package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_work_space, WorkSpaceFragment.newInstance(), "TAG")
                .commit()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_basic_functions, BasicFunctionsFragment.newInstance())
            .commit()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_scientific_functions, ScientificFunctionsFragment.newInstance())
            .commit()


    }


}