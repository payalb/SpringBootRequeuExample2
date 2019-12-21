package com.example.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public Exchange exchange() {
		return new FanoutExchange("ex1");
	}
	
	//dead letter exchange. By default rabbitmq will use the same routing key from exchange
	@Bean
	public Exchange dlxexchange() {
		return new FanoutExchange("dlxexchange");
	}
	
	
	
	@Bean
	public Queue queue() {
		//if message lies on queue for 10 mins unprocessed, move it to dead letter exchange
		return QueueBuilder.nonDurable("queue").deadLetterExchange("dlxexchange").ttl(600000)
				.build();
	}
	
	@Bean
	public Queue dlxqueue() {
		//if message lies on queue for 10 mins unprocessed, move it to dead letter exchange
		return QueueBuilder.nonDurable("dlxqueue")
				.build();
	}
	
	@Bean
	public Binding binding() {
		//since fanout, binidng key does not matter
		return BindingBuilder.bind(queue()).to(exchange()).with("order").noargs();
	}

	@Bean
	public Binding dlxbinding() {
		//since fanout, binidng key does not matter
		return BindingBuilder.bind(dlxqueue()).to(dlxexchange()).with("order").noargs();
	}
}

