/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crud.crud.services;

import com.crud.crud.Repository.ProdutoRepository;
import com.crud.crud.data.vo.ProdutoVO;
import com.crud.crud.entity.Produto;
import com.crud.crud.exception.ResourceNotFoundException;
import com.crud.crud.message.ProdutoSendMessage;
import java.util.Optional;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mateus
 */
@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ProdutoSendMessage produtoSendMessage;

    public ProdutoVO create(ProdutoVO produtoVO) {
        ProdutoVO proVoRetorno = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
        produtoSendMessage.sendMessage(proVoRetorno);
        return proVoRetorno;
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        Page<Produto> page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    private ProdutoVO convertToProdutoVO(Produto produto) {
        return ProdutoVO.create(produto);
    }
    
    public ProdutoVO findById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records"));
        return ProdutoVO.create(produto);
    }
    
    public ProdutoVO update(ProdutoVO produtoVO) {
        Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());
        if(!optionalProduto.isPresent()){
            new ResourceNotFoundException("No record founds");
        }
        return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
    }
    
      public void delete(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records"));
        produtoRepository.delete(produto);
    }
}
