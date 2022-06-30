package br.com.felipesantos.brasileiraoapi.dto;

import br.com.felipesantos.brasileiraoapi.util.StatusPartida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaGoogleDTO {

    private StatusPartida statusPartida;
    private String tempoPartida;
    // info equipe casa
    private String nomeEquipeCasa;
    private String urlLogoEquipeCasa;
    private Integer placarEquipeCasa;
    private String golsEquipeCasa;
    private Integer placarEstendidoEquipeCasa;
    // info equipe visitante
    private String nomeEquipeVisitante;
    private String urlLogoEquipeVisitante;
    private Integer placarEquipeVisitante;
    private String golsEquipeVisitante;
    private Integer placarEstendidoEquipeVisitante;
}
