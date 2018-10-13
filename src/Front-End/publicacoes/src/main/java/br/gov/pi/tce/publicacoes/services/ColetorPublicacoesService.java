package br.gov.pi.tce.publicacoes.services;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import br.gov.pi.tce.publicacoes.controller.beans.PublicacaoController;

/**
 * Classe responsável por executar o agendamento das coletas das publicações nos sites.
 * 
 * 
 * @author Erick Guilherme Cavalcanti
 *
 */
@Stateless
public class ColetorPublicacoesService {

	@Inject
	private PublicacaoController publicacaoController;
	
	// URL das fontes dos diários oficiais
	public final static Long URL_FONTE_DIARIO_OFICIAL_DOS_MUNICIPIOS = Long.valueOf(2);
	public final static Long URL_FONTE_DIARIO_OFICIAL_PIAUI = Long.valueOf(3);
	public final static Long URL_FONTE_DIARIO_OFICIAL_TERESINA = Long.valueOf(4);
	public final static Long URL_FONTE_DIARIO_OFICIAL_PARNAIBA = Long.valueOf(5);
	
	public final static int QUANTIDADE_DIAS = 5;
	
	//static final Logger logger = LogManager.getLogger(ColetorPublicacoesService.class.getName());
	
	//logger.info("Iniciando procedimentos");

	@Schedule(hour="14", minute = "07")
	public void coletarDiarioOficialParnaiba() {
		//logger.info("Iniciando a Coleta do Diario Oficial da Parnaiba");
		Date data = new Date();
		Date dataInicial = getData00Horas00Minutos00SeguntosMenosQuatidadeDias(data, QUANTIDADE_DIAS);
		Date dataFinal = getData23Horas59Minutos59Seguntos(data);
		publicacaoController.getDiariosDOM(URL_FONTE_DIARIO_OFICIAL_PARNAIBA, dataInicial, dataFinal);
		//logger.info("Finalizando a Coleta do Diario Oficial da Parnaiba");
	}
	
	@Schedule(hour="13", minute = "54")
	public void coletarDiarioOficialTeresina() {
		//logger.info("Iniciando a Coleta do Diario Oficial de Teresina");
		Date data = new Date();
		Date dataInicial = getData00Horas00Minutos00SeguntosMenosQuatidadeDias(data, QUANTIDADE_DIAS);
		Date dataFinal = getData23Horas59Minutos59Seguntos(data);
		publicacaoController.getDiariosDOM(URL_FONTE_DIARIO_OFICIAL_TERESINA, dataInicial, dataFinal);
		//logger.info("Finalizando a Coleta do Diario Oficial de Teresina");
	}
	
	@Schedule(hour="00", minute = "49")
	public void coletarDiarioOficialMunicipios() {
		//logger.info("Iniciando a Coleta do Diario Oficial dos Municipios");
		Date data = new Date();
		Date dataInicial = getData00Horas00Minutos00SeguntosMenosQuatidadeDias(data,QUANTIDADE_DIAS);
		Date dataFinal = getData23Horas59Minutos59Seguntos(data);
		publicacaoController.getDiariosDOM(URL_FONTE_DIARIO_OFICIAL_DOS_MUNICIPIOS, dataInicial, dataFinal);
		//logger.info("Finalizando a Coleta do Diario Oficial dos Municipios");
	}
	
	@Schedule(hour="18", minute = "42")
	public void coletarDiarioOficialPiaui() {
		//logger.info("Iniciando a Coleta do Diario Oficial do Estado do Piaui");
		Date data = new Date();
		Date dataInicial = getData00Horas00Minutos00SeguntosMenosQuatidadeDias(data,QUANTIDADE_DIAS);
		Date dataFinal = getData23Horas59Minutos59Seguntos(data);
		publicacaoController.getDiariosEmDiarioOficialPI(dataInicial, dataFinal);
		//logger.info("Finalizando a Coleta do Diario Oficial do Estado do Piaui");
	}
	
	private Date getData00Horas00Minutos00SeguntosMenosQuatidadeDias(Date data, int quatidadeDias){
		if (quatidadeDias <= 0) {
			quatidadeDias = 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_MONTH, (quatidadeDias * -1));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	private Date getData23Horas59Minutos59Seguntos(Date data){
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
	
}
