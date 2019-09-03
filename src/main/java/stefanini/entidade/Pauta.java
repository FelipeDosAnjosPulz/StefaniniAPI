package stefanini.entidade;

import org.hibernate.annotations.Type;
import stefanini.entidade.base.AbstractIEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pauta")
public class Pauta extends AbstractIEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //remove filhos de Pauta e depois pauta
    @OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "idPauta")
    @Column(nullable = true)
    private List<Voto> votos;

    @NotNull
    @Size(max = 100)
    private String nomePauta;

    @Size(max = 1000)
    private String descricaoPauta;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @NotNull
    @Type(type = "stefanini.util.LocalDateTimeUserType")
    private LocalDateTime dataInicioPauta;

    @NotNull
    @Type(type = "stefanini.util.LocalDateTimeUserType")
    private LocalDateTime dataFimPauta;

    public Pauta() {
    }

    public Pauta(String nomePauta, String descricaoPauta, LocalDateTime dataInicioPauta, LocalDateTime dataFimPauta, Situacao situacao) {
        this.nomePauta = nomePauta;
        this.descricaoPauta = descricaoPauta;
        this.dataInicioPauta = dataInicioPauta;
        this.dataFimPauta = dataFimPauta;
        this.situacao = situacao;
    }

    public Pauta(List<Voto> votos, String nomePauta, String descricaoPauta, LocalDateTime dataInicioPauta, LocalDateTime dataFimPauta) {
        this.votos = votos;
        this.nomePauta = nomePauta;
        this.descricaoPauta = descricaoPauta;
        this.dataInicioPauta = dataInicioPauta;
        this.dataFimPauta = dataFimPauta;
    }

    public enum Situacao {
        ABERTA("ABERTA"), ENCERRADA("ENCERRADA");
        private final String valor;

        Situacao(String valorOpcao) {
            valor = valorOpcao;
        }

        public String getValor() {
            return valor;
        }
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

    public LocalDateTime getDataInicioPauta() {
        return dataInicioPauta;
    }

    public void setDataInicioPauta(LocalDateTime dataInicioPauta) {
        this.dataInicioPauta = dataInicioPauta;
    }

    public LocalDateTime getDataFimPauta() {
        return dataFimPauta;
    }

    public void setDataFimPauta(LocalDateTime dataFimPauta) {
        this.dataFimPauta = dataFimPauta;
    }

    @NotNull
    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(@NotNull Situacao situacao) {
        this.situacao = situacao;
    }

}
