package br.com.alunos.odontofast

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NotificationsActivity : AppCompatActivity() {

    private lateinit var notificationsLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        // Atualize esta linha
        notificationsLayout = findViewById(R.id.notificationsContainer)

        // Carregar notificações
        loadNotifications()
    }

    private fun loadNotifications() {
        val sharedPreferences = getSharedPreferences("notifications_prefs", MODE_PRIVATE)
        val json = sharedPreferences.getString("notifications", "[]")
        val notifications: List<NotificationData> = Gson().fromJson(json, object : TypeToken<List<NotificationData>>() {}.type)

        for (notification in notifications) {
            addNotificationToLayout(notification)
        }
    }

    private fun addNotificationToLayout(notification: NotificationData) {
        // Inflar o layout da notificação e configurar os dados
        val notificationView = layoutInflater.inflate(R.layout.notification_item, notificationsLayout, false)

        notificationView.findViewById<TextView>(R.id.notification1).text = notification.message
        notificationView.findViewById<TextView>(R.id.notification1_time).text = notification.time
        // Você pode configurar o título e outras propriedades aqui, conforme seu layout

        notificationsLayout.addView(notificationView)
    }
}
