package domain;

import java.util.Objects;

public class Categorie {

    private String idCategorie;
    private String denumire;
    private String descriere;

    public Categorie() {
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
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
        Categorie categorie = (Categorie) o;
        return Objects.equals(idCategorie, categorie.idCategorie) && Objects.equals(denumire, categorie.denumire) && Objects.equals(descriere, categorie.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategorie, denumire, descriere);
    }
}
