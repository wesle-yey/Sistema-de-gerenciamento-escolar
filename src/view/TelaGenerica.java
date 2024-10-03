package src.view;


import javax.swing.*;
import java.awt.*;

public abstract class TelaGenerica extends JFrame {

    protected JTable tabela;
    protected JButton btnAdicionar;
    protected JButton btnEditar;
    protected JButton btnRemover;
    protected JButton btnListar;

    public TelaGenerica(String titulo) {
        super(titulo);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar layout
        setLayout(new BorderLayout());

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());

        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnRemover = new JButton("Remover");
        btnListar = new JButton("Listar");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnListar);

        // Adicionar painel de botões ao topo
        add(painelBotoes, BorderLayout.NORTH);

        // Configurar tabela
        tabela = new JTable();
        JScrollPane painelTabela = new JScrollPane(tabela);
        add(painelTabela, BorderLayout.CENTER);

        // Método abstrato para configurar a tabela de acordo com a tela
        configurarTabela();

        // Ações dos botões
        btnAdicionar.addActionListener(e -> adicionar());
        btnEditar.addActionListener(e -> editar());
        btnRemover.addActionListener(e -> remover());
        btnListar.addActionListener(e -> atualizarTabela());
    }

    // Método para configurar colunas da tabela (a ser implementado por subclasses)
    protected abstract void configurarTabela();

    // Métodos abstratos para implementar em subclasses
    protected abstract void adicionar();

    protected abstract void editar();

    protected abstract void remover();

    // Método para atualizar os dados da tabela (pode ser sobrescrito pelas subclasses se necessário)
    protected void atualizarTabela() {
        // Subclasses podem sobrescrever para atualizar a tabela com dados do banco
    }
}



