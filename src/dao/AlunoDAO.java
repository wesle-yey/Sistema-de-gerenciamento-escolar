package src.dao;

import src.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private static Connection conexao;

    public AlunoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo aluno
    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO Aluno (matricula, nome, data_nascimento, endereco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setDate(3, java.sql.Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(4, aluno.getEndereco());
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os alunos
    public static List<Aluno> listar() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getString("matricula"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getString("endereco")
                );
                alunos.add(aluno);
            }
        }
        return alunos;
    }
}

