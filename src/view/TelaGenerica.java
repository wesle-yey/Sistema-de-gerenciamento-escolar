package src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TelaGenerica extends JFrame {
    protected JButton btnAdicionar;
    protected JButton btnEditar;
    protected JButton btnExcluir;
    protected JTable tabela;

    public TelaGenerica(String titulo) {
        setTitle(titulo);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Criando os botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        // Adiciona tabela genérica (pode ser customizada em subclasses)
        tabela = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabela);

        // Adiciona os componentes na tela
        add(painelBotoes, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Métodos para customizar o comportamento podem ser definidos e sobrescritos nas subclasses
    protected void configurarTabela() throws SQLException {
        // Implementar nas classes que herdarem
    }
}

