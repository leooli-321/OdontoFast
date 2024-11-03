package br.com.alunos.odontofast

data class ChecklistItem(
    val task: String,
    val description: String,
    var isChecked: Boolean
)
