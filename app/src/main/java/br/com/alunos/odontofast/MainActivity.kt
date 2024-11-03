package br.com.alunos.odontofast

import NotificationWorker
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o SharedPreferences
        sharedPreferences = getSharedPreferences("notifications_prefs", Context.MODE_PRIVATE)

        // Receber o valor da carteirinha
        val carteirinha = intent.getStringExtra("CARTEIRINHA")

        // Atualizar o TextView com o nome da carteirinha
        val textViewGreeting = findViewById<TextView>(R.id.textViewGreeting)
        textViewGreeting.text = "Olá,\n $carteirinha! 👋"

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

        // Limpar notificações para evitar dados inválidos
        clearNotifications()  // Opcional: comente esta linha se não quiser limpar os dados a cada inicialização

        // Iniciar as notificações
        scheduleNotifications()

        // Exemplo de como enviar uma notificação
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        sendNotification("Consulta amanhã às 14:45!", "Consulta agendada com sucesso.", currentTime)
        sendNotification("Não esqueça de passar fio dental!", "Lembre-se de cuidar da sua saúde bucal.", currentTime)
    }

    private fun scheduleNotifications() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun sendNotification(title: String, message: String, time: String) {
        // Salva a notificação completa
        saveNotification(NotificationData(title, message, time))
    }

    private fun saveNotification(notification: NotificationData) {
        val notifications = getSavedNotifications().toMutableList()
        notifications.add(notification)

        // Limita a 10 notificações
        if (notifications.size > 10) {
            notifications.removeAt(0) // Remove a notificação mais antiga
        }

        // Salva as notificações de volta no SharedPreferences
        val json = Gson().toJson(notifications)
        Log.d("DEBUG", "Saving notifications: $json")
        sharedPreferences.edit().putString("notifications", json).apply()
    }

    private fun clearNotifications() {
        sharedPreferences.edit().remove("notifications").apply()
    }

    private fun getSavedNotifications(): List<NotificationData> {
        val json = sharedPreferences.getString("notifications", "[]")
        Log.d("DEBUG", "Retrieved notifications JSON: $json")
        return try {
            Gson().fromJson(json, object : TypeToken<List<NotificationData>>() {}.type)
        } catch (e: Exception) {
            Log.e("ERROR", "Failed to parse notifications JSON", e)
            emptyList() // Retorna uma lista vazia em caso de erro
        }
    }
}
