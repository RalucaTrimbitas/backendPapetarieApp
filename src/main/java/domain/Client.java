package domain;

import domain.enums.TipClient;

import java.util.Objects;

public class Client {

    private String numeUtilizator;
    private String parola;
    private String nume;
    private String prenume;
    private TipClient tip;
    private String email;
    private String numarTelefon;
    private String adresa;
    private String companie;
    private String codFiscal;

    public Client() {
    }

    public Client(String numeUtilizator, String parola, String nume, String prenume, String email, TipClient tip, String companie, String codFiscal, String numarTelefon) {
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.tip = tip;
        this.companie = companie;
        this.codFiscal = codFiscal;
        this.numarTelefon = numarTelefon;
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

    public TipClient getTip() {
        return tip;
    }

    public void setTip(TipClient tip) {
        this.tip = tip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(numeUtilizator, client.numeUtilizator) && Objects.equals(parola, client.parola) && Objects.equals(nume, client.nume) && Objects.equals(prenume, client.prenume) && tip == client.tip && Objects.equals(email, client.email) && Objects.equals(numarTelefon, client.numarTelefon) && Objects.equals(adresa, client.adresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeUtilizator, parola, nume, prenume, tip, email, numarTelefon, adresa);
    }

    @Override
    public String toString() {
        return "Client{" +
                "numeUtilizator='" + numeUtilizator + '\'' +
                ", parola='" + parola + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", tip=" + tip +
                ", email='" + email + '\'' +
                ", numarTelefon='" + numarTelefon + '\'' +
                ", adresa='" + adresa + '\'' +
                '}';
    }
}


