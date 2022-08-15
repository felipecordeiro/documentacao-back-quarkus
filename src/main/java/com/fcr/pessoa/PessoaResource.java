package com.fcr.pessoa;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pessoa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PessoaResource {

    @Inject
    PessoaRn pessoaRn;

    @GET
    Response obterTodos(){

        return Response.ok().entity(pessoaRn.obterTodos()).build();
    }
    
}
