package org.kafka.producer;

import org.apache.kafka.clients.consumer.internals.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;

public class WikimediaChangesHandler implements BackgroundEventHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(WikimediaChangesHandler.class);

	private KafkaTemplate<String, String> kafkaTemplate;
	private String topic;
	
	public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}

	
	@Override
	public void onClosed() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComment(String arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(String arg0, MessageEvent arg1) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Event data -> %s" + arg1.getData());
		kafkaTemplate.send(topic, arg1.getData());		
	}

	@Override
	public void onOpen() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
