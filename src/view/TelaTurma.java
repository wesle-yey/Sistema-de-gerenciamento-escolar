package src.view;

import src.dao.TurmaDAO;
import src.db.ConexaoBD;
import src.model.Turma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaTurma extends TelaGenerica {
    public TelaTurma() {
        super("Turma");
        setTitle("Gerenciar Turmas");

        // Layout da tela
        setLayout(new BorderLayout());

        // Tabela para exibir as turmas
        configurarTabela();

        // Criar a barra de ferramentas
        JToolBar toolBar = new JToolBar();

        JButton botaoAdicionar = new JButton("Adicionar Turma");
        botaoAdicionar.addActionListener(e -> adicionar());

        JButton botaoEditar = new JButton("Editar Turma");
        botaoEditar.addActionListener(e -> editar());

        JButton botaoRemover = new JButton("Remover Turma");
        botaoRemover.addActionListener(e -> remover());

        JButton botaoAtualizar = new JButton("Atualizar Lista");
        botaoAtualizar.addActionListener(e -> atualizarTabela());

        toolBar.add(botaoAdicionar);
        toolBar.add(botaoEditar);
        toolBar.add(botaoRemover);
        toolBar.add(botaoAtualizar);

        // Adicionando a barra de ferramentas e a tabela na tela
        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    @Override
    protected void configurarTabela() {
        String[] colunas = {"Código", "Horário", "Sala", "Capacidade"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        tabela.setModel(modeloTabela);
    }

    protected void adicionar() {
        // Criar um formulário simples para adicionar turma
        JTextField campoCodigo = new JTextField();
        JTextField campoHorario = new JTextField();
        JTextField campoSala = new JTextField();
        JTextField campoCapacidade = new JTextField();

        Object[] formulario = {
                "Código:", campoCodigo,
                "Horário:", campoHorario,
                "Sala:", campoSala,
                "Capacidade:", campoCapacidade
        };

        int resultado = JOptionPane.showConfirmDialog(this, formulario, "Adicionar Turma", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try (Connection conexao = ConexaoBD.conectar()) {
                TurmaDAO turmaDAO = new TurmaDAO(conexao);
                Turma novaTurma = new Turma(
                        campoCodigo.getText(),
                        campoHorario.getText(),
                        campoSala.getText(),
                        Integer.parseInt(campoCapacidade.getText())
                );

                boolean sucesso = turmaDAO.adicionar(novaTurma);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Turma adicionada com sucesso!");
                    atualizarTabela(); // Atualiza a tabela após adicionar
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar turma.");
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

            JTextField campoCodigo = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 0));
            JTextField campoHorario = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 1));
            JTextField campoSala = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 2));
            JTextField campoCapacidade = new JTextField(modeloTabela.getValueAt(linhaSelecionada, 3).toString());

            Object[] formulario = {
                    "Código:", campoCodigo,
                    "Horário:", campoHorario,
                    "Sala:", campoSala,
                    "Capacidade:", campoCapacidade
            };

            int resultado = JOptionPane.showConfirmDialog(this, formulario, "Editar Turma", JOptionPane.OK_CANCEL_OPTION);

            if (resultado == JOptionPane.OK_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    TurmaDAO turmaDAO = new TurmaDAO(conexao);
                    Turma turmaEditada = new Turma(
                            campoCodigo.getText(),
                            campoHorario.getText(),
                            campoSala.getText(),
                            Integer.parseInt(campoCapacidade.getText())
                    );

                    boolean sucesso = turmaDAO.editar(turmaEditada);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Turma editada com sucesso!");
                        atualizarTabela(); // Atualiza a tabela após editar
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao editar turma.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma turma para editar.");
        }
    }

    @Override
    protected void remover() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            String codigo = (String) modeloTabela.getValueAt(linhaSelecionada, 0);

            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a turma com código " + codigo + "?", "Remover Turma", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    TurmaDAO turmaDAO = new TurmaDAO(conexao);
                    boolean sucesso = turmaDAO.remover(codigo);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Turma removida com sucesso!");
                        atualizarTabela(); // Atualiza a tabela após remover
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao remover turma.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma turma para remover.");
        }
    }

    protected void atualizarTabela() {
        try {
            Connection conexao = ConexaoBD.conectar();
            TurmaDAO turmaDAO = new TurmaDAO(conexao);
            List<Turma> listaTurmas = turmaDAO.listar(); // Buscar a lista de turmas do DAO

            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            modeloTabela.setRowCount(0); // Limpar tabela antes de adicionar novos dados

            for (Turma turma : listaTurmas) {
                Object[] rowData = {
                        turma.getCodigoTurma(),
                        turma.getHorario(),
                        turma.getSala(),
                        turma.getCapacidade()
                };
                modeloTabela.addRow(rowData); // Adiciona a linha com os dados da turma
            }

            conexao.close(); // Fecha a conexão após a operação

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a lista de turmas: " + e.getMessage());
        }
    }
}
