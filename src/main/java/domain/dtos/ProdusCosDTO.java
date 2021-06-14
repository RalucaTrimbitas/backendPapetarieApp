package domain.dtos;

import java.util.Objects;

public class ProdusCosDTO {
    private String codDeBare;
    private String denumire;
    private Float pret;
    private String descriere;
    private String src;
    private String detalii;
    private Integer cantitate;
    private String idCosCumparaturiProdus;


    public ProdusCosDTO() {
    }

    public String getCodDeBare() {
        return codDeBare;
    }

    public void setCodDeBare(String codDeBare) {
        this.codDeBare = codDeBare;
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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public String getIdCosCumparaturiProdus() {
        return idCosCumparaturiProdus;
    }

    public void setIdCosCumparaturiProdus(String idCosCumparaturiProdus) {
        this.idCosCumparaturiProdus = idCosCumparaturiProdus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdusCosDTO that = (ProdusCosDTO) o;
        return Objects.equals(codDeBare, that.codDeBare) && Objects.equals(denumire, that.denumire) && Objects.equals(pret, that.pret) && Objects.equals(descriere, that.descriere) && Objects.equals(src, that.src) && Objects.equals(detalii, that.detalii) && Objects.equals(cantitate, that.cantitate) && Objects.equals(idCosCumparaturiProdus, that.idCosCumparaturiProdus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codDeBare, denumire, pret, descriere, src, detalii, cantitate, idCosCumparaturiProdus);
    }

    @Override
    public String toString() {
        return "ProdusCosDTO{" +
                "codDeBare='" + codDeBare + '\'' +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", descriere='" + descriere + '\'' +
                ", src='" + src + '\'' +
                ", detalii='" + detalii + '\'' +
                ", cantitate=" + cantitate +
                ", idCosCumparaturiProdus='" + idCosCumparaturiProdus + '\'' +
                '}';
    }
}
