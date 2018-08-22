package service;

import static java.util.Objects.isNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Diarios;

public class DiariosService {

	// URL das fontes disponibilizadas para busca dos di�rios oficiais
	public final static String DIARIO_OFICIAL_PI = "http://www.diariooficial.pi.gov.br/diarios.php";
	public final static String DIARIO_OFICIAL_DOS_MUNICIPIOS = "http://www.diarioficialdosmunicipios.org/";
	// public final static String DOM_TERESINA =
	// "http://www.dom.teresina.pi.gov.br/lista_diario.php";
	// public final static String DOM_PARNAIBA = "http://dom.parnaiba.pi.gov.br/";
	public final static String DOM_TERESINA = "http://www.dom.teresina.pi.gov.br/lista_diario.php?pagina=";
	public final static String DOM_PARNAIBA = "http://dom.parnaiba.pi.gov.br/home?d=";

	// URL Espec�fica para buscar diarios oficiais do Di�rio Oficial do PI
	public final static String DIARIO_OFICIAL_PI_ESPECIFICA = "http://www.diariooficial.pi.gov.br/diario.php?dia=";

	// URL Espec�fica para buscar diarios oficiais dos munic�pios
	public final static String DIARIO_OFICIAL_DOS_MUNICIPIOS_ESPECIFICA = "http://www.diarioficialdosmunicipios.org/edicao_atual.html";

	public final static String PALAVRA_FINAL_DO_ARQUIVO = "assets/diarios/";

	private static Date convertDate(String date) {

		if (date.isEmpty()) {
			return null;
		}

		List<String> dateFormats = new ArrayList<String>(
				Arrays.asList("dd/MM/yyyy", "dd-MM-yyyy", "d' 'MMMM' de 'yyyy"));

		for (String dateFormatString : dateFormats) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
				dateFormat.setLenient(false);
				return dateFormat.parse(date.trim());
			} catch (ParseException pe) {
			}
		}
		return null;
	}

	// Busca de di�rio oficial em
	// http://www.dom.teresina.pi.gov.br/lista_diario.php,
	// http://www.diarioficialdosmunicipios.org/
	// e tambem no http://dom.parnaiba.pi.gov.br/
	public static void getDiariosDOM(String urlString, Date dataInicial, Date dataFinal) {

		int pageDomTeresina = 1;
		int pageDomParnaiba = 1;
		Boolean isFinalPaginacao = Boolean.FALSE;
		List<String> arquivoList = new ArrayList<String>();

		// Lista do objeto Fontes em que consta a data e o nome do pdf
		List<Diarios> diarios = new ArrayList<Diarios>();

		if (urlString.equals(DOM_TERESINA)) {
			for (pageDomTeresina = 1; isFinalPaginacao.equals(Boolean.FALSE); pageDomTeresina++) {
				isFinalPaginacao = getPaginasDiariosDOM(urlString, pageDomTeresina, arquivoList, diarios, dataInicial,
						dataFinal);
				if (isFinalPaginacao == null) {
					System.out.println("Erro na fonte:" + urlString + " - pagina - " + pageDomTeresina);
					isFinalPaginacao = Boolean.FALSE;
				}
			}
		} else if (urlString.equals(DOM_PARNAIBA)) {
			for (pageDomParnaiba = 1; isFinalPaginacao.equals(Boolean.FALSE); pageDomParnaiba++) {
				isFinalPaginacao = getPaginasDiariosDOM(urlString, pageDomParnaiba, arquivoList, diarios, dataInicial,
						dataFinal);
				if (isFinalPaginacao == null) {
					System.out.println("Erro na fonte:" + urlString + " - pagina - " + pageDomParnaiba);
					isFinalPaginacao = Boolean.FALSE;
				}
			}
		} else if (urlString.equals(DIARIO_OFICIAL_DOS_MUNICIPIOS)) {
			isFinalPaginacao = getPaginasDiariosDOM(urlString, 1, arquivoList, diarios, dataInicial, dataFinal);
		}

		exibirDiariosConsole(diarios);
	}

	/**
	 * @param urlString
	 */
	private static Boolean getPaginasDiariosDOM(String urlString, int pageDom, List<String> arquivoList,
			List<Diarios> diarios, Date dataInicial, Date dataFinal) {

		String htmlCompleto = "";
		Boolean isFinalPaginacao = null;

		try {
			String regexForDate = null, regexForPDF = null;

			// A express�o regular das datas e PDFs est�o diferentes para cada URL
			if (urlString.equals(DOM_TERESINA)) {
				regexForDate = "\\d{2}/\\d{2}/\\d{4}";
				regexForPDF = "DOM+[0-9]+A?+[-]+[A|B-B-|A-A-|0-9]+[-]?+\\s?+[0-9A-Za-z]+\\s?+[(]?+[0-9]?+[)]?+.(pdf|rar|exe)";
			} else if (urlString.equals(DOM_PARNAIBA)) {
				regexForDate = "\\d{2}-\\d{2}-\\d{4}";
				regexForPDF = "[0-9A-Za-z]+[.][Pp][Dd][Ff]";
			} else if (urlString.equals(DIARIO_OFICIAL_DOS_MUNICIPIOS)) {
				regexForDate = "\\d{2}\\s+((Janeiro)|(Fevereiro)|(Mar�o)|(Abril)|(Maio)|(Junho)|(Julho)|(Agosto)|(Setembro)|(Outubro)|(Novembro)|(Dezembro))\\s+[d][e]\\s+\\d{4}";
				regexForPDF = "[D][M]\\s+[0-9]+[.][Pp][Dd][Ff]";
				// Em particular, o di�rio oficial dos municipios tamb�m requer a busca em uma
				// p�gina espec�fica
				urlString = DIARIO_OFICIAL_DOS_MUNICIPIOS_ESPECIFICA;
			}

			isFinalPaginacao = lerPaginaDiarioDOM(urlString, pageDom, arquivoList, diarios, htmlCompleto,
					isFinalPaginacao, regexForDate, regexForPDF, dataInicial, dataFinal);

		} catch (MalformedURLException excecao) {
			excecao.printStackTrace();
		} catch (IOException excecao) {
			excecao.printStackTrace();
		}

		return isFinalPaginacao;
	}

	/**
	 * @param urlString
	 * @param pageDom
	 * @param arquivoList
	 * @param diarios
	 * @param htmlCompleto
	 * @param isFinalPaginacao
	 * @param regexForDate
	 * @param regexForPDF
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static Boolean lerPaginaDiarioDOM(String urlString, int pageDom, List<String> arquivoList,
			List<Diarios> diarios, String htmlCompleto, Boolean isFinalPaginacao, String regexForDate,
			String regexForPDF, Date dataInicial, Date dataFinal) throws MalformedURLException, IOException {

		String linhaHTML;
		Date date = null;
		String urlFonte = urlString;

		URL url = new URL(urlString);
		if (urlString.equals(DOM_TERESINA) || urlString.equals(DOM_PARNAIBA)) {
			urlFonte = urlString + pageDom;
			url = new URL(urlFonte);
		}
		BufferedReader fonteHTML = new BufferedReader(new InputStreamReader(url.openStream()));
		Pattern datePattern = Pattern.compile(regexForDate);
		Pattern pdfPattern = Pattern.compile(regexForPDF);

		while ((linhaHTML = fonteHTML.readLine()) != null) {

			htmlCompleto = htmlCompleto + linhaHTML;
			if ((!isNull(regexForDate)) && (!isNull(regexForPDF))) {

				Matcher matcher = datePattern.matcher(linhaHTML);
				while (matcher.find()) {
					date = convertDate(matcher.group());
				}

				matcher = pdfPattern.matcher(linhaHTML);
				while (matcher.find()) {
					if (!isNull(date)) {
						String arquivoStr = matcher.group();
						if (urlString.equals(DOM_PARNAIBA)
								|| urlString.equals(DIARIO_OFICIAL_DOS_MUNICIPIOS_ESPECIFICA)) {

							if (dataInicial.compareTo(date) <= 0 && dataFinal.compareTo(date) >= 0) {
								incluirDiarioOficial(urlFonte, diarios, date, arquivoStr);
							} else if (date.compareTo(dataInicial) < 0) {
								isFinalPaginacao = Boolean.TRUE;
								break;
							}
							// Necess�rio nullar o date para evitar trazer PDFs que n�o contenham na
							// lista, e estes est�o sem data.
							date = null;
						} else if (urlString.equals(DOM_TERESINA)) {
							if (!arquivoList.contains(arquivoStr)) {
								arquivoList.add(arquivoStr);
								if (dataInicial.compareTo(date) <= 0 && dataFinal.compareTo(date) >= 0) {
									incluirDiarioOficial(urlFonte, diarios, date, arquivoStr);
								} else if (date.compareTo(dataInicial) < 0) {
									isFinalPaginacao = Boolean.TRUE;
									break;
								}
								// Necess�rio nullar o date para evitar trazer PDFs que n�o contenham na
								// lista, e estes est�o sem data.
								date = null;
								isFinalPaginacao = Boolean.FALSE;
							} else {
								isFinalPaginacao = Boolean.TRUE;
								return isFinalPaginacao;
							}
						}
					}
				}
			}
		}
		fonteHTML.close();
		if (urlString.equals(DOM_PARNAIBA)) {
			if (htmlCompleto.contains(PALAVRA_FINAL_DO_ARQUIVO)) {
				isFinalPaginacao = Boolean.FALSE;
			} else {
				isFinalPaginacao = Boolean.TRUE;
			}
		}
		return isFinalPaginacao;
	}

	/**
	 * @param urlString
	 * @param diarios
	 * @param date
	 * @param arquivoStr
	 */
	private static void incluirDiarioOficial(String urlString, List<Diarios> diarios, Date date, String arquivoStr) {
		diarios.add(new Diarios(urlString, date, arquivoStr));
	}

	// // Busca de di�rio oficial em http://www.diariooficial.pi.gov.br/diarios.php
	// public static void getDiariosEmDiarioOficialPI() {
	//
	// // Lista do objeto Fontes em que consta a data e o nome do pdf
	// List<Diarios> diarios = new ArrayList<Diarios>();
	//
	// try {
	// URL url = new URL(DIARIO_OFICIAL_PI);
	// BufferedReader fonteHTML = new BufferedReader(new
	// InputStreamReader(url.openStream()));
	// String linhaHTML, regexForDate = null, regexForPDF = null;
	// Date date = null;
	// String dateString = null;
	// Matcher matcher = null;
	//
	// // Encontra a data do ultimo di�rio publicado
	// buscaData: while ((linhaHTML = fonteHTML.readLine()) != null) {
	//
	// regexForDate = "\\d{2}/\\d{2}/\\d{4}";
	//
	// Pattern datePattern = Pattern.compile(regexForDate);
	// matcher = datePattern.matcher(linhaHTML);
	//
	// while (matcher.find()) {
	// date = convertDate(matcher.group());
	// dateString = matcher.group();
	// // Ao encontrar a data sai do loop mais externo
	// break buscaData;
	// }
	// }
	// fonteHTML.close();
	//
	// // Constr�i nova url para coletar o PDF e Busca em nova fonte
	// String arrayData[] = dateString.split("/");
	// url = new URL(DIARIO_OFICIAL_PI_ESPECIFICA + arrayData[2] + arrayData[1] +
	// arrayData[0]);
	// fonteHTML = new BufferedReader(new InputStreamReader(url.openStream()));
	//
	// buscaPDF: while ((linhaHTML = fonteHTML.readLine()) != null) {
	// regexForPDF = "DIARIO+[0-9]+[_]+[0-9A-Za-z]+[.][Pp][Dd][Ff]";
	// Pattern pdfPattern = Pattern.compile(regexForPDF);
	//
	// matcher = pdfPattern.matcher(linhaHTML);
	// while (matcher.find()) {
	// if (!isNull(date)) {
	// incluirDiarioOficial(DIARIO_OFICIAL_PI, diarios, date, matcher.group());
	// // Ao encontrar o pdf sai do loop mais externo
	// break buscaPDF;
	// }
	// }
	// }
	// fonteHTML.close();
	//
	// } catch (MalformedURLException excecao) {
	// excecao.printStackTrace();
	// } catch (IOException excecao) {
	// excecao.printStackTrace();
	// }
	//
	// exibirDiariosConsole(diarios);
	// }

	// teste
	public static void getDiariosEmDiarioOficialPI(Date dataInicial, Date dataFinal) {

		// Lista do objeto Fontes em que consta a data e o nome do pdf
		List<Diarios> diarios = new ArrayList<Diarios>();

		List<LocalDate> localDateList = getDiasUteis(dataInicial, dataFinal);
		for (LocalDate localDate : localDateList) {

			try {

				String linhaHTML, regexForPDF = null;
				Date date = asDate(localDate);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				String dia = String.format("%02d", c.get(Calendar.DATE));
				String mes = String.format("%02d", c.get(Calendar.MONTH) + 1); // janeiro � 0
				String ano = String.valueOf(c.get(Calendar.YEAR));
				Matcher matcher = null;

				// Constr�i nova url para coletar o PDF e Busca em nova fonte
				URL url = new URL(DIARIO_OFICIAL_PI_ESPECIFICA + ano + mes + dia);
				BufferedReader fonteHTML = new BufferedReader(new InputStreamReader(url.openStream()));

				buscaPDF: while ((linhaHTML = fonteHTML.readLine()) != null) {
					regexForPDF = "DIARIO+[0-9]+[_]+[0-9A-Za-z]+[.][Pp][Dd][Ff]";
					Pattern pdfPattern = Pattern.compile(regexForPDF);

					matcher = pdfPattern.matcher(linhaHTML);
					while (matcher.find()) {
						if (!isNull(date)) {
							incluirDiarioOficial(DIARIO_OFICIAL_PI, diarios, date, matcher.group());
							// Ao encontrar o pdf sai do loop mais externo
							break buscaPDF;
						}
					}
				}
				fonteHTML.close();

			} catch (MalformedURLException excecao) {
				excecao.printStackTrace();
			} catch (IOException excecao) {
				excecao.printStackTrace();
			}
		}

		exibirDiariosConsole(diarios);
	}

	private static List<LocalDate> getDiasUteis(Date dataInicial, Date dataFinal) {
		LocalDate lda = asLocalDate(dataInicial);
		LocalDate ldb = asLocalDate(dataFinal);

		List<LocalDate> dates = new ArrayList<>();
		for (LocalDate ld = lda; !ld.isAfter(ldb); ld = ld.plusDays(1)) {
			if (!ld.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !ld.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				dates.add(ld);
			}
		}
		return dates;
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 *
	 */
	private static void exibirDiariosConsole(List<Diarios> diarios) {
		// Formato de exibicao da data
		SimpleDateFormat formatoDeData = new SimpleDateFormat("dd/MM/yyyy");
		for (Diarios diario : diarios) {
			System.out.println(
					diario.getFonte() + " - " + formatoDeData.format(diario.getDate()) + " - " + diario.getPdfName());
		}
	}

	/**
	 * @param dataStr
	 * @return data
	 */
	public static Date getData(String dataStr) {
		Date data = null;
		try {
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dataParseada = formatoData.parse(dataStr);
			data = dataParseada;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
}
