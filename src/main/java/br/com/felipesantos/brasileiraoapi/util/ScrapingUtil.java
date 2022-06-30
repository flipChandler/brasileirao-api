package br.com.felipesantos.brasileiraoapi.util;

import br.com.felipesantos.brasileiraoapi.dto.PartidaGoogleDTO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScrapingUtil {

    private static final String DIV_PARTIDA_NAO_INICIADA = "div[class=imso_mh__vs-at-sep imso_mh__team-names-have-regular-font]";
    private static final String DIV_PARTIDA_EM_ANDAMENTO = "div[class=imso_mh__lv-m-stts-cont]";
    private static final String DIV_TEMPO_PARTIDA = "div[class=imso_mh__lv-m-stts-cont]";
    private static final String DIV_PARTIDA_ENCERRADA = "span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]";
    private static final String DIV_DADOS_EQUIPE_CASA = "div[class=imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol]";
    private static final String DIV_DADOS_EQUIPE_VISITANTE = "div[class=imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol]";
    private static final String ITEM_LOGO = "img[class=imso_btl__mh-logo]";
    private static final String DIV_PENALIDADES = "div[class=imso_mh_s__psn-sc]";
    private static final String ITEM_GOL = "div[class=imso_gs__gs-r]";
    private static final String DIV_GOLS_EQUIPE_CASA = "div[class=imso_gs__tgs imso_gs__left-team]";
    private static final String DIV_GOLS_EQUIPE_VISITANTE = "div[class=imso_gs__tgs imso_gs__right-team]";
    private static final String DIV_PLACAR_EQUIPE_CASA = "div[class=imso_mh__l-tm-sc imso_mh__scr-it imso-light-font]";
    private static final String DIV_PLACAR_EQUIPE_VISITANTE = "div[class=imso_mh__r-tm-sc imso_mh__scr-it imso-light-font]";
    private static final String BASE_URL_GOOGLE = "https://www.google.com/search?q=";
    private static final String COMPLEMENTO_URL_GOOGLE = "&h1=pt-BR";
    private static final String CASA = "casa";
    private static final String VISITANTE = "visitante";
    private static final String PENALTIS = "Pênaltis";
    private static final String SPAN = "span";
    private static final String SRC = "src";
    private static final String MIN = " min";

    /*public static void main(String[] args) {
        String url = getUrl();

        ScrapingUtil scrapingUtil = new ScrapingUtil();
        String url = scrapingUtil.montarUrlGoogle("palmeiras", "cerro porteno");
        scrapingUtil.getInformacoesPartida(url);
    }*/

    public PartidaGoogleDTO getInformacoesGoogle(String url) {
        PartidaGoogleDTO partida = new PartidaGoogleDTO();
        Document document = null;

        try {
            document = Jsoup.connect(url).get();    // conexao com a pagina
            String title = document.title();        // title do html
            log.info("Titulo da pagina: {}", title);

            StatusPartida statusPartida = getStatusPartida(document);
            partida.setStatusPartida(statusPartida);
            log.info("Status da partida: {}", statusPartida);

            if (statusPartida != StatusPartida.PARTIDA_NAO_INICIADA) {
                String tempoPartida = getTempoPartida(document);
                partida.setTempoPartida(tempoPartida);
                log.info("Tempo da partida: {}", tempoPartida);

                Integer placarEquipeCasa = getPlacarEquipe(document, DIV_PLACAR_EQUIPE_CASA); // l-tm
                partida.setPlacarEquipeCasa(placarEquipeCasa);
                log.info("Placar Equipe Casa: {}", placarEquipeCasa);

                Integer placarEquipeVisitante = getPlacarEquipe(document, DIV_PLACAR_EQUIPE_VISITANTE); // r-tm
                partida.setPlacarEquipeVisitante(placarEquipeVisitante);
                log.info("Placar Equipe Visitante: {}", placarEquipeVisitante);

                String golsEquipeCasa = getGolsEquipe(document, DIV_GOLS_EQUIPE_CASA);
                partida.setGolsEquipeCasa(golsEquipeCasa);
                log.info("Gols Equipe Casa: {}", golsEquipeCasa);

                String golsEquipeVisitante = getGolsEquipe(document, DIV_GOLS_EQUIPE_VISITANTE);
                partida.setGolsEquipeVisitante(golsEquipeVisitante);
                log.info("Gols Equipe Visitante: {}", golsEquipeVisitante);
            }

            String nomeEquipeCasa = getNomeEquipe(document, DIV_DADOS_EQUIPE_CASA);
            partida.setNomeEquipeCasa(nomeEquipeCasa);
            log.info("Nome Equipe Casa: {}", nomeEquipeCasa);

            String nomeEquipeVisitante = getNomeEquipe(document, DIV_DADOS_EQUIPE_VISITANTE);
            partida.setNomeEquipeVisitante(nomeEquipeVisitante);
            log.info("Nome Equipe Visitante: {}", nomeEquipeVisitante);

            String urlLogoEquipeCasa = getLogoEquipe(document, DIV_DADOS_EQUIPE_CASA);
            partida.setUrlLogoEquipeCasa(urlLogoEquipeCasa);
            log.info("URL Logo Equipe Casa: {}", urlLogoEquipeCasa);

            String urlLogoEquipeVisitante = getLogoEquipe(document, DIV_DADOS_EQUIPE_VISITANTE);
            partida.setUrlLogoEquipeVisitante(urlLogoEquipeVisitante);
            log.info("URL Logo Equipe Visitante: {}", urlLogoEquipeVisitante);

            Integer placarEstendidoEquipeCasa = getPenalidades(document, CASA);
            partida.setPlacarEstendidoEquipeCasa(placarEstendidoEquipeCasa);
            log.info("Placar Estendido Equipe Casa: {}", placarEstendidoEquipeCasa);

            Integer placarEstendidoEquipeVisitante = getPenalidades(document, VISITANTE);
            partida.setPlacarEstendidoEquipeVisitante(placarEstendidoEquipeVisitante);
            log.info("Placar Estendido Equipe Visitante: {}", placarEstendidoEquipeVisitante);

            return partida;
        } catch (IOException e) {
            log.error("ERRO AO TENTAR CONECTAR NO GOOGLE COM JSOUP -> {}", e.getMessage());
        }

        return null;
    }

    private Integer getPenalidades(Document document, String tipoEquipe) {
        boolean isPenalidades = document.select(DIV_PENALIDADES).isEmpty();
        if (!isPenalidades) {
            String penalidades = document
                    .select(DIV_PENALIDADES)
                    .text();

            String penalidadesCompletas = penalidades
                    .substring(0, 5)
                    .replace(" ", "");

            String[] divisao = penalidadesCompletas.split("-");

            return tipoEquipe.equals(CASA)
                    ? formatarPlacarStringInteger(divisao[0])
                    : formatarPlacarStringInteger(divisao[1]);
        }

        return null;
    }

    public Integer formatarPlacarStringInteger(String placar) {
        Integer valor;
        try {
            valor = Integer.valueOf(placar);
        } catch (Exception e) {
            valor = 0;
        }
        return valor;
    }

    public StatusPartida getStatusPartida(Document document) {
        StatusPartida statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;

        boolean isTempoPartida = document.select(DIV_PARTIDA_EM_ANDAMENTO).isEmpty();
        if (!isTempoPartida) {
            String tempoPartida = document
                    .select(DIV_PARTIDA_EM_ANDAMENTO)
                    .first()
                    .text(); // printa o status da partida ... intervalo ..'55

            statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;

            if (tempoPartida.contains(PENALTIS)) {
                statusPartida = StatusPartida.PARTIDA_PENALTIS;
            }
        }

        isTempoPartida = document
                .select(DIV_PARTIDA_ENCERRADA)
                .isEmpty();

        if (!isTempoPartida) {
            statusPartida = StatusPartida.PARTIDA_ENCERRADA;
        }

        return statusPartida;
    }

    public String getTempoPartida(Document document) {
        String tempoPartida = "";

        boolean isTempoPartida = document
                .select(DIV_PARTIDA_NAO_INICIADA)
                .isEmpty();

        if (!isTempoPartida) {
            tempoPartida = document
                    .select(DIV_PARTIDA_NAO_INICIADA)
                    .first()
                    .text();
        }

        // tempo partida: 46 min, ou intervalo ou penalidades
        isTempoPartida = document
                .select(DIV_TEMPO_PARTIDA)
                .isEmpty();

        if (!isTempoPartida) {
            tempoPartida = document
                    .select(DIV_TEMPO_PARTIDA)
                    .first()
                    .text();
        }

        // tempo partida: encerrado
        isTempoPartida = document
                .select(DIV_PARTIDA_ENCERRADA)
                .isEmpty();

        if (!isTempoPartida) {
            tempoPartida = document
                    .select(DIV_PARTIDA_ENCERRADA)
                    .first()
                    .text();
        }
        return corrigeTempoPartida(tempoPartida);
    }

    public String corrigeTempoPartida(String tempo) {
        if (tempo.contains("'")) {
            return tempo.replace("'", MIN);
        }
        return tempo;
    }

    private String getNomeEquipe(Document document, String divNomeEquipe) {
        Element elemento = document.selectFirst(divNomeEquipe);
        String nomeEquipe = elemento
                .select(SPAN)
                .text();

        return nomeEquipe;
    }

    private String getLogoEquipe(Document document, String divLogoEquipe) {
        Element elemento = document.selectFirst(divLogoEquipe);
        String urlLogo = elemento
                .select(ITEM_LOGO)
                .attr(SRC);  // pega o attr src de img

        // data:image/png;base64,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
        return urlLogo;
    }

    private Integer getPlacarEquipe(Document document, String divPlacarEquipe) {
        String placarEquipe = document
                .selectFirst(divPlacarEquipe)
                .text();

        return formatarPlacarStringInteger(placarEquipe);
    }

    private String getGolsEquipe(Document document, String divGolsEquipe) {
        List<String> golsEquipe = new ArrayList<>();
        Elements elementos = document
                .select(divGolsEquipe)
                .select(ITEM_GOL);

        for (Element elemento : elementos) {
            String infoGol = elemento.select(ITEM_GOL).text();
            golsEquipe.add(infoGol);
        }

        return String.join(", ", golsEquipe);   // one string, separate each element by ', '
    }

    public static String getUrl() {
        return new StringBuilder(BASE_URL_GOOGLE)
                .append("palmeiras+x+corinthians+08/08/2020")
                .append(COMPLEMENTO_URL_GOOGLE)
                .toString();
    }

    public String montarUrlGoogle(String nomeEquipeCasa, String nomeEquipeVisitante) {
        try {
            String equipeCasa = nomeEquipeCasa
                    .replace(" ", "+")
                    .replace("-", "+");

            String equipeVisitante = nomeEquipeVisitante
                    .replace(" ", "+")
                    .replace("-", "+");

            return BASE_URL_GOOGLE + equipeCasa + "+x+" + equipeVisitante + COMPLEMENTO_URL_GOOGLE;
        } catch (Exception e) {
            log.error("ERRO: {}", e.getMessage());
        }
        return "";
    }
}
