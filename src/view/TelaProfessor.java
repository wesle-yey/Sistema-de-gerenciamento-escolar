package src.view;

import src.dao.ProfessorDAO;
import src.db.ConexaoBD;
import src.model.Professor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaProfessor extends TelaGenerica {
    public TelaProfessor() {
        super("Professor");
        setTitle("Gerenciar Professores");
    }

    @Override
    protected void configurarTabela() {
        String[] colunas = {"ID", "Nome", "Especialização", "Departamento"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        tabela.setModel(modeloTabela);

        btnListar.addActionListener(e -> atualizarTabela());
    }

    @Override
    protected void adicionar() {
        JTextField campoId = new JTextField();
        JTextField campoNome = new JTextField();
        JTextField campoEspecializacao = new JTextField();
        JTextField campoDepartamento = new JTextField();

        Object[] formulario = {
                "ID:", campoId,
                "Nome:", campoNome,
                "Especialização:", campoEspecializacao,
                "Departamento:", campoDepartamento
        };

        int resultado = JOptionPane.showConfirmDialog(this, formulario, "Adicionar Professor", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try (Connection conexao = ConexaoBD.conectar()) {
                ProfessorDAO professorDAO = new ProfessorDAO(conexao);
                Professor novoProfessor = new Professor(
                        campoNome.getText(),
                        Integer.parseInt(campoId.getText()),
                        campoEspecializacao.getText(),
                        campoDepartamento.getText()
                );

                boolean sucesso = professorDAO.adicionar(novoProfessor);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Professor adicionado com sucesso!");
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar professor.");
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

            JTextField campoNome = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 1));
            JTextField campoEspecializacao = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 2));
            JTextField campoDepartamento = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 3));

            Object[] formulario = {
                    "Nome:", campoNome,
                    "Especialização:", campoEspecializacao,
                    "Departamento:", campoDepartamento
            };

            int resultado = JOptionPane.showConfirmDialog(this, formulario, "Editar Professor", JOptionPane.OK_CANCEL_OPTION);

            if (resultado == JOptionPane.OK_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    ProfessorDAO professorDAO = new ProfessorDAO(conexao);
                    Professor professorEditado = new Professor(
                            campoNome.getText(),
                            (int) modeloTabela.getValueAt(linhaSelecionada, 0),
                            campoEspecializacao.getText(),
                            campoDepartamento.getText()
                    );

                    boolean sucesso = professorDAO.editar(professorEditado);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Professor editado com sucesso!");
                        atualizarTabela();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao editar professor.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um professor para editar.");
        }
    }

    @Override
    protected void remover() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);

            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover o professor com ID " + id + "?", "Remover Professor", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    ProfessorDAO professorDAO = new ProfessorDAO(conexao);
                    boolean sucesso = professorDAO.remover(id);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Professor removido com sucesso!");
                        atualizarTabela();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao remover professor.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um professor para remover.");
        }
    }

    protected void atualizarTabela() {
        try (Connection conexao = ConexaoBD.conectar()) {
            ProfessorDAO professorDAO = new ProfessorDAO(conexao);
            List<Professor> listaProfessores = professorDAO.listar();

            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            modeloTabela.setRowCount(0);

            for (Professor professor : listaProfessores) {
                Object[] rowData = {
                        professor.getId(),
                        professor.getNome(),
                        professor.getEspecializacao(),
                        professor.getDepartamento()
                };
                modeloTabela.addRow(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a lista de professores: " + e.getMessage());
        }
    }
}
