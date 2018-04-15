package com.joaomanoel.garagem.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import com.joaomanoel.garagem.model.Pessoa;

@RequestScoped
public class PessoaListProducer {

    @Inject
    private PessoaRepository pessoaRepository;
    private List<Pessoa> pessoas;

    @Produces
    @Named
    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void onPessoaListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Pessoa pessoa) {
        retrieveAllPessoasOrderedByName();
    }

    @PostConstruct
    public void retrieveAllPessoaOrderedByNome() {
        pessoas = pessoaRepository.findAllOrderedByName();
    }

    private void retrieveAllPessoasOrderedByName() {
        throw new UnsupportedOperationException("Ainda não suportado."); 
    }
}
