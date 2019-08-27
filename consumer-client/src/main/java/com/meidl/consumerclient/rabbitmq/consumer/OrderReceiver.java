package com.meidl.consumerclient.rabbitmq.consumer;

import com.meidl.rabbitmq.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "rabbitmqtest-queue", durable = "true"),//持久化
            exchange = @Exchange(name = "rabbitmqtest-exchange", durable = "true", type = "topic"),
            key = "order.#"
        )
    )
    @RabbitHandler //标识消费方法操作
    public void onOrderMessage(@Payload Order order,//消息体
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        //消费者操作

//        Order order = (Order) message;
        System.out.println("--收到消息，开始消费 -> 订单实体："+order.getContent()+", 订单ID: "+order.getId());

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        //ACK
        channel.basicAck(deliveryTag,false);//批量监听false

    }
}
