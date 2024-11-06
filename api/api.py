from flask import Flask, jsonify
import random

app = Flask(__name__)

# Exemplo de dados do checklist
checklist_data = [
    {"task": "Escovar os dentes", "description": "Escovar os dentes após cada refeição.", "isChecked": False},
    {"task": "Usar fio dental", "description": "Usar fio pelo menos uma vez por dia.", "isChecked": False},
    {"task": "Visitar o dentista", "description": "Agendar uma consulta com o dentista a cada 6 meses.", "isChecked": False},
    {"task": "Beber água", "description": "Beber pelo menos 2 litros de água por dia.", "isChecked": False},
    {"task": "Fazer exercícios", "description": "Praticar atividade física pelo menos 3 vezes por semana.", "isChecked": False},
    {"task": "Lavar as mãos", "description": "Lavar as mãos antes de comer e após usar o banheiro.", "isChecked": False},
    {"task": "Usar protetor solar", "description": "Aplicar protetor solar antes de sair de casa.", "isChecked": False},
    {"task": "Dormir bem", "description": "Tentar dormir pelo menos 7 horas por noite.", "isChecked": False},
    {"task": "Comer frutas e vegetais", "description": "Incluir frutas e vegetais na alimentação diária.", "isChecked": False},
    {"task": "Evitar açúcar em excesso", "description": "Limitar a ingestão de açúcar adicionado.", "isChecked": False},
]

@app.route('/checklist', methods=['GET'])
def get_checklist():
    random_tasks = random.sample(checklist_data, 3)
    return jsonify(random_tasks)

if __name__ == '__main__':
    app.run(debug=True)
