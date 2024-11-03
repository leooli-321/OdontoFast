package br.com.alunos.odontofast.api

import retrofit2.Call
import retrofit2.http.GET

// Defina a interface da API
interface ApiService {
    @GET("path/to/your/checklist/data") // Altere para o endpoint correto
    fun getChecklistItems(): Call<List<ChecklistItem>> // ChecklistItem deve ser a classe que representa os itens
}
