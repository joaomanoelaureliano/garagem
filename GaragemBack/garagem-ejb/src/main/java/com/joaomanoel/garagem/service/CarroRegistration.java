package com.joaomanoel.garagem.service;

import com.joaomanoel.garagem.model.Carro;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class CarroRegistration {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Carro> carroEventSrc;
    
    public void register(Carro carro) throws Exception {
        log.info("Registrando" + carro.getPlaca());
        em.persist(carro);
        carroEventSrc.fire(carro);
    }
}
