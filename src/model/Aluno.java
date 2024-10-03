package src.model;

import java.time.LocalDate;

public class Aluno {
    private int matricula; // Antes era String, agora é int
    private String nome;
    private LocalDate dataNascimento;
    private String endereco;

    // Construtor
    public Aluno(int matricula, String nome, LocalDate dataNascimento, String endereco) {
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    // Getters e Setters
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
