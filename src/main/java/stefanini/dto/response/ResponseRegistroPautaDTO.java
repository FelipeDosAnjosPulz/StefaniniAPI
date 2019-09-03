package stefanini.dto.response;

public class ResponseRegistroPautaDTO {

    private String mensagem;

    public ResponseRegistroPautaDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
