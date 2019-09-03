package stefanini.exception;

public enum ApplicationBusinessExceptionCode {

    ERRO_NOME_PAUTA("Favor informar o nome da pauta."),
    ERRO_NOME_PAUTA_LENGHT("O nome da pauta deve ter entre 1 e 100 caracteres."),
    ERRO_DESCRICAO_PAUTA_LENGHT("A descrição da pauta deve ter entre 0 e 1000 caracteres."),
    ERRO_QUANTIDADE_REGISTRO_PAUTA("Pauta não encontrada."),
    ERRO_CPF_JA_VOTOU_PAUTA("CPF já votou nesta pauta."),
    ERRO_PAUTA_JA_REGISTRADA("Esta pauta já foi registrada."),
    ERRO_CPF_NAO_PODE_VOTAR("CPF inválido para votação."),
    ERRO_PAUTA_EXPIRADA("Essa pauta já foi encerrada."),
    ERRO_VOTO("O voto deve ser SIM ou NAO."),
    ERRO_CPF("Favor informar o CPF."),
    ERRO_CPF_LENGHT("O CPF deve ter 11.");

    private String errorCode;

    ApplicationBusinessExceptionCode(String codeError) {
        this.errorCode = codeError;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

}
