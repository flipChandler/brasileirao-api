package br.com.felipesantos.brasileiraoapi.controller;

import br.com.felipesantos.brasileiraoapi.dto.EquipeDTO;
import br.com.felipesantos.brasileiraoapi.dto.EquipeResponse;
import br.com.felipesantos.brasileiraoapi.entities.Equipe;
import br.com.felipesantos.brasileiraoapi.exception.StandardError;
import br.com.felipesantos.brasileiraoapi.service.EquipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api("API de Equipes")
@RestController
@RequestMapping("api/v1/equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @ApiOperation("Buscar equipe por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Equipe.class),
            @ApiResponse(code = 400, message = "Bad request", response = StandardError.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not found", response = StandardError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = StandardError.class)
    })
    @GetMapping("{id}")
    public ResponseEntity<Equipe> buscarEquipePorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(equipeService.buscarEquipePorId(id));
    }

    @ApiOperation(value = "Listar equipes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = EquipeResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = StandardError.class)
    })
    @GetMapping
    public ResponseEntity<EquipeResponse> listarEquipes() {
        return ResponseEntity.ok().body(equipeService.listarEquipes());
    }

    @ApiOperation(value = "Inserir equipe")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Equipe.class),
            @ApiResponse(code = 400, message = "Bad request", response = StandardError.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not found", response = StandardError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = StandardError.class)
    })
    @PostMapping
    public ResponseEntity<Equipe> inserirEquipe(@Valid @RequestBody EquipeDTO equipeDTO) {
        Equipe equipe = equipeService.inserirEquipe(equipeDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(equipe.getId()).toUri();

        return ResponseEntity.created(uri).body(equipe);
    }

    @ApiOperation(value = "Alterar equipe")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "no content", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request", response = StandardError.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not found", response = StandardError.class),
            @ApiResponse(code = 500, message = "Internal server error", response = StandardError.class)
    })
    @PutMapping("{id}")
    public ResponseEntity<Void> alterarEquipe(@PathVariable Long id,
                                              @Valid @RequestBody EquipeDTO equipeDTO) {
        equipeService.alterarEquipe(id, equipeDTO);
        return ResponseEntity.noContent().build();
    }
}
