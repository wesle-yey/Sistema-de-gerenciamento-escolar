package src.db;

import java.sql.Connection;
import java.sql.Statement;

public class CriarTabelas {

    public static void criarTabelas(Connection conexao) {
        try {
            Statement stmt = conexao.createStatement();

            // Criação das tabelas
            String sqlAluno = "CREATE TABLE IF NOT EXISTS Aluno (" +
                    "matricula INT PRIMARY KEY," +
                    "nome VARCHAR(100)," +
                    "data_nascimento DATE," +
                    "endereco VARCHAR(255))";
            stmt.execute(sqlAluno);

            String sqlProfessor = "CREATE TABLE IF NOT EXISTS Professor (" +
                    "id INT PRIMARY KEY," +
                    "nome VARCHAR(100)," +
                    "especializacao VARCHAR(100)," +
                    "departamento VARCHAR(100))";
            stmt.execute(sqlProfessor);

            String sqlTurma = "CREATE TABLE IF NOT EXISTS Turma (" +
                    "codigo_turma INT PRIMARY KEY," +
                    "horario TIME," +
                    "sala VARCHAR(10)," +
                    "capacidade INT)";
            stmt.execute(sqlTurma);

            String sqlDisciplina = "CREATE TABLE IF NOT EXISTS Disciplina (" +
                    "codigo_disciplina INT PRIMARY KEY," +
                    "nome VARCHAR(100)," +
                    "carga_horaria INT," +
                    "ementa TEXT)";
            stmt.execute(sqlDisciplina);

            System.out.println("Tabelas criadas com sucesso!");
            stmt.close();
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

