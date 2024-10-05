package br.com.alunos.odontofast

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vinculando os layouts de botões
        val fichaOdontoButton = findViewById<ConstraintLayout>(R.id.cardLayout1)
        val agendaButton = findViewById<ConstraintLayout>(R.id.cardLayout2)
        val checklistButton = findViewById<ConstraintLayout>(R.id.cardLayout3)
        val notificacoesButton = findViewById<ConstraintLayout>(R.id.cardLayout4)

        // Navegação para FichaActivity
        fichaOdontoButton.setOnClickListener {
            val intent = Intent(this, FichaActivity::class.java)
            startActivity(intent)
        }

        // Navegação para AgendaActivity
        agendaButton.setOnClickListener {
            val intent = Intent(this, AgendaActivity::class.java)
            startActivity(intent)
        }

        // Navegação para ChecklistActivity
        checklistButton.setOnClickListener {
            val intent = Intent(this, ChecklistActivity::class.java)
            startActivity(intent)
        }

        // Navegação para NotificationsActivity
        notificacoesButton.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }
    }
}
