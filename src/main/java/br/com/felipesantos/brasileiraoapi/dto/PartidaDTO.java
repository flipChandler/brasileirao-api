package br.com.felipesantos.brasileiraoapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaDTO {

    @NotBlank
    private String nomeEquipeCasa;

    @NotBlank
    private String nomeEquipeVisitante;

    @NotBlank
    private String localPartida;

    @NotNull
    @ApiModelProperty(example = "dd/MM/yyyy HH:mm")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy HH:mm",
            timezone = "America/Sao_Paulo")
    private Date dataHoraPartida;
}
