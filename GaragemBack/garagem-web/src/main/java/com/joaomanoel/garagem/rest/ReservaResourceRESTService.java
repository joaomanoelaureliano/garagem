package com.joaomanoel.garagem.rest;

import com.joaomanoel.garagem.data.ReservaRepository;
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
import com.joaomanoel.garagem.model.Reserva;
import com.joaomanoel.garagem.service.ReservaRegistration;

@Path("/reservas")
@RequestScoped
public class ReservaResourceRESTService {
    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private ReservaRepository repository;

    @Inject
    ReservaRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reserva> listAllCarros() {
        return repository.findAllOrderedByData();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Reserva lookupPessoaById(@PathParam("id") long id) {
        Reserva reserva = repository.findById(id);
        if (reserva == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return reserva;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReserva(Reserva reserva) {
        Response.ResponseBuilder builder = null;
        try {
            validatePessoa(reserva);
            registration.register(reserva);
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
    
    private void validatePessoa(Reserva pessoa) throws ConstraintViolationException, ValidationException {
        Set<ConstraintViolation<Reserva>> violations = validator.validate(pessoa);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validação concluida. Violações encontradas. " + violations.size());
        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
