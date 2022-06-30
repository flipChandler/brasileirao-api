package br.com.felipesantos.brasileiraoapi.task;

import br.com.felipesantos.brasileiraoapi.service.ScrapingService;
import br.com.felipesantos.brasileiraoapi.util.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
@Slf4j
public class PartidaTask {

    public static final String TODA_QUARTA_A_CADA_30_SEGUNDOS_DAS_19_AS_23 = "0/30 * 19-23 * * WED";
    public static final String TODA_QUINTA_A_CADA_30_SEGUNDOS_DAS_19_AS_23 = "0/30 * 19-23 * * THU";
    public static final String TODO_SABADO_A_CADA_30_SEGUNDOS_DAS_16_AS_23 = "0/30 * 16-23 * * SAT";
    public static final String TODO_DOMINGO_A_CADA_30_SEGUNDOS_DAS_11_AS_13 = "0/30 * 11-13 * * SUN";
    public static final String TODO_DOMINGO_A_CADA_30_SEGUNDOS_DAS_16_AS_23 = "0/30 * 16-23 * * SUN";
    private static final String TIME_ZONE = "America/Sao_Paulo";
    private static final String DD_MM_YY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

    @Autowired
    private ScrapingService scrapingService;

    @Scheduled(cron = TODA_QUARTA_A_CADA_30_SEGUNDOS_DAS_19_AS_23, zone = TIME_ZONE)
    public void taskPartidaQuartaFeira() {
        this.inicializarAgendamento("taskPartidaQuartaFeira()");
    }

    @Scheduled(cron = TODA_QUINTA_A_CADA_30_SEGUNDOS_DAS_19_AS_23, zone = TIME_ZONE)
    public void taskPartidaQuintaFeira() {
        this.inicializarAgendamento("taskPartidaQuintaFeira()");
    }

    @Scheduled(cron = TODO_SABADO_A_CADA_30_SEGUNDOS_DAS_16_AS_23, zone = TIME_ZONE)
    public void taskPartidaSabado() {
        this.inicializarAgendamento("taskPartidaSabado()");
    }

    @Scheduled(cron = TODO_DOMINGO_A_CADA_30_SEGUNDOS_DAS_11_AS_13, zone = TIME_ZONE)
    public void taskPartidaDomingoManha() {
        this.inicializarAgendamento("taskPartidaDomingoManha()");
    }

    @Scheduled(cron = TODO_DOMINGO_A_CADA_30_SEGUNDOS_DAS_16_AS_23, zone = TIME_ZONE)
    public void taskPartidaDomingoTarde() {
        this.inicializarAgendamento("taskPartidaDomingoTarde()");
    }

    private void inicializarAgendamento(String diaSemana) {
        this.gravarLogInfo(String.format("%s: %s", diaSemana,
                DataUtil.formatarDateEmString(new Date(), DD_MM_YY_HH_MM_SS)));

        scrapingService.verificarPartidaPeriodo();
    }

    private void gravarLogInfo(String mensagem) {
        log.info(mensagem);
    }
}
// https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm