package src.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;

public class ConexaoBD {

    // Informações de conexão
    private static final String URL = "jdbc:mysql://localhost:3306/escola"; // Ajuste o nome do banco de dados
    private static final String USER = "root"; // Seu usuário MySQL
    private static final String PASSWORD = ""; // Sua senha MySQL

    // Método para estabelecer a conexão
    public static Connection conectar() {
        Connection conexao = null;
        try {
            // Carrega o driver MySQL (não é mais obrigatório, mas bom ter)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabelece a conexão
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão estabelecida com sucesso ao MySQL!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
        return conexao;
    }

    // Método para criar tabelas (exemplo)
    public static void criarTabelas(Connection conexao) {
        if (conexao != null) {
            try (Statement stmt = conexao.createStatement()) {
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

                System.out.println("Tabelas criadas com sucesso no MySQL!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Conexão não estabelecida. Não é possível criar tabelas.");
        }
    }

    // Método principal para teste
    public static void main(String[] args) {
        Connection conexao = conectar();
        criarTabelas(conexao);
        // Fechar a conexão quando não for mais necessária
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão fechada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


