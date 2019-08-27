package com.meidl.rabbitmq;

import com.meidl.rabbitmq.entity.Order;
import com.meidl.rabbitmq.producer.OrderSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    OrderSender orderSender;

    @Test
    public void testSend1() throws Exception {
        for(int i=0; i<100; i++){
            Order order = new Order();
            order.setId("201908270000000000000000000000"+i);
            order.setContent("测试订单"+i);
            order.setMessageId(System.currentTimeMillis()
                    +"$"+ UUID.randomUUID());
            this.orderSender.send(order);
        }

    }

}
