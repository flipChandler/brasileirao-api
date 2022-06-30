package br.com.felipesantos.brasileiraoapi.service;

import br.com.felipesantos.brasileiraoapi.dto.PartidaDTO;
import br.com.felipesantos.brasileiraoapi.dto.PartidaGoogleDTO;
import br.com.felipesantos.brasileiraoapi.dto.PartidaResponse;
import br.com.felipesantos.brasileiraoapi.entities.Partida;
import br.com.felipesantos.brasileiraoapi.exception.NotFoundException;
import br.com.felipesantos.brasileiraoapi.repository.PartidaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EquipeService equipeService;

    public Partida buscarPartidaPorId(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhuma partida foi encontrado com o id informado: " + id));
    }

    public PartidaResponse listarPartidas() {
        PartidaResponse partidas = new PartidaResponse();
        partidas.setPartidas(partidaRepository.findAll());

        return partidas;
    }

    public Partida inserirPartida(PartidaDTO partidaDTO) {
        Partida partida = modelMapper.map(partidaDTO, Partida.class);
        partida.setEquipeCasa(equipeService.buscarEquipePorNome(partidaDTO.getNomeEquipeCasa()));
        partida.setEquipeVisitante(equipeService.buscarEquipePorNome(partidaDTO.getNomeEquipeVisitante()));

        return salvarPartida(partida);
    }

    private Partida salvarPartida(Partida partida) {
        return partidaRepository.save(partida);
    }

    public void alterarPartida(Long id, PartidaDTO partidaDTO) {
        boolean exists = partidaRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Não foi possivel atualizar a partida: ID inexistente" + id);
        }

        Partida partida = buscarPartidaPorId(id);
        partida.setEquipeCasa(equipeService.buscarEquipePorNome(partidaDTO.getNomeEquipeCasa()));
        partida.setEquipeVisitante(equipeService.buscarEquipePorNome(partidaDTO.getNomeEquipeVisitante()));
        partida.setDataHoraPartida(partidaDTO.getDataHoraPartida());
        partida.setLocalPartida(partidaDTO.getLocalPartida());

        salvarPartida(partida);
    }

    public void atualizaPartida(Partida partida, PartidaGoogleDTO partidaGoogle) {
        atualizaInfoPartida(partida, partidaGoogle);

        salvarPartida(partida);
    }

    private void atualizaInfoPartida(Partida partida, PartidaGoogleDTO partidaGoogle) {
        partida.setPlacarEquipeCasa(partidaGoogle.getPlacarEquipeCasa());
        partida.setPlacarEquipeVisitante(partidaGoogle.getPlacarEquipeVisitante());
        partida.setGolsEquipeCasa(partidaGoogle.getGolsEquipeCasa());
        partida.setGolsEquipeVisitante(partidaGoogle.getGolsEquipeVisitante());
        partida.setPlacarEstendidoEquipeCasa(partidaGoogle.getPlacarEstendidoEquipeCasa());
        partida.setPlacarEstendidoEquipeVisitante(partidaGoogle.getPlacarEstendidoEquipeVisitante());
        partida.setTempoPartida(partidaGoogle.getTempoPartida());
    }

    public List<Partida> listarPartidasPeriodo() {
        return partidaRepository.listarPartidasPeriodo();
    }

    public Integer buscarQuantidadePartidasPeriodo() {
        return partidaRepository.buscarQuantidadePartidasPeriodo();
    }
}
