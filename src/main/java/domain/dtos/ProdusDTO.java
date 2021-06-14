package domain.dtos;

public class ProdusDTO {

    private String codDeBare;
    private String denumire;
    private Float pret;
    private String descriere;
    private String src;
    private String idCategorieProdus;
    private String detalii;

    public ProdusDTO() {
    }

    @Override
    public String toString() {
        return "ProdusDTO{" +
                "codDeBare='" + codDeBare + '\'' +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", descriere='" + descriere + '\'' +
                ", src='" + src + '\'' +
                ", idCategorieProdus='" + idCategorieProdus + '\'' +
                ", detalii='" + detalii + '\'' +
                '}';
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public String getIdCategorieProdus() {
        return idCategorieProdus;
    }

    public void setIdCategorieProdus(String idCategorieProdus) {
        this.idCategorieProdus = idCategorieProdus;
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

}
