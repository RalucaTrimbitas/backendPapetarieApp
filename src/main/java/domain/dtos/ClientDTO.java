package domain.dtos;

import domain.enums.TipClient;

public class ClientDTO {

    private String prenume;
    private String nume;
    private String numeUtilizator;
    private String parola;
    private String email;
    private String confirmParola;
    private TipClient tip;
    private String companie;
    private String codFiscal;
    private String numarTelefon;

    public ClientDTO() {

    }

    public TipClient getTip() {
        return tip;
    }

    public void setTip(TipClient tip) {
        this.tip = tip;
    }

    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
    }

    public String getCodFiscal() {
        return codFiscal;
    }

    public void setCodFiscal(String codFiscal) {
        this.codFiscal = codFiscal;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmParola() {
        return confirmParola;
    }

    public void setConfirmParola(String confirmParola) {
        this.confirmParola = confirmParola;
    }
}
