package com.joaomanoel.garagem.service;

import com.joaomanoel.garagem.model.Reserva;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class ReservaRegistration {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Reserva> reservaEventSrc;

    public void register(Reserva reserva) throws Exception {
        log.info("Registrando" + reserva.getDtSaida());
        em.persist(reserva);
        reservaEventSrc.fire(reserva);
    }
}
