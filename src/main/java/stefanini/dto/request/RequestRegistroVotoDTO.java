package stefanini.dto.request;

public class RequestRegistroVotoDTO {

    private String nomePauta;
    private OpcoesVoto voto;
    private String cpf;

    public String getNomePauta() {
        return nomePauta;
    }

    public void setNomePauta(String nomePauta) {
        this.nomePauta = nomePauta;
    }

    public OpcoesVoto getVoto() {
        return voto;
    }

    public void setVoto(OpcoesVoto voto) {
        this.voto = voto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public enum OpcoesVoto {
        SIM("SIM"), NAO("NAO");

        private final String valor;

        OpcoesVoto(String valorOpcao) {
            valor = valorOpcao;
        }

        public String getValor() {
            return valor;
        }
    }

}
