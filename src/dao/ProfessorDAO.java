package src.dao;

import src.model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    private Connection conexao;

    public ProfessorDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean adicionar(Professor professor) throws SQLException {
        String sql = "INSERT INTO Professor (id, nome, especializacao, departamento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, professor.getId());
            stmt.setString(2, professor.getNome());
            stmt.setString(3, professor.getEspecializacao());
            stmt.setString(4, professor.getDepartamento());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean editar(Professor professor) throws SQLException {
        String sql = "UPDATE Professor SET nome = ?, especializacao = ?, departamento = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEspecializacao());
            stmt.setString(3, professor.getDepartamento());
            stmt.setInt(4, professor.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM Professor WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Professor> listar() throws SQLException {
        List<Professor> professores = new ArrayList<>();
        String sql = "SELECT * FROM Professor";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getString("nome"),
                        rs.getInt("id"),
                        rs.getString("especializacao"),
                        rs.getString("departamento")
                );
                professores.add(professor);
            }
        }
        return professores;
    }
}

