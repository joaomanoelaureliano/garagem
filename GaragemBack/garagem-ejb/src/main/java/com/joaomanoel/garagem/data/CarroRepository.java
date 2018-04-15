package com.joaomanoel.garagem.data;

import com.joaomanoel.garagem.model.Carro;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class CarroRepository {

    @Inject
    private EntityManager em;

    public Carro findById(Long id) {
        return em.find(Carro.class, id);
    }
    
    public List<Carro> findAllOrderedByAno() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Carro> criteria = cb.createQuery(Carro.class);
        Root<Carro> carro = criteria.from(Carro.class);
        criteria.select(carro).orderBy(cb.asc(carro.get("ano")));
        return em.createQuery(criteria).getResultList();
    }
}
