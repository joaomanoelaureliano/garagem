package com.joaomanoel.garagem.rest;

import com.joaomanoel.garagem.data.CarroRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
import com.joaomanoel.garagem.model.Carro;
import com.joaomanoel.garagem.service.CarroRegistration;
import javax.ws.rs.OPTIONS;

@Path("/carros")
@RequestScoped
public class CarroResourceRESTService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private CarroRepository repository;

    @Inject
    CarroRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Carro> listAllCarros() {
        return repository.findAllOrderedByAno();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Carro lookupMemberById(@PathParam("id") long id) {
        Carro carro = repository.findById(id);
        if (carro == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return carro;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCarro(Carro carro) {
        Response.ResponseBuilder builder = null;
        try {
            validatePessoa(carro);
            registration.register(carro);
            builder = Response.ok();
            
        } catch (ConstraintViolationException ce) {
            builder = createViolationResponse(ce.getConstraintViolations());
            
        } catch (Exception e) {           
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    private void validatePessoa(Carro pessoa) throws ConstraintViolationException, ValidationException {
        Set<ConstraintViolation<Carro>> violations = validator.validate(pessoa);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validação concluida. Violações encontrada: " + violations.size());
        Map<String, String> responseObj = new HashMap<>();
        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
