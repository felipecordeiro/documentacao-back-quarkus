package com.fcr.pessoa;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@RequestScoped
public class PessoaDao {

    @Inject
    EntityManager em;

    public List<PessoaEntity> obterTodos(){
        StringBuilder query = new StringBuilder();
        query.append(" SELECT a FROM ");
        query.append(" PessoaEntity a ");
        TypedQuery<PessoaEntity> result = em.createQuery(query.toString(), PessoaEntity.class);
        return result.getResultList();
    }
    
}
