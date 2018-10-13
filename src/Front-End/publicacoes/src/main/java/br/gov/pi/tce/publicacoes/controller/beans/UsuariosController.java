package br.gov.pi.tce.publicacoes.controller.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.pi.tce.publicacoes.clients.UsuarioServiceClient;
import br.gov.pi.tce.publicacoes.modelo.Usuario;

@Named
@ViewScoped
public class UsuariosController extends BeanController {
	
	@Inject
	private UsuarioServiceClient usuarioServiceClient;

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private List<Usuario> usuarios;
	
	@PostConstruct
	public void init() {
		limpar();
		iniciaUsuarios();
	}
	
	public void limpar() {
		usuario = new Usuario();
	}

	public void editar(Usuario usuarioEditar) {
		usuario = usuarioEditar;
	}
	
	public void excluir(Usuario usuarioExcluir) {
		try {
			if(usuarioExcluir == null) {
				registrarMensagem(FacesMessage.SEVERITY_ERROR, "label.erro", "");
			}
			else {
				if(usuarioExcluir.getId() > 0) {
					usuarioServiceClient.excluirUsuarioPorCodigo(usuarioExcluir.getId());
				}	
				registrarMensagem(FacesMessage.SEVERITY_INFO, "label.sucesso", "");
				iniciaUsuarios();
			}
			limpar();
		}
		catch (Exception e) {
			registrarMensagem(FacesMessage.SEVERITY_ERROR, "label.erro", "");
		}
	}
     
    
	private void iniciaUsuarios() {
		try {
			usuarios = usuarioServiceClient.consultarTodos();
		}
		catch (Exception e) {
			registrarMensagem(FacesMessage.SEVERITY_ERROR, "label.erro", e.getMessage());
		}
	}
	
	public void salvar() {
		try {
			if(usuario == null) {
				registrarMensagem(FacesMessage.SEVERITY_ERROR, "label.erro", "");
			}
			else {
				if(usuario.getId() == null || usuario.getId() == 0) {
					usuarioServiceClient.cadastrarUsuario(usuario);
				}	
				else {
					usuarioServiceClient.alterarUsuario(usuario);
				}
				registrarMensagem(FacesMessage.SEVERITY_INFO, "label.sucesso", "");
				iniciaUsuarios();
			}
			limpar();
			
		}
		catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR,  "", e.getMessage());
		}
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}