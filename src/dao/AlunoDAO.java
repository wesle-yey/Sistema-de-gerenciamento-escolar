package src.dao;

import src.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private final Connection conexao;

    // Construtor que recebe a conexão
    public AlunoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Exemplo de método adicionar
    public boolean adicionar(Aluno aluno) {
        String sql = "INSERT INTO Aluno (matricula, nome, data_nascimento, endereco) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setDate(3, java.sql.Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(4, aluno.getEndereco());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao adicionar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean editar(Aluno aluno) {
        String sql = "UPDATE Aluno SET nome = ?, data_nascimento = ?, endereco = ? WHERE matricula = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setDate(2, java.sql.Date.valueOf(aluno.getDataNascimento())); // Convertendo LocalDate para java.sql.Date
            stmt.setString(3, aluno.getEndereco());
            stmt.setInt(4, aluno.getMatricula()); // A matrícula é o critério para atualizar

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Retorna true se uma linha foi atualizada
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Método para listar todos os alunos
    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int matricula = rs.getInt("matricula"); // Agora é int
                String nome = rs.getString("nome");
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                String endereco = rs.getString("endereco");

                Aluno aluno = new Aluno(matricula, nome, dataNascimento, endereco);
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }

    // Método para remover um aluno pelo número de matrícula
    public boolean remover(int matricula) {
        String sql = "DELETE FROM Aluno WHERE matricula = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0; // Retorna true se uma linha foi removida
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
