import br.com.alunos.odontofast.ChecklistItem
import br.com.alunos.odontofast.database.ChecklistDao

private fun fetchChecklistItems(onResult: (List<ChecklistItem>) -> Unit) {
    val checklistDao = ChecklistDao()
    val checklistItems = checklistDao.getChecklistItems()
    onResult(checklistItems) // Chama o callback com os itens
}
