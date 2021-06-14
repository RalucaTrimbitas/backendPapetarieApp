package domain;

import domain.enums.StatusComanda;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comanda {

    StatusComanda status;
    private int numarComanda;
    private String numeUtilizatorClient;
    private LocalDateTime dataPlasare;
    private Float suma;
    private Float tva;
    private Float total;

    public Comanda() {
    }

    public int getNumarComanda() {
        return numarComanda;
    }

    public void setNumarComanda(int numarComanda) {
        this.numarComanda = numarComanda;
    }

    public String getNumeUtilizatorClient() {
        return numeUtilizatorClient;
    }

    public void setNumeUtilizatorClient(String numeUtilizatorClient) {
        this.numeUtilizatorClient = numeUtilizatorClient;
    }

    public LocalDateTime getDataPlasare() {
        return dataPlasare;
    }

    public void setDataPlasare(LocalDateTime dataPlasare) {
        this.dataPlasare = dataPlasare;
    }

    public Float getSuma() {
        return suma;
    }

    public void setSuma(Float suma) {
        this.suma = suma;
    }

    public Float getTva() {
        return tva;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public StatusComanda getStatus() {
        return status;
    }

    public void setStatus(StatusComanda status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comanda comanda = (Comanda) o;
        return numarComanda == comanda.numarComanda && Objects.equals(numeUtilizatorClient, comanda.numeUtilizatorClient) && Objects.equals(dataPlasare, comanda.dataPlasare) && Objects.equals(suma, comanda.suma) && Objects.equals(tva, comanda.tva) && Objects.equals(total, comanda.total) && status == comanda.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numarComanda, numeUtilizatorClient, dataPlasare, suma, tva, total, status);
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "numarComanda=" + numarComanda +
                ", numeUtilizatorClient='" + numeUtilizatorClient + '\'' +
                ", dataPlasare=" + dataPlasare +
                ", suma=" + suma +
                ", tva=" + tva +
                ", total=" + total +
                ", status=" + status +
                '}';
    }
}
