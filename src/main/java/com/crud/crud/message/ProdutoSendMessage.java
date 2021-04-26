/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crud.crud.message;

import com.crud.crud.data.vo.ProdutoVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mateus
 */
@Component
public class ProdutoSendMessage {
    @Value("${crud.rabbitmq.exchange}")
    String exchange;
    
    @Value("${crud.rabbitmq.routingkey}")
    String routingkey;
    
    public final RabbitTemplate rabbitTemplate;
    @Autowired
    public ProdutoSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void sendMessage(ProdutoVO produtoVO){
        this.rabbitTemplate.convertAndSend(exchange,routingkey,produtoVO);
    }
    
    
}
