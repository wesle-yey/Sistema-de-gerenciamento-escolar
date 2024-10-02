package src.model;

public class Turma {
    private String codigoTurma;
    private String horario;
    private String sala;
    private int capacidade;

    // Construtor
    public Turma(String codigoTurma, String horario, String sala, int capacidade) {
        this.codigoTurma = codigoTurma;
        this.horario = horario;
        this.sala = sala;
        this.capacidade = capacidade;
    }

    // Getters e Setters
    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
}

