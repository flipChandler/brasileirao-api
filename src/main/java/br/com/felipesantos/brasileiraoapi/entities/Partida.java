package br.com.felipesantos.brasileiraoapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partida")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partida_id")
    private Long id;

    @Transient
    private String statusPartida;

    @ManyToOne
    @JoinColumn(name = "equipe_casa_id")
    private Equipe equipeCasa;

    @ManyToOne
    @JoinColumn(name = "equipe_visitante_id")
    private Equipe equipeVisitante;

    @Column(name = "placar_equipe_casa")
    private Integer placarEquipeCasa;

    @Column(name = "placar_equipe_visitante")
    private Integer placarEquipeVisitante;

    @Column(name = "gols_equipe_casa")
    private String golsEquipeCasa;

    @Column(name = "gols_equipe_visitante")
    private String golsEquipeVisitante;

    @Column(name = "placar_estendido_equipe_casa")
    private Integer placarEstendidoEquipeCasa;

    @Column(name = "placar_estendido_equipe_visitante")
    private Integer placarEstendidoEquipeVisitante;

    @ApiModelProperty(example = "dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "America/Sao_Paulo")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora_partida")
    private Date dataHoraPartida;

    @Column(name = "local_partida")
    private String localPartida;

    @Column(name = "tempo_partida")
    private String tempoPartida;
}
