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
        super("Disciplina");
        setTitle("Gerenciar Disciplinas");

        // Layout da tela
        setLayout(new BorderLayout());

        // Tabela para exibir as disciplinas
        configurarTabela();

        // Criar a barra de ferramentas
        JToolBar toolBar = new JToolBar();

        JButton botaoAdicionar = new JButton("Adicionar Disciplina");
        botaoAdicionar.addActionListener(e -> adicionar());

        JButton botaoEditar = new JButton("Editar Disciplina");
        botaoEditar.addActionListener(e -> editar());

        JButton botaoRemover = new JButton("Remover Disciplina");
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
        String[] colunas = {"Nome", "Código", "Carga Horária", "Ementa"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        tabela.setModel(modeloTabela);
    }

    protected void adicionar() {
        // Criar um formulário simples para adicionar disciplina
        JTextField campoNome = new JTextField();
        JTextField campoCodigo = new JTextField();
        JTextField campoCargaHoraria = new JTextField();
        JTextField campoEmenta = new JTextField();

        Object[] formulario = {
                "Nome:", campoNome,
                "Código:", campoCodigo,
                "Carga Horária:", campoCargaHoraria,
                "Ementa:", campoEmenta
        };

        int resultado = JOptionPane.showConfirmDialog(this, formulario, "Adicionar Disciplina", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try (Connection conexao = ConexaoBD.conectar()) {
                DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conexao);
                Disciplina novaDisciplina = new Disciplina(
                        campoNome.getText(),
                        campoCodigo.getText(),
                        Integer.parseInt(campoCargaHoraria.getText()),
                        campoEmenta.getText()
                );

                boolean sucesso = disciplinaDAO.adicionar(novaDisciplina);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Disciplina adicionada com sucesso!");
                    atualizarTabela(); // Atualiza a tabela após adicionar
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

            JTextField campoNome = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 0));
            JTextField campoCodigo = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 1));
            JTextField campoCargaHoraria = new JTextField(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            JTextField campoEmenta = new JTextField((String) modeloTabela.getValueAt(linhaSelecionada, 3));

            Object[] formulario = {
                    "Nome:", campoNome,
                    "Código:", campoCodigo,
                    "Carga Horária:", campoCargaHoraria,
                    "Ementa:", campoEmenta
            };

            int resultado = JOptionPane.showConfirmDialog(this, formulario, "Editar Disciplina", JOptionPane.OK_CANCEL_OPTION);

            if (resultado == JOptionPane.OK_OPTION) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conexao);
                    Disciplina disciplinaEditada = new Disciplina(
                            campoNome.getText(),
                            campoCodigo.getText(),
                            Integer.parseInt(campoCargaHoraria.getText()),
                            campoEmenta.getText()
                    );

                    boolean sucesso = disciplinaDAO.editar(disciplinaEditada);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Disciplina editada com sucesso!");
                        atualizarTabela(); // Atualiza a tabela após editar
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao editar disciplina.");
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
            String codigo = (String) modeloTabela.getValueAt(linhaSelecionada, 1); // Código na coluna 1

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
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina para remover.");
        }
    }

    protected void atualizarTabela() {
        try {
            Connection conexao = ConexaoBD.conectar();
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO(conexao);
            List<Disciplina> listaDisciplinas = disciplinaDAO.listar(); // Buscar a lista de disciplinas do DAO

            DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
            modeloTabela.setRowCount(0); // Limpar tabela antes de adicionar novos dados

            for (Disciplina disciplina : listaDisciplinas) {
                Object[] rowData = {
                        disciplina.getNomeDisciplina(),
                        disciplina.getCodigo(),
                        disciplina.getCargaHoraria(),
                        disciplina.getEmenta()
                };
                modeloTabela.addRow(rowData); // Adiciona a linha com os dados da disciplina
            }

            conexao.close(); // Fecha a conexão após a operação

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a lista de disciplinas: " + e.getMessage());
        }
    }
}
