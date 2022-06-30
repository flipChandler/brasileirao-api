package br.com.felipesantos.brasileiraoapi.dto;

import br.com.felipesantos.brasileiraoapi.entities.Partida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaResponse {

    private List<Partida> partidas;
}
