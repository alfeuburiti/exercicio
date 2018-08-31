package br.com.nesoftware.rest.publicacao.service;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.nesoftware.modelo.Publicacao;
import br.com.nesoftware.negocio.validador.exception.NegocioException;
import br.com.nesoftware.rest.exception.representation.FalhaInesperadaRepresentation;
import br.com.nesoftware.rest.exception.representation.FalhaNegocioRepresentation;
import br.com.nesoftware.rest.mensagens.MensagensRetornoHTTP;
import br.com.nesoftware.rest.publicacao.parser.PublicacaoParser;
import br.com.nesoftware.rest.publicacao.representation.PublicacaoRepresentation;
import br.com.nesoftware.rest.publicacao.representation.UsuarioRepresentation;
import br.com.nesoftware.service.publicacao.IPublicacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestScoped
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "Usu�rio", consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)

public class UsuarioResource {
	
	@Inject
	private IPublicacaoService publicacaoService;
	
	@Inject
	private PublicacaoParser publicacaoParser;
	
	
	@POST
	@Path(value = "/novo")
	@ApiOperation(value = "Cria um novo usuario no sistema.", 
			notes = "Recebe um objeto UsuarioRepresentation.")
    @ApiResponses(value = {
    		@ApiResponse(code = HttpURLConnection.HTTP_OK, message = MensagensRetornoHTTP.MSG_OK, response = String.class),
    		@ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = MensagensRetornoHTTP.MSG_ERRO, response = FalhaInesperadaRepresentation.class),
    		@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = MensagensRetornoHTTP.MSG_UNAUTHORIZED, response = FalhaNegocioRepresentation.class),
    	    @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = MensagensRetornoHTTP.MSG_BADREQUEST, response = String.class)})
	public Response inserir(
			@ApiParam(value = "Novo Usuario.", required = true) UsuarioRepresentation usuario,
			@Context HttpServletRequest request) throws Exception{
		
		UsuarioRepresentation usuRepresentation = usuario;
		throw new Exception("Erro de consulta");
//		if(usuRepresentation != null) {
//			return Response.status(Status.OK).build();
//		}
//		else {
//			return Response.status(Status.OK).build();
//		}
	}
	
	@GET
	@Path(value = "/{id}")
	@ApiOperation(value = "Obt�m uma publica��o.", 
			notes = "Retorna um objeto PublicacaoRepresentation.")
    @ApiResponses(value = { 
    		@ApiResponse(code = HttpURLConnection.HTTP_OK, message = MensagensRetornoHTTP.MSG_OK, response = PublicacaoRepresentation.class),
    		@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = MensagensRetornoHTTP.MSG_BADREQUEST),
    		@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = MensagensRetornoHTTP.MSG_UNAUTHORIZED, response = FalhaNegocioRepresentation.class),
    	    @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = MensagensRetornoHTTP.MSG_NOTFOUND)})
	public Response obterPublicacao(
	        @ApiParam(name="id", value="Id da publica��o.") @PathParam("id") final Long id, 
	        @Context HttpServletRequest request) throws NegocioException{
		
		PublicacaoRepresentation rep;
		
		if (id != null){
			Publicacao publicacao = publicacaoService.obter(id);
			rep = publicacaoParser.getRepresentation(publicacao);
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (rep == null){
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.OK).entity(rep).build();
		}	
	}
	
	
	
	@GET
	@Path(value = "/")
	@ApiOperation(value = "Obt�m todos os usuarios.", 
			notes = "Retorna uma lista do objeto UsuarioRepresentation.")
    @ApiResponses(value = { 
    		@ApiResponse(code = HttpURLConnection.HTTP_OK, message = MensagensRetornoHTTP.MSG_OK, response = UsuarioRepresentation.class),
    		@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = MensagensRetornoHTTP.MSG_UNAUTHORIZED, response = FalhaNegocioRepresentation.class),
    	    @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = MensagensRetornoHTTP.MSG_NOTFOUND)})
	public Response obterUsuarios(
	        @Context HttpServletRequest request) throws Exception{
		
		//throw new Exception("Erro de consulta");
		List<UsuarioRepresentation> listaUsuariosRep = obtemUsuariosMock();
		
		if (listaUsuariosRep == null || listaUsuariosRep.isEmpty()){
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.OK).entity(listaUsuariosRep).build();
		}	
	}

	private List<UsuarioRepresentation> obtemUsuariosMock() {
		List<UsuarioRepresentation> listaUsuariosRep = new ArrayList<UsuarioRepresentation>();
		
		listaUsuariosRep.add(new UsuarioRepresentation(1L, "Helton", "helton@gmail.com", "loginHelton"));
		return listaUsuariosRep;
	}
}