package br.gov.pi.tce.publicacoes.clients;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import modelo.Usuario;

@Local
@Stateless(name="UsuarioServiceClient")
public class UsuarioServiceClient{
	
	private static final String PATH_EXCLUIR = "excluir";
	private static final String PATH_GET_USUARIO = "getUsuario";
	private static final String PATH_ALTERAR = "alterar";
	private static final String PATH_CADASTRAR = "usuarios/novo";
	private static final String RESPONSE_TYPE = "application/json;charset=UTF-8";
	private final String URL_SERVICE = "http://localhost:8080/apirestsiscap/rest/";
	private final String PATH_CONSULTA_TODOS = "usuarios";

	
	private Client client;
	private WebTarget webTarget;


	
	public UsuarioServiceClient(){
		this.client = ClientBuilder.newClient();  
	}
	
	
	
	public List<Usuario> ConsultarTodos(){
		try {
			this.webTarget = this.client.target(URL_SERVICE).path(PATH_CONSULTA_TODOS);
			Invocation.Builder invocationBuilder =  this.webTarget.request(RESPONSE_TYPE);
			Response response = invocationBuilder.get();
			List<Usuario> list = response.readEntity(new GenericType<List<Usuario>>() {});
			return list;
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	
	
	public String CadastrarUsuario(Usuario Usuario){

		this.webTarget = this.client.target(URL_SERVICE).path(PATH_CADASTRAR);
		Invocation.Builder invocationBuilder =  this.webTarget.request(RESPONSE_TYPE);
		Response response = invocationBuilder.post(Entity.entity(Usuario, RESPONSE_TYPE));
		return response.readEntity(String.class);

	}

	public String AlterarUsuario(Usuario Usuario){
		this.webTarget = this.client.target(URL_SERVICE).path(PATH_ALTERAR);
		Invocation.Builder invocationBuilder =  this.webTarget.request(RESPONSE_TYPE);
		Response response = invocationBuilder.put(Entity.entity(Usuario, RESPONSE_TYPE));
		return response.readEntity(String.class);

	}


	public Usuario ConsultarUsuarioPorCodigo(int codigo){
		this.webTarget = this.client.target(URL_SERVICE).path(PATH_GET_USUARIO).path(String.valueOf(codigo));
		Invocation.Builder invocationBuilder =  this.webTarget.request(RESPONSE_TYPE);
		Response response = invocationBuilder.get();
		return response.readEntity(Usuario.class);
	}


	public String ExcluirUsuarioPorCodigo(int codigo){
		this.webTarget = this.client.target(URL_SERVICE).path(PATH_EXCLUIR).path(String.valueOf(codigo));
		Invocation.Builder invocationBuilder =  this.webTarget.request(RESPONSE_TYPE);
		Response response = invocationBuilder.delete();
		return response.readEntity(String.class);

	}


}
