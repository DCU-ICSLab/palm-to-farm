package com.spring.smart_plant.services;

import java.util.Random;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import com.spring.smart_plant.DTO.JSON.RabbitMQCustomMessage;

public class Producer {
	public void send(Object obj) {
		CachingConnectionFactory cf = new CachingConnectionFactory("192.168.0.23", 5672);
	    cf.setUsername("manager");
	    cf.setPassword("manager");
	 
	    //메시지 보내기
	    RabbitTemplate template = new RabbitTemplate(cf);
	    template.setExchange("amq.direct");
	    template.setQueue("myQueue");
	    template.setMessageConverter(new Jackson2JsonMessageConverter());
	    Integer i=new Random().nextInt(200);
	    template.convertAndSend("foo.bar",new RabbitMQCustomMessage(0, i));
	    /*template.convertAndSend("foo.bar", new RabbitMQCustomMessage(0, i));*/
	    /*template.convertAndSend("foo.bar", i.toString());*/
	    cf.destroy();
	}
}
