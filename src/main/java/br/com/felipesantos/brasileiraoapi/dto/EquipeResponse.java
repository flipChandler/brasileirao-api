package br.com.felipesantos.brasileiraoapi.dto;

import br.com.felipesantos.brasileiraoapi.entities.Equipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipeResponse {

    private List<Equipe> equipes;
}
