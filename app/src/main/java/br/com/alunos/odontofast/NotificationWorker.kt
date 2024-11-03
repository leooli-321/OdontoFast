import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.alunos.odontofast.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val notificationChannelId = "checklist_channel"
    private val notificationMessages: List<String> = loadNotificationMessages(context)

    override fun doWork(): Result {
        createNotificationChannel()

        val notificationMessage = notificationMessages[Random.nextInt(notificationMessages.size)]
        sendNotification(notificationMessage)

        return Result.success()
    }

    private fun loadNotificationMessages(context: Context): List<String> {
        val inputStream = context.resources.openRawResource(R.raw.notification_messages)
        val reader = inputStream.bufferedReader()
        return Gson().fromJson(reader, object : TypeToken<List<String>>() {}.type)
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Checklist Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(message: String) {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notificationBuilder = NotificationCompat.Builder(applicationContext, notificationChannelId)
                .setSmallIcon(R.drawable.ic_notification) // Altere para o ícone apropriado
                .setContentTitle("Lembrete de Saúde Bucal")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val notificationManager = NotificationManagerCompat.from(applicationContext)
            notificationManager.notify(Random.nextInt(), notificationBuilder.build())
        } else {
            // Lidar com a situação em que a permissão não foi concedida
            // Isso pode incluir não enviar a notificação ou armazenar uma ação para enviar mais tarde
            // Caso queira adicionar lógica aqui, faça conforme necessário.
        }
    }
}
