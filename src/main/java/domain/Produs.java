package domain;

import java.util.Objects;

public class Produs {
    private String codDeBare;
    private String numeUtilizatorAdministrator;
    private String idCategorieProdus;
    private String denumire;
    private Float pret;
    private String descriere;
    private String detalii;
    private String src;

    public Produs() {
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCodDeBare() {
        return codDeBare;
    }

    public void setCodDeBare(String codDeBare) {
        this.codDeBare = codDeBare;
    }

    public String getNumeUtilizatorAdministrator() {
        return numeUtilizatorAdministrator;
    }

    public void setNumeUtilizatorAdministrator(String numeUtilizatorAdministrator) {
        this.numeUtilizatorAdministrator = numeUtilizatorAdministrator;
    }

    public String getIdCategorieProdus() {
        return idCategorieProdus;
    }

    public void setIdCategorieProdus(String idCategorieProdus) {
        this.idCategorieProdus = idCategorieProdus;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Float getPret() {
        return pret;
    }

    public void setPret(Float pret) {
        this.pret = pret;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produs produs = (Produs) o;
        return Objects.equals(codDeBare, produs.codDeBare) && Objects.equals(numeUtilizatorAdministrator, produs.numeUtilizatorAdministrator) && Objects.equals(idCategorieProdus, produs.idCategorieProdus) && Objects.equals(denumire, produs.denumire) && Objects.equals(pret, produs.pret) && Objects.equals(descriere, produs.descriere) && Objects.equals(detalii, produs.detalii) && Objects.equals(src, produs.src);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codDeBare, numeUtilizatorAdministrator, idCategorieProdus, denumire, pret, descriere, detalii, src);
    }

    @Override
    public String toString() {
        return "Produs{" +
                "codDeBare='" + codDeBare + '\'' +
                ", numeUtilizatorAdministrator='" + numeUtilizatorAdministrator + '\'' +
                ", idCategorieProdus='" + idCategorieProdus + '\'' +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", descriere='" + descriere + '\'' +
                ", detalii='" + detalii + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}

