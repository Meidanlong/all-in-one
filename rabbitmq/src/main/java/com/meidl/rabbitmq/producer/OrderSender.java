package com.meidl.rabbitmq.producer;


import com.meidl.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {

    @Autowired
    RabbitTemplate rabbitTemplate;


    public void send(Order order) throws Exception{
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());

        rabbitTemplate.convertAndSend("rabbitmqtest-exchange",//exchange
                "rabbitmqtest.abcd",//routingKey
                order,      //消息体内容
                correlationData);//correlationData 消息唯一ID
    }
}
