/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.goalfound.Model;

/**
 *
 * @author Muhammad Fauzan nur
 */


public class Pemain {

    private int idPemain;
    private int idTim;
    private String namaPemain;
    private String npm;
    private int noPunggung;

    public Pemain() {}

    public Pemain(int idTim, String namaPemain, String npm, int noPunggung) {
        this.idTim = idTim;
        this.namaPemain = namaPemain;
        this.npm = npm;
        this.noPunggung = noPunggung;
    }

    public Pemain(int idPemain, int idTim, String namaPemain, String npm, int noPunggung) {
        this.idPemain = idPemain;
        this.idTim = idTim;
        this.namaPemain = namaPemain;
        this.npm = npm;
        this.noPunggung = noPunggung;
    }

    public int getIdPemain() {
        return idPemain;
    }

    public void setIdPemain(int idPemain) {
        this.idPemain = idPemain;
    }

    public int getIdTim() {
        return idTim;
    }

    public void setIdTim(int idTim) {
        this.idTim = idTim;
    }

    public String getNamaPemain() {
        return namaPemain;
    }

    public void setNamaPemain(String namaPemain) {
        this.namaPemain = namaPemain;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public int getNoPunggung() {
        return noPunggung;
    }

    public void setNoPunggung(int noPunggung) {
        this.noPunggung = noPunggung;
    }
}

