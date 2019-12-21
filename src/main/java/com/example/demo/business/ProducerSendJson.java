package com.example.demo.business;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Picture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProducerSendJson {

	@Autowired RabbitTemplate template;
	ObjectMapper mapper= new ObjectMapper();
	public void sendMessage(Picture p) throws JsonProcessingException {
		String m= mapper.writeValueAsString(p);
		template.convertAndSend("queue", m);
	}
}
