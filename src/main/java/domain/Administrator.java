package domain;

import java.util.Objects;

public class Administrator {

    private String numeUtilizator;
    private String parola;
    private String nume;
    private String prenume;
    private String email;

    public Administrator() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(numeUtilizator, that.numeUtilizator) && Objects.equals(parola, that.parola) && Objects.equals(nume, that.nume) && Objects.equals(prenume, that.prenume) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeUtilizator, parola, nume, prenume, email);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "numeUtilizator='" + numeUtilizator + '\'' +
                ", parola='" + parola + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
