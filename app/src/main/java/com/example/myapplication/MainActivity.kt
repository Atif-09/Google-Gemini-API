package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createButton.setOnClickListener {
            if (binding.textInput.text!!.isEmpty()){
                Toast.makeText(this, "Please enter some query", Toast.LENGTH_SHORT).show()
            } else {

                sendPrompt(binding.textInput.text.toString())
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun sendPrompt(text: String) {
        val generativeModel = GenerativeModel(
            // For text-only input, use the gemini-pro model
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.apiKey
        )
        CoroutineScope(Dispatchers.IO).launch {
            val response = generativeModel.generateContent(text)
            withContext(Dispatchers.Main) {
                binding.response.text = response.text
                binding.response.movementMethod = ScrollingMovementMethod()
                binding.progressBar.visibility = View.GONE
                Log.d("Model Response", "sendPrompt: ${response.text}")
            }

        }

    }

}