package br.com.alunos.odontofast

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FichaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fichaodonto)

        // Configurar o botão de voltar para retornar à MainActivity
        val backButton = findViewById<ImageView>(R.id.imageView9)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finalizar a FichaActivity para que não fique no histórico
        }
    }
}
