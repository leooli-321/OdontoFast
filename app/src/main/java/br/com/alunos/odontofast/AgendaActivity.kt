package br.com.alunos.odontofast

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AgendaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        // Configurar o botão de voltar para retornar à MainActivity
        val backButton = findViewById<ImageView>(R.id.imageView9)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finalizar a AgendaActivity para que não fique no histórico
        }
    }
}
