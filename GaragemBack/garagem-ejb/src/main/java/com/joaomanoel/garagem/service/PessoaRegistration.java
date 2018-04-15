package com.joaomanoel.garagem.service;

import com.joaomanoel.garagem.model.Pessoa;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class PessoaRegistration {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Pessoa> pessoaEventSrc;

    public void register(Pessoa pessoa) throws Exception {
        log.info("Registrando" + pessoa.getNome());
        em.persist(pessoa);
        pessoaEventSrc.fire(pessoa);
    }
}
