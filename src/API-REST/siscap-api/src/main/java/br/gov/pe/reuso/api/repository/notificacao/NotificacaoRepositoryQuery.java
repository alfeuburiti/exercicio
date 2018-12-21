package br.gov.pe.reuso.api.repository.notificacao;

import java.util.List;

import br.gov.pe.reuso.api.model.Notificacao;
import br.gov.pe.reuso.api.repository.filter.NotificacaoFilter;

public interface NotificacaoRepositoryQuery {
	
	public List<Notificacao> filtrar(NotificacaoFilter filter);

}
