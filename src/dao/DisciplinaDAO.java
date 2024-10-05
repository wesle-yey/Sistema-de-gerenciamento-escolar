package src.dao;

import src.model.Disciplina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    private final Connection conexao;

    public DisciplinaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para adicionar uma nova disciplina no banco de dados
    public boolean adicionar(Disciplina disciplina) {
        if (disciplina == null || disciplina.getCodigo() == null || disciplina.getNomeDisciplina() == null) {
            throw new IllegalArgumentException("Disciplina e seus atributos não podem ser nulos");
        }

        String sql = "INSERT INTO disciplina (codigo_disciplina, nome, carga_horaria, ementa) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, disciplina.getCodigo());
            stmt.setString(2, disciplina.getNomeDisciplina());
            stmt.setInt(3, disciplina.getCargaHoraria());
            stmt.setString(4, disciplina.getEmenta());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar disciplina: " + e.getMessage());
            return false;
        }
    }

    // Método para listar todas as disciplinas
    public List<Disciplina> listar() {
        List<Disciplina> listaDisciplinas = new ArrayList<>();
        String sql = "SELECT * FROM disciplina";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String codigoDisciplina = rs.getString("codigo_disciplina");
                String nome = rs.getString("nome");
                int cargaHoraria = rs.getInt("carga_horaria");
                String ementa = rs.getString("ementa");


                Disciplina disciplina = new Disciplina(codigoDisciplina, nome, cargaHoraria, ementa);
                listaDisciplinas.add(disciplina);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaDisciplinas;
    }

    // Método para editar uma disciplina no banco de dados
    public boolean editar(Disciplina disciplina) {
        String sql = "UPDATE disciplina SET nome = ?, carga_horaria = ?, ementa = ? WHERE codigo_disciplina = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNomeDisciplina());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setString(3, disciplina.getEmenta());
            stmt.setString(4, disciplina.getCodigo());


            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao editar disciplina: " + e.getMessage());
            return false;
        }
    }

    public boolean remover(String codigoDisciplina) {
        String sql = "DELETE FROM disciplina WHERE codigo_disciplina = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, codigoDisciplina);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover disciplina: " + e.getMessage());
            return false;
        }
    }
}
