package com.joaomanoel.garagem.data;

import com.joaomanoel.garagem.model.Reserva;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class ReservaRepository {

    @Inject
    private EntityManager em;
    public Reserva findById(Long id) {
        return em.find(Reserva.class, id);
    }

    public List<Reserva> findAllOrderedByData() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reserva> criteria = cb.createQuery(Reserva.class);
        Root<Reserva> reserva = criteria.from(Reserva.class);
        criteria.select(reserva).orderBy(cb.asc(reserva.get("dtRetorno")));
        return em.createQuery(criteria).getResultList();
    }
}
