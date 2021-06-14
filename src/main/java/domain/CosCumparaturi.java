package domain;

import java.util.Objects;

public class CosCumparaturi {
    private int idCosCumparaturi;
    private String numeUtilizatorClient;

    public CosCumparaturi() {
    }

    public int getIdCosCumparaturi() {
        return idCosCumparaturi;
    }

    public void setIdCosCumparaturi(int idCosCumparaturi) {
        this.idCosCumparaturi = idCosCumparaturi;
    }

    public String getNumeUtilizatorClient() {
        return numeUtilizatorClient;
    }

    public void setNumeUtilizatorClient(String numeUtilizatorClient) {
        this.numeUtilizatorClient = numeUtilizatorClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CosCumparaturi that = (CosCumparaturi) o;
        return Objects.equals(idCosCumparaturi, that.idCosCumparaturi) && Objects.equals(numeUtilizatorClient, that.numeUtilizatorClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCosCumparaturi, numeUtilizatorClient);
    }

    @Override
    public String toString() {
        return "CosCumparaturi{" +
                "idCosCumparaturi='" + idCosCumparaturi + '\'' +
                ", numeUtilizatorClient='" + numeUtilizatorClient + '\'' +
                '}';
    }
}
