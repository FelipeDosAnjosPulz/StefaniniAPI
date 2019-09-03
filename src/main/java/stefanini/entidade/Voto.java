package stefanini.entidade;

import stefanini.dto.request.RequestRegistroVotoDTO;
import stefanini.entidade.base.AbstractIEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "voto", indexes = {@Index(columnList = "idPauta, cpf", unique = true)})
public class Voto extends AbstractIEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 11)
    private String cpf;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestRegistroVotoDTO.OpcoesVoto voto;

    @Column(nullable = true)
    private int idPauta;

    public Voto() {
    }

    public Voto(String cpf, RequestRegistroVotoDTO.OpcoesVoto voto, int idPauta) {
        this.cpf = cpf;
        this.voto = voto;
        this.idPauta = idPauta;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RequestRegistroVotoDTO.OpcoesVoto getVoto() {
        return voto;
    }

    public void setVoto(RequestRegistroVotoDTO.OpcoesVoto voto) {
        this.voto = voto;
    }

    public int getId_pauta() {
        return idPauta;
    }

    public void setId_pauta(int id_pauta) {
        this.idPauta = id_pauta;
    }

}
