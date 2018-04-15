package com.joaomanoel.garagem.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import com.joaomanoel.garagem.model.Pessoa;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PessoaRepository {

    @Inject
    private EntityManager em;

    public Pessoa findById(Long id) {
        return em.find(Pessoa.class, id);
    }

    public Pessoa findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteria = cb.createQuery(Pessoa.class);
        Root<Pessoa> pessoa = criteria.from(Pessoa.class);
        criteria.select(pessoa).where(cb.equal(pessoa.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Pessoa> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteria = cb.createQuery(Pessoa.class);
        Root<Pessoa> pessoa = criteria.from(Pessoa.class);
        criteria.select(pessoa).orderBy(cb.asc(pessoa.get("nome")));
        return em.createQuery(criteria).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Pessoa save(Pessoa pessoa){
        return em.merge(pessoa);
    };
}
