package br.com.alunos.odontofast

import retrofit2.Call
import retrofit2.http.GET

interface ChecklistService {
    @GET("/checklist") // Agora está correto
    fun getChecklistItems(): Call<List<ChecklistItem>>
}
