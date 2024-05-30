package com.example.tde4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tde4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inicialize o binding
        val view = binding.root
        setContentView(view)

        // Acessando os elementos do layout via binding
        binding.startGameButton.setOnClickListener {
            // Inicia a próxima activity que contém o jogo
           // val intent = Intent(this, GameActivity::class.java)
            //startActivity(intent)
        }
    }
}