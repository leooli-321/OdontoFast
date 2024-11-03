package br.com.alunos.odontofast

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class FichaActivity : AppCompatActivity() {

    private val username = "rm554024@fiap.com.br"
    private val password = "180995"
    private val url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fichaodonto)

        // Configurar o botão de voltar para retornar à MainActivity
        val backButton = findViewById<ImageView>(R.id.imageView9)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Chama a função para buscar dados
        fetchDentalData()
    }

    private fun fetchDentalData() {
        var connection: Connection? = null
        try {
            connection = DriverManager.getConnection(url, username, password)
            val statement: Statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM dental_records") // Substitua 'dental_records' pelo nome da sua tabela

            if (resultSet.next()) {
                // Supondo que você tenha colunas chamadas 'title' e 'event'
                val title = resultSet.getString("title")
                val event = resultSet.getString("event")

                // Exibe os dados no TextView
                findViewById<TextView>(R.id.tvTitle).text = title
                findViewById<TextView>(R.id.textViewEvento1).text = event
            }

            resultSet.close()
            statement.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
    }
}
