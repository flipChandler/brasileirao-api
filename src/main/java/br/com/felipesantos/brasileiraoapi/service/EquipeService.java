package br.com.felipesantos.brasileiraoapi.service;

import br.com.felipesantos.brasileiraoapi.dto.EquipeDTO;
import br.com.felipesantos.brasileiraoapi.dto.EquipeResponse;
import br.com.felipesantos.brasileiraoapi.entities.Equipe;
import br.com.felipesantos.brasileiraoapi.exception.BadRequestException;
import br.com.felipesantos.brasileiraoapi.exception.NotFoundException;
import br.com.felipesantos.brasileiraoapi.repository.EquipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Equipe buscarEquipePorId(Long id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o id informado: " + id));
    }

    public Equipe buscarEquipePorNome(String nomeEquipe) {
        return equipeRepository.findByNomeEquipe(nomeEquipe)
                .orElseThrow(() -> new NotFoundException(
                        "Nenhuma equipe encontrada com o nome informado: " + nomeEquipe));
    }

    public EquipeResponse listarEquipes() {
        EquipeResponse equipes = new EquipeResponse();
        equipes.setEquipes(equipeRepository.findAll());

        return equipes;
    }

    public Equipe inserirEquipe(EquipeDTO equipeDTO) {
        Boolean existeEquipe = equipeRepository.existsByNomeEquipe(equipeDTO.getNomeEquipe());
        if (existeEquipe) {
            throw new BadRequestException("Já existe uma equipe cadastrada com o nome informado.");
        }
        Equipe equipe = modelMapper.map(equipeDTO, Equipe.class);
        return equipeRepository.save(equipe);
    }

    public void alterarEquipe(Long id, EquipeDTO equipeDTO) {
        Boolean existeEquipe = equipeRepository.existsById(id);
        if (!existeEquipe) {
            throw new BadRequestException("Não foi possível alterar a equipe: ID inexistente.");
        }
        Equipe equipe = modelMapper.map(equipeDTO, Equipe.class);
        equipe.setId(id);
        equipeRepository.save(equipe);
    }
}
