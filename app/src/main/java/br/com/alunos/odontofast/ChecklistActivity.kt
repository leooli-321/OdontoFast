package br.com.alunos.odontofast

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ChecklistActivity : AppCompatActivity() {

    private lateinit var checklistAdapter: ChecklistAdapter
    private var completedTasks = 0 // Para contar tarefas concluídas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)

        // Botão de voltar
        val backButton = findViewById<ImageView>(R.id.imageView9)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Solicitar permissão para notificações, se necessário
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33 ou superior
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        // Configuração do RecyclerView
        val recyclerChecklist = findViewById<RecyclerView>(R.id.recyclerChecklist)
        recyclerChecklist.layoutManager = LinearLayoutManager(this)

        // Verifica a conectividade antes de carregar os dados
        if (isNetworkAvailable(this)) {
            // Carregar dados da API e configurar o Adapter
            fetchChecklistItems { checklistItems ->
                checklistAdapter = ChecklistAdapter(checklistItems) { item ->
                    onChecklistItemChecked(item)
                }
                recyclerChecklist.adapter = checklistAdapter
            }
        } else {
            // Exibir mensagem e carregar dados locais
            Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show()
            val localChecklistItems = loadLocalChecklistItems()
            checklistAdapter = ChecklistAdapter(localChecklistItems) { item ->
                onChecklistItemChecked(item)
            }
            recyclerChecklist.adapter = checklistAdapter
        }
    }

    private fun fetchChecklistItems(onResult: (List<ChecklistItem>) -> Unit) {
        // Configuração do Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // Altere para o URL da sua API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val checklistService = retrofit.create(ChecklistService::class.java)

        // Requisição para obter os dados
        checklistService.getChecklistItems().enqueue(object : Callback<List<ChecklistItem>> {
            override fun onResponse(call: Call<List<ChecklistItem>>, response: Response<List<ChecklistItem>>) {
                if (response.isSuccessful) {
                    val checklistItems = response.body() ?: emptyList()
                    onResult(checklistItems) // Chama o callback com os itens
                } else {
                    Log.e("ChecklistActivity", "Erro ao obter os dados: ${response.code()}")
                    onResult(emptyList()) // Retorna lista vazia em caso de erro
                }
            }

            override fun onFailure(call: Call<List<ChecklistItem>>, t: Throwable) {
                Log.e("ChecklistActivity", "Falha na requisição: ${t.message}")
                onResult(emptyList()) // Retorna lista vazia em caso de falha
            }
        })
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    private fun loadLocalChecklistItems(): List<ChecklistItem> {
        // Carregue os itens do checklist de uma fonte local
        return listOf(
            ChecklistItem("Escovar os dentes", "Escovar os dentes após cada refeição.", false),
            ChecklistItem("Usar fio dental", "Usar fio dental pelo menos uma vez por dia.", false),
            ChecklistItem("Visitar o dentista", "Agendar uma consulta com o dentista a cada 6 meses.", false)
        )
    }

    private fun onChecklistItemChecked(item: ChecklistItem) {
        // Exibir um Toast ao concluir uma tarefa
        if (item.isChecked) {
            completedTasks++
            Toast.makeText(this, "${item.task} concluído!", Toast.LENGTH_SHORT).show()
        } else {
            completedTasks--
        }

        // Verifica se todas as tarefas estão concluídas
        if (completedTasks == checklistAdapter.itemCount) {
            sendNotification("Parabéns! Você completou todas as tarefas!")
        }
    }

    private fun sendNotification(message: String) {
        val notificationManager = NotificationManagerCompat.from(this)
        val channelId = "checklist_channel"

        // Cria um canal de notificação se estiver em Android 8.0 ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Checklist Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para notificações de checklist"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification) // Use um ícone adequado
            .setContentTitle("Tarefas Concluídas")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Verifica se a permissão de notificações foi concedida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, notificationBuilder.build())
        } else {
            // Se a permissão não foi concedida, você pode solicitar a permissão aqui ou ignorar
            Toast.makeText(this, "Permissão de notificações não concedida.", Toast.LENGTH_SHORT).show()
        }
    }
}
