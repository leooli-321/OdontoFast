package br.com.alunos.odontofast

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Vinculando os elementos da interface
        val etCarteirinha = findViewById<EditText>(R.id.etCarteirinha)
        val etSenha = findViewById<EditText>(R.id.etSenha)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Configuração do botão de login
        btnLogin.setOnClickListener {
            val carteirinha = etCarteirinha.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            // Tratamento de erros: verificar se os campos estão vazios
            if (carteirinha.isEmpty()) {
                etCarteirinha.error = "Por favor, digite a carteirinha"
                return@setOnClickListener
            }

            if (senha.isEmpty()) {
                etSenha.error = "Por favor, digite a senha"
                return@setOnClickListener
            }

            // Simples lógica para permitir login com qualquer valor
            Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show()

            // Navegação para a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("CARTEIRINHA", carteirinha) // Passando a carteirinha
            startActivity(intent)
            finish()  // Finaliza a LoginActivity para não voltar nela
        }
    }
}
