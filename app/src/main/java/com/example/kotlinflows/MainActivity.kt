package com.example.kotlinflows

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinflows.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

      val job = GlobalScope.launch {
            val data = producer()
            data.collect {
                Log.e("Emmited data", it.toString())
            }
        }

     GlobalScope.launch {
            val data = producer()
         delay(2500)
            data.collect {
                Log.e("Emmited data 2nd consumer", it.toString())
            }
        }

        GlobalScope.launch {
            delay(5500)
            job.cancel()
        }
    }

    fun producer() = flow<Int> {

        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        list.forEach {
            delay(1000)
            Log.e("Emmited data", it.toString())
            emit(it)
        }
    }
}