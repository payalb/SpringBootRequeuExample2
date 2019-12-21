package com.example.demo.business;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Service
public class Consumer {

	ObjectMapper mapper= new ObjectMapper();
	@RabbitListener(queues = "queue")
	public void listen(Message message, Channel channel) throws InterruptedException, IOException {
		Thread.sleep(1000);
		//If exception is thrown, message won't be removed from queue..
		//U need to configure a dlx
		Picture picture= mapper.readValue(message.getBody(), Picture.class);
		if(picture.getSize()>900) {
			//Instead of throwing our custom excepption, we should throw provided by spring
		//	throw new PictureTooLargeException("Unable to process Image: " + message.getName());
			//throw new AmqpRejectAndDontRequeueException("Unable to process Image: " + message.getName());
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
			//delivery tag is unique for each message
		}
		System.out.println(Thread.currentThread().getName()+ " consumed "+ message);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
}

//spin up 3 consumer threads