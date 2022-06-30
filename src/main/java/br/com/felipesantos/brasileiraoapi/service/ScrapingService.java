package br.com.felipesantos.brasileiraoapi.service;

import br.com.felipesantos.brasileiraoapi.dto.PartidaGoogleDTO;
import br.com.felipesantos.brasileiraoapi.entities.Partida;
import br.com.felipesantos.brasileiraoapi.util.ScrapingUtil;
import br.com.felipesantos.brasileiraoapi.util.StatusPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapingService {

    @Autowired
    private ScrapingUtil scrapingUtil;

    @Autowired
    private PartidaService partidaService;

    public void verificarPartidaPeriodo() {
        Integer quantidadePartida = partidaService.buscarQuantidadePartidasPeriodo();

        if (quantidadePartida > 0) {
            List<Partida> partidas = partidaService.listarPartidasPeriodo();

            partidas.stream()
                    .forEach(partida -> {
                        String urlPartida = scrapingUtil.montarUrlGoogle(
                                partida.getEquipeCasa().getNomeEquipe(),
                                partida.getEquipeVisitante().getNomeEquipe());

                        PartidaGoogleDTO partidaGoogle = scrapingUtil.getInformacoesGoogle(urlPartida);

                        if (partidaGoogle.getStatusPartida() != StatusPartida.PARTIDA_NAO_INICIADA) {
                            partidaService.atualizaPartida(partida, partidaGoogle);
                        }
                    });
        }
    }
}
