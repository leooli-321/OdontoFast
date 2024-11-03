package br.com.alunos.odontofast

import NotificationWorker
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Receber o valor da carteirinha
        val carteirinha = intent.getStringExtra("CARTEIRINHA")

        // Atualizar o TextView com o nome da carteirinha
        val textViewGreeting = findViewById<TextView>(R.id.textViewGreeting)
        textViewGreeting.text = "Olá,\n $carteirinha! 👋" // Atualize aqui

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

        // Iniciar as notificações
        scheduleNotifications()
    }

    private fun scheduleNotifications() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS) // Frequência das notificações
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }
}
