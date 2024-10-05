package src.view;

import src.dao.DisciplinaDAO;
import src.db.ConexaoBD;
import src.model.Disciplina;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaDisciplina extends TelaGenerica {

    public TelaDisciplina() {
        super("Gerenciar Disciplinas");
        // Configuração adicional específica da disciplina, se houver
    }

    @Override
    protected void configurarTabela() {
        String[] colunas = {"Código", "Nome", "Carga Horária", "Ementa"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        tabela.setModel(modeloTabela);
    }

    @Override
    protected void adicionar() {
        JTextField campoCodigo = new JTextField();
        JTextField campoNome = new JTextField();
        JTextField campoCargaHoraria = new JTextField();
        JTextField campoEmenta = new JTextField();

        Object[] formulario = {
                "Código:", campoCodigo,
                "Nome:", campoNome,
                "Carga Horária:", campoCargaHoraria,
                "Ementa:", campoEmenta
        };

        int resultado = JOptionPane.showConfirmDialog(this, formulario, "Adicionar Disciplina", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try (Connection conexao = ConexaoBD.conectar()) {
                DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conexao);
                Disciplina novaDisciplina = new Disciplina(
                        campoCodigo.getText(),
                        campoNome.getText(),
                        Integer.parseInt(campoCargaHoraria.getText()),
                        campoEmenta.getText()
                );

                boolean sucesso = disciplinaDAO.adicionar(novaDisciplina);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Disciplina adicionada com sucesso!");
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar disciplina.");
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

            JLabel labelCodigo = new JLabel((String) modeloTabela.getValueAt(linhaSelecionada, 0));
            JTextField campoNome = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 1));
            JTextField campoCargaHoraria = new JTextField(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            JTextField campoEmenta = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 3));

            Object[] formulario = {
                    "Código:", labelCodigo,
                    "Nome:", campoNome,
                    "Carga Horária:", campoCargaHoraria,
                    "Ementa:", campoEmenta
            };

            int resultado = JOptionPane.showConfirmDialog(this, formulario, "Editar Disciplina", JOptionPane.OK_CANCEL_OPTION);

            if (resultado == JOptionPane.OK_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conexao);
                    Disciplina disciplinaEditada = new Disciplina(
                            labelCodigo.getText(),
                            campoNome.getText(),
                            Integer.parseInt(campoCargaHoraria.getText()),
                            campoEmenta.getText()
                    );

                    boolean sucesso = disciplinaDAO.editar(disciplinaEditada);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Disciplina editada com sucesso!");
                        atualizarTabela();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao editar disciplina.");
                        System.out.println(labelCodigo.getText());
                        System.out.println(campoNome.getText());
                        System.out.println(Integer.parseInt(campoCargaHoraria.getText()));
                                System.out.println(campoEmenta.getText());

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina para editar.");
        }
    }

    @Override
    protected void remover() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            String codigo = (String) modeloTabela.getValueAt(linhaSelecionada, 0);

            // Verifique o valor de "codigo" antes de passar para o DAO
            System.out.println("Código da disciplina selecionada: " + codigo);

            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a disciplina com código " + codigo + "?", "Remover Disciplina", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conexao);
                    boolean sucesso = disciplinaDAO.remover(codigo);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Disciplina removida com sucesso!");
                        atualizarTabela(); // Atualiza a tabela após remover
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao remover disciplina.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        }
    }

    @Override
    protected void atualizarTabela() {
        try (Connection conexao = ConexaoBD.conectar()) {
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conexao);
            List<Disciplina> listaDisciplinas = disciplinaDAO.listar();

            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            modeloTabela.setRowCount(0); // Limpar a tabela

            for (Disciplina disciplina : listaDisciplinas) {
                Object[] rowData = {
                        disciplina.getCodigo(),
                        disciplina.getNomeDisciplina(),
                        disciplina.getCargaHoraria(),
                        disciplina.getEmenta()
                };
                modeloTabela.addRow(rowData); // Adicionar linha
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a lista de disciplinas: " + e.getMessage());
        }
    }
}
