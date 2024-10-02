package src.model;

public class Professor {
    private String nome;
    private String id;
    private String especializacao;
    private String departamento;

    // Construtor
    public Professor(String nome, String id, String especializacao, String departamento) {
        this.nome = nome;
        this.id = id;
        this.especializacao = especializacao;
        this.departamento = departamento;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
