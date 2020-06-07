package com.belen.proyecto;

public class Fase {
    private String fase;
    private String normas;

    public Fase(String fase, String normas){
        this.setFase(fase);
        this.setNormas(normas);
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getNormas() {
        return normas;
    }

    public void setNormas(String normas) {
        this.normas = normas;
    }
}
