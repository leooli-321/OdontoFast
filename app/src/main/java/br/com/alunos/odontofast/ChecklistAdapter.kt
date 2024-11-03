package br.com.alunos.odontofast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChecklistAdapter(
    private val items: List<ChecklistItem>,
    private val onItemChecked: (ChecklistItem) -> Unit // Callback para tarefa concluída
) : RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {

    class ChecklistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(item: ChecklistItem, onItemChecked: (ChecklistItem) -> Unit) {
            taskTextView.text = item.task
            descriptionTextView.text = item.description
            checkBox.isChecked = item.isChecked

            // Define um listener para o CheckBox
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
                onItemChecked(item) // Chama o callback quando a tarefa é marcada/desmarcada
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checklist, parent, false)
        return ChecklistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemChecked) // Passa o callback para o holder
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
