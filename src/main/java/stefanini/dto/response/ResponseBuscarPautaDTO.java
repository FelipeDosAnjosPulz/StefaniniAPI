package stefanini.dto.response;

public class ResponseBuscarPautaDTO {

    private String nomePauta;
    private String descricaoPauta;
    private String dataInicioPauta;
    private String dataFimPauta;
    private String tempoRestante;
    private int totalVotos;
    private long votosSim;
    private long votosNao;

    public ResponseBuscarPautaDTO(String nomePauta, String descricaoPauta, String dataInicioPauta, String dataFimPauta, String tempoRestante, int totalVotos, long votosSim, long votosNao) {
        this.nomePauta = nomePauta;
        this.descricaoPauta = descricaoPauta;
        this.dataInicioPauta = dataInicioPauta;
        this.dataFimPauta = dataFimPauta;
        this.tempoRestante = tempoRestante;
        this.totalVotos = totalVotos;
        this.votosSim = votosSim;
        this.votosNao = votosNao;
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

    public String getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoRestante(String tempoRestante) {
        this.tempoRestante = tempoRestante;
    }

    public int getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(int totalVotos) {
        this.totalVotos = totalVotos;
    }

    public long getVotosSim() {
        return votosSim;
    }

    public void setVotosSim(long votosSim) {
        this.votosSim = votosSim;
    }

    public long getVotosNao() {
        return votosNao;
    }

    public void setVotosNao(long votosNao) {
        this.votosNao = votosNao;
    }

}
