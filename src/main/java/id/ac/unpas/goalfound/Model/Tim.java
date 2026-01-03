/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.Model;

/**
 *
 * @author Muhammad Fauzan nur
 */

public class Tim {
    private int idTim;
    private String namaTim;
    private String fakultas;

    public Tim() {}

    public Tim(String namaTim, String fakultas) {
        this.namaTim = namaTim;
        this.fakultas = fakultas;
    }

    public Tim(int idTim, String namaTim, String fakultas) {
        this.idTim = idTim;
        this.namaTim = namaTim;
        this.fakultas = fakultas;
    }

    public int getIdTim() {
        return idTim;
    }

    public void setIdTim(int idTim) {
        this.idTim = idTim;
    }

    public String getNamaTim() {
        return namaTim;
    }

    public void setNamaTim(String namaTim) {
        this.namaTim = namaTim;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }
}

