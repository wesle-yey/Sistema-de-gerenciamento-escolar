package src.view;

import src.dao.AlunoDAO;
import src.db.ConexaoBD;
import src.model.Aluno;

import javax.swing.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.SQLException;

import static src.db.ConexaoBD.conectar;
import static src.db.CriarTabelas.criarTabelas;

public class TelaPrincipal extends JFrame {

    private JButton botaoAluno;
    private JButton botaoProfessor;
    private JButton botaoTurma;
    private JButton botaoDisciplina;

    public TelaPrincipal() {
        // Configurações da janela
        setTitle("Sistema de Gerenciamento Escolar");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela

        // Configurando o layout e os botões
        setLayout(new GridLayout(4, 1));

        botaoAluno = new JButton("Aluno");
        botaoProfessor = new JButton("Professor");
        botaoTurma = new JButton("Turma");
        botaoDisciplina = new JButton("Disciplina");

        // Adicionando botões à tela
        add(botaoAluno);
        add(botaoProfessor);
        add(botaoTurma);
        add(botaoDisciplina);

        // Dentro do ActionListener do botão Aluno
        botaoAluno.addActionListener(e -> {
            Connection conexao = ConexaoBD.conectar(); // Conecta ao banco
            AlunoDAO alunoDAO = new AlunoDAO(conexao);

            List<Aluno> alunos = alunoDAO.listar();
            // Abrir a tela de Aluno
            TelaAluno telaAluno = new TelaAluno();
            telaAluno.setVisible(true);
        });

        botaoProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir tela de professores
                TelaProfessor telaProfessor = new TelaProfessor();
                telaProfessor.setVisible(true);
            }
        });

        botaoTurma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir tela de turmas
                TelaTurma telaTurma = new TelaTurma();
                telaTurma.setVisible(true);
            }
        });

        botaoDisciplina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir tela de disciplinas
                TelaDisciplina telaDisciplina = new TelaDisciplina();
                telaDisciplina.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Connection con = conectar();
        criarTabelas(con);

        // Fechar a conexão quando não for mais necessária
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexão fechada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Abrir a tela principal na thread de evento do Swing
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}
