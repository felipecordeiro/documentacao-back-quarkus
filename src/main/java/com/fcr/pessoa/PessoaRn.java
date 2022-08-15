package com.fcr.pessoa;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class PessoaRn {

    @Inject
    PessoaDao pessoaDao;
    
    List<PessoaEntity> obterTodos(){
        return pessoaDao.obterTodos();
    }
}
