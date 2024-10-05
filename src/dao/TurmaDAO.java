package src.dao;

import src.model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    private final Connection conexao;

    public TurmaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para adicionar uma turma no banco de dados
    public boolean adicionar(Turma turma) {
        String sql = "INSERT INTO turma (codigo_turma, horario, sala, capacidade) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, turma.getCodigoTurma());
            stmt.setString(2, turma.getHorario());
            stmt.setString(3, turma.getSala());
            stmt.setInt(4, turma.getCapacidade());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todas as turmas
    public List<Turma> listar() {
        List<Turma> listaTurmas = new ArrayList<>();
        String sql = "SELECT * FROM turma";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String codigoTurma = rs.getString("codigo_turma");
                String horario = rs.getString("horario");
                String sala = rs.getString("sala");
                int capacidade = rs.getInt("capacidade");

                Turma turma = new Turma(codigoTurma, horario, sala, capacidade);
                listaTurmas.add(turma);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaTurmas;
    }

    // Método para editar uma turma no banco de dados
    public boolean editar(Turma turma) {
        String sql = "UPDATE turma SET horario = ?, sala = ?, capacidade = ? WHERE codigo_turma = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, turma.getHorario());
            stmt.setString(2, turma.getSala());
            stmt.setInt(3, turma.getCapacidade());
            stmt.setString(4, turma.getCodigoTurma());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para remover uma turma do banco de dados
    public boolean remover(String codigoTurma) {
        String sql = "DELETE FROM turma WHERE codigo_turma = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, codigoTurma);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
