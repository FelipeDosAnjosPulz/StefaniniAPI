package stefanini.dto.response;

public class ResponseRegistroVotoDTO {

    private String mensagem;

    public ResponseRegistroVotoDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
