package src.view;

import src.dao.AlunoDAO;
import src.model.Aluno;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class TelaAluno extends TelaGenerica {

    public TelaAluno() throws SQLException {
        super("Gerenciamento de Alunos");
        configurarTabela(); // Chama o método para configurar a tabela
    }

    @Override
    protected void configurarTabela() throws SQLException {
        // Definindo as colunas da tabela
        String[] colunas = {"Matrícula", "Nome", "Data de Nascimento", "Endereço"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        tabela.setModel(modeloTabela);
        List<Aluno> alunos= AlunoDAO.listar();

        for (int i = 0; i < alunos.size(); i++) {
            modeloTabela.addRow(new Object[]{alunos.get(i),
                    alunos.get(i).getMatricula(),
                    alunos.get(i).getNome(),
                    alunos.get(i).getDataNascimento(),
                    alunos.get(i).getEndereco(),});
        }

        // Aqui você pode carregar os dados dos alunos a partir do banco
        // Exemplo de como adicionar linhas
        //modeloTabela.addRow(new Object[]{"123", "João Silva", "2000-01-01", "Rua A, 123"});
        //modeloTabela.addRow(new Object[]{"124", "Maria Souza", "1999-05-22", "Rua B, 456"});
    }
}

