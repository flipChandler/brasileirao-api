package br.com.felipesantos.brasileiraoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO {

    @NotBlank
    private String nomeEquipe;

    @NotBlank
    private String urlLogoEquipe;
}
