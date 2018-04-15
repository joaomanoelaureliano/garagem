package com.joaomanoel.garagem.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.joaomanoel.garagem.data.PessoaRepository;
import com.joaomanoel.garagem.model.Pessoa;
import com.joaomanoel.garagem.service.PessoaRegistration;

@Path("/pessoas")
public class PessoaResourceRESTService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private PessoaRepository repository;

    @Inject
    PessoaRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pessoa> listAllPessoas() {
        return repository.findAllOrderedByName();
    }
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pessoa lookupPessoaById(@PathParam("id") long id) {
        Pessoa pessoa = repository.findById(id);
        if (pessoa == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return pessoa;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPessoa(Pessoa pessoa) {
        Response.ResponseBuilder builder = null;
               
        try {
            validatePessoa(pessoa);
            registration.register(pessoa);
            builder = Response.ok();
            
        } catch (ConstraintViolationException ce) {
            builder = createViolationResponse(ce.getConstraintViolations());
            
        } catch (ValidationException e) {        
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "E-mail não é valido.");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
            
        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }     
        return builder.build();
    }

    private void validatePessoa(Pessoa pessoa) throws ConstraintViolationException, ValidationException {
        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
        if (emailAlreadyExists(pessoa.getEmail())) {
            throw new ValidationException("Violação de E-mail");
        }
    }
    
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validação concluida. Violação encontrada: " + violations.size());
        Map<String, String> responseObj = new HashMap<>();
        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

     public boolean emailAlreadyExists(String email) {
        Pessoa pessoa = null;
        try {
            pessoa = repository.findByEmail(email);
        } catch (NoResultException e) {
        }
        return pessoa != null;
    }
}
