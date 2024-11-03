package br.com.alunos.odontofast.database

import br.com.alunos.odontofast.ChecklistItem
import br.com.alunos.odontofast.DatabaseHelper
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class ChecklistDao {

    fun getChecklistItems(): List<ChecklistItem> {
        val connection: Connection? = DatabaseHelper.getConnection() // Chamada correta da função getConnection()
        val items = mutableListOf<ChecklistItem>()

        if (connection != null) {
            // Usando connection como variável nomeada, não 'it'
            val stmt: Statement = connection.createStatement()
            val resultSet: ResultSet = stmt.executeQuery("SELECT task, description, isChecked FROM checklist")

            while (resultSet.next()) {
                val task = resultSet.getString("task")
                val description = resultSet.getString("description")
                val isChecked = resultSet.getBoolean("isChecked")

                items.add(ChecklistItem(task, description, isChecked))
            }
            resultSet.close()
            stmt.close()
            connection.close() // Feche a conexão aqui
        }

        return items
    }
}
