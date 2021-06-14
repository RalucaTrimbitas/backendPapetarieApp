package domain;

import java.util.Objects;

public class CosCumparaturi_Produs {

    private String id_CosCumparaturi_Produs;
    private int idCosCumparaturi;
    private String idProdus;
    private int cantitate;

    public CosCumparaturi_Produs() {
    }

    public String getId_CosCumparaturi_Produs() {
        return id_CosCumparaturi_Produs;
    }

    public void setId_CosCumparaturi_Produs(String id_CosCumparaturi_Produs) {
        this.id_CosCumparaturi_Produs = id_CosCumparaturi_Produs;
    }

    public int getIdCosCumparaturi() {
        return idCosCumparaturi;
    }

    public void setIdCosCumparaturi(int idCosCumparaturi) {
        this.idCosCumparaturi = idCosCumparaturi;
    }

    public String getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(String idProdus) {
        this.idProdus = idProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CosCumparaturi_Produs that = (CosCumparaturi_Produs) o;
        return cantitate == that.cantitate && Objects.equals(id_CosCumparaturi_Produs, that.id_CosCumparaturi_Produs) && Objects.equals(idCosCumparaturi, that.idCosCumparaturi) && Objects.equals(idProdus, that.idProdus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_CosCumparaturi_Produs, idCosCumparaturi, idProdus, cantitate);
    }

    @Override
    public String toString() {
        return "CosCumparaturi_Produs{" +
                "id_CosCumparaturi_Produs='" + id_CosCumparaturi_Produs + '\'' +
                ", idCosCumparaturi='" + idCosCumparaturi + '\'' +
                ", idProdus='" + idProdus + '\'' +
                ", cantitate=" + cantitate +
                '}';
    }
}
