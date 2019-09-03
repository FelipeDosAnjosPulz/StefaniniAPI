package stefanini.dto.request;

public class RequestRegistroPautaDTO {

    private String nomePauta;
    private String descricaoPauta;
    private int expirarPauta;

    public String getNomePauta() {
        return nomePauta;
    }

    public void setNomePauta(String nomePauta) {
        this.nomePauta = nomePauta;
    }

    public String getDescricaoPauta() {
        return descricaoPauta;
    }

    public void setDescricaoPauta(String descricaoPauta) {
        this.descricaoPauta = descricaoPauta;
    }

    public int getExpirarPauta() {
        return expirarPauta;
    }

    public void setExpirarPauta(int expirarPauta) {
        this.expirarPauta = expirarPauta;
    }

}