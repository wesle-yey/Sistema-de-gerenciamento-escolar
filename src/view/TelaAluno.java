package src.view;

import src.dao.AlunoDAO;
import src.db.ConexaoBD;
import src.model.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TelaAluno extends TelaGenerica {

    public TelaAluno() {
        super("Aluno");
        setTitle("Gerenciar Alunos");

        // Configurar a tabela (definido no método configurarTabela)
        configurarTabela();

        // Adiciona ação ao botão de listar
        btnListar.addActionListener(e -> atualizarTabela());
    }

    @Override
    protected void configurarTabela() {
        // Definindo as colunas da tabela para alunos
        String[] colunas = {"Matrícula", "Nome", "Data de Nascimento", "Endereço"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        tabela.setModel(modeloTabela);
    }

    @Override
    public void adicionar() {
        // Criar um formulário simples para adicionar aluno
        JTextField campoMatricula = new JTextField();
        JTextField campoNome = new JTextField();
        JTextField campoDataNascimento = new JTextField();
        JTextField campoEndereco = new JTextField();

        Object[] formulario = {
                "Matrícula:", campoMatricula,
                "Nome:", campoNome,
                "Data de Nascimento (yyyy-mm-dd):", campoDataNascimento,
                "Endereço:", campoEndereco
        };

        int resultado = JOptionPane.showConfirmDialog(this, formulario, "Adicionar Aluno", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try (Connection conexao = ConexaoBD.conectar()) {
                AlunoDAO alunoDAO = new AlunoDAO(conexao);
                Aluno novoAluno = new Aluno(
                        Integer.parseInt(campoMatricula.getText()),
                        campoNome.getText(),
                        Date.valueOf(campoDataNascimento.getText()).toLocalDate(),
                        campoEndereco.getText()
                );

                boolean sucesso = alunoDAO.adicionar(novoAluno);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Aluno adicionado com sucesso!");
                    atualizarTabela(); // Atualiza a tabela após adicionar
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar aluno.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }

    @Override
    protected void editar() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();

            // Recupera os valores atuais do aluno selecionado
            String matricula = modeloTabela.getValueAt(linhaSelecionada, 0).toString(); // Matricula não editável
            JTextField campoNome = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 1));
            JTextField campoDataNascimento = new JTextField(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            JTextField campoEndereco = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 3));

            // Exibe a matrícula como JLabel para visualização, mas não editável
            Object[] formulario = {
                    "Matrícula (não editável):", new JLabel(matricula),
                    "Nome:", campoNome,
                    "Data de Nascimento (yyyy-mm-dd):", campoDataNascimento,
                    "Endereço:", campoEndereco
            };

            int resultado = JOptionPane.showConfirmDialog(this, formulario, "Editar Aluno", JOptionPane.OK_CANCEL_OPTION);

            if (resultado == JOptionPane.OK_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    AlunoDAO alunoDAO = new AlunoDAO(conexao);
                    Aluno alunoEditado = new Aluno(
                            Integer.parseInt(matricula), // Mantém a matrícula original
                            campoNome.getText(),
                            Date.valueOf(campoDataNascimento.getText()).toLocalDate(),
                            campoEndereco.getText()
                    );

                    boolean sucesso = alunoDAO.editar(alunoEditado);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Aluno editado com sucesso!");
                        atualizarTabela(); // Atualiza a tabela após editar
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao editar aluno.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para editar.");
        }
    }

    @Override
    protected void remover() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            int matricula = Integer.parseInt(modeloTabela.getValueAt(linhaSelecionada, 0).toString());

            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover o aluno com matrícula " + matricula + "?", "Remover Aluno", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    AlunoDAO alunoDAO = new AlunoDAO(conexao);
                    boolean sucesso = alunoDAO.remover(matricula);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Aluno removido com sucesso!");
                        atualizarTabela(); // Atualiza a tabela após remover
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao remover aluno.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para remover.");
        }
    }

    protected void atualizarTabela() {
        try {
            Connection conexao = ConexaoBD.conectar();
            AlunoDAO alunoDAO = new AlunoDAO(conexao);
            List<Aluno> listaAlunos = alunoDAO.listar();

            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            modeloTabela.setRowCount(0);

            for (Aluno aluno : listaAlunos) {
                Object[] rowData = {
                        aluno.getMatricula(),
                        aluno.getNome(),
                        aluno.getDataNascimento(),
                        aluno.getEndereco()
                };
                modeloTabela.addRow(rowData);
            }

            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a lista de alunos: " + e.getMessage());
        }
    }
}

