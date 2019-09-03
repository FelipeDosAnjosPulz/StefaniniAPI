package stefanini.dto.scheduled;

import stefanini.entidade.Pauta;
import stefanini.entidade.Voto;

import java.util.List;

public class PautaScheduledDTO {

    private int id;
    private List<Voto> votos;
    private String nomePauta;
    private String descricaoPauta;
    private Pauta.Situacao situacao;
    private String dataInicioPauta;
    private String dataFimPauta;

    public PautaScheduledDTO() {
    }

    public PautaScheduledDTO(int id, List<Voto> votos, String nomePauta, String descricaoPauta, Pauta.Situacao situacao, String dataInicioPauta, String dataFimPauta) {
        this.id = id;
        this.votos = votos;
        this.nomePauta = nomePauta;
        this.descricaoPauta = descricaoPauta;
        this.situacao = situacao;
        this.dataInicioPauta = dataInicioPauta;
        this.dataFimPauta = dataFimPauta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

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

    public Pauta.Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Pauta.Situacao situacao) {
        this.situacao = situacao;
    }

    public String getDataInicioPauta() {
        return dataInicioPauta;
    }

    public void setDataInicioPauta(String dataInicioPauta) {
        this.dataInicioPauta = dataInicioPauta;
    }

    public String getDataFimPauta() {
        return dataFimPauta;
    }

    public void setDataFimPauta(String dataFimPauta) {
        this.dataFimPauta = dataFimPauta;
    }

}
