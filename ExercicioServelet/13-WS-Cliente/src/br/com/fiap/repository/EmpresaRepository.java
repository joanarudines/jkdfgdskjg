package br.com.fiap.repository;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.fiap.exception.ResponseException;
import br.com.fiap.to.Empresa;

public class EmpresaRepository {
	
	private Client client = Client.create();
	private static final String URL = "http://10.20.53.56:8080/12-WS-Restful/rest/empresa";
	
	public void atualizar(Empresa empresa) throws ResponseException {
		
		WebResource resource = client.resource(URL).path(Integer.toString(empresa.getCodigo()));
		
		ClientResponse resp = resource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, empresa);
		
		if(resp.getStatus() != 200) {
			throw new ResponseException();
		}
		
	}
	
	public void deletar(int codigo1) throws ResponseException {
		
		WebResource resource = client.resource(URL).path(Integer.toString(codigo1));
		ClientResponse resp = resource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		
		if(resp.getStatus() != 204) {
			throw new ResponseException();
		}
		
	}
	
	public void cadastrar(Empresa empresa) throws ResponseException {
		
		WebResource resource = client.resource(URL);
		ClientResponse resp = resource.type(MediaType.APPLICATION_JSON)
										.post(ClientResponse.class, empresa);
		
		if(resp.getStatus() != 201) {
			throw new ResponseException();
		}
		
	}
	
	public Empresa pesquisa(int codigo) throws ResponseException { 
		
		WebResource resource = client.resource(URL).path(Integer.toString(codigo));
		
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		if(response.getStatus() != 200) {
			throw new ResponseException();
		}
		
		return response.getEntity(Empresa.class);
		
	}
	
	public List<Empresa> listar() throws ResponseException {
		
		WebResource resource = client.resource(URL);
		
		ClientResponse resp = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		if(resp.getStatus() != 200) {
			throw new ResponseException();
		}
		
		//Recuperar as empresas do webservice
		Empresa[] vetor = resp.getEntity(Empresa[].class);
		return Arrays.asList(vetor); //Transforma o vetor em lista
		
	}

}
