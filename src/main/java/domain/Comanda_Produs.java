package domain;

import java.util.Objects;

public class Comanda_Produs {
    private String id_Comanda_Produs;
    private int idComanda;
    private String idProdus;
    private int cantitate;

    public Comanda_Produs() {
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getId_Comanda_Produs() {
        return id_Comanda_Produs;
    }

    public void setId_Comanda_Produs(String id_Comanda_Produs) {
        this.id_Comanda_Produs = id_Comanda_Produs;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public String getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(String idProdus) {
        this.idProdus = idProdus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comanda_Produs that = (Comanda_Produs) o;
        return idComanda == that.idComanda && cantitate == that.cantitate && Objects.equals(id_Comanda_Produs, that.id_Comanda_Produs) && Objects.equals(idProdus, that.idProdus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Comanda_Produs, idComanda, idProdus, cantitate);
    }

    @Override
    public String toString() {
        return "Comanda_Produs{" +
                "id_Comanda_Produs='" + id_Comanda_Produs + '\'' +
                ", idComanda=" + idComanda +
                ", idProdus='" + idProdus + '\'' +
                ", cantitate=" + cantitate +
                '}';
    }
}
