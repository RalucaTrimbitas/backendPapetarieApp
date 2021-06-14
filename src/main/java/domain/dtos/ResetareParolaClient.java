package domain.dtos;

import java.util.Objects;

public class ResetareParolaClient {
    private String numeUtilizator;
    private String parola_actuala;
    private String parola_noua;
    private String confirmare_parola;

    public ResetareParolaClient() {
    }

    public String getParola_actuala() {
        return parola_actuala;
    }

    public void setParola_actuala(String parola_actuala) {
        this.parola_actuala = parola_actuala;
    }

    public String getParola_noua() {
        return parola_noua;
    }

    public void setParola_noua(String parola_noua) {
        this.parola_noua = parola_noua;
    }

    public String getConfirmare_parola() {
        return confirmare_parola;
    }

    public void setConfirmare_parola(String confirmare_parola) {
        this.confirmare_parola = confirmare_parola;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResetareParolaClient that = (ResetareParolaClient) o;
        return Objects.equals(numeUtilizator, that.numeUtilizator) && Objects.equals(parola_actuala, that.parola_actuala) && Objects.equals(parola_noua, that.parola_noua) && Objects.equals(confirmare_parola, that.confirmare_parola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeUtilizator, parola_actuala, parola_noua, confirmare_parola);
    }

    @Override
    public String toString() {
        return "ResetareParolaClient{" +
                "numeUtilizator='" + numeUtilizator + '\'' +
                ", parola_actuala='" + parola_actuala + '\'' +
                ", parola_noua='" + parola_noua + '\'' +
                ", confirmare_parola='" + confirmare_parola + '\'' +
                '}';
    }
}
