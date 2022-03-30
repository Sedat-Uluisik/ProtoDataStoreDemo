package com.sedat.protodatastoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sedat.protodatastoredemo.databinding.ActivityMainBinding
import com.sedat.protodatastoredemo.model.UserPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var protoViewModel: ProtoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        protoViewModel = ViewModelProvider(this)[ProtoViewModel::class.java]

        binding.saveBtn.setOnClickListener {

            val name = binding.nameEdtx.text.toString()
            val age = binding.ageEdtx.text.toString()

            if(name.isNotEmpty() && age.isNotEmpty())
                protoViewModel.writeData(name, age.toInt())
        }

        lifecycleScope.launchWhenStarted {
            protoViewModel.readData.collect { userPrefs ->
                binding.nameTxv.text = userPrefs.name
                binding.ageTxv.text = userPrefs.age.toString()
            }
        }
    }
}