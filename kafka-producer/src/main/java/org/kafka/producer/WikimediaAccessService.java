package org.kafka.producer;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import com.launchdarkly.eventsource.background.BackgroundEventSource.Builder;

@Service
public class WikimediaAccessService {
	
	private static final Logger logger = LoggerFactory.getLogger(WikimediaAccessService.class);
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public WikimediaAccessService(KafkaTemplate<String, String> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMaessage() throws InterruptedException{
		String topic = "wikimediaChange";
		
		//to read realtime data using eventSource
		BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
		String url = "https://stream.wikimedia.org/v2/stream/recentchange";
		EventSource.Builder builder = new EventSource.Builder(URI.create(url));
		Builder builder1 = new Builder(eventHandler, builder);
		BackgroundEventSource eventsrc = builder1.build();
		
		eventsrc.start();
		
		TimeUnit.MINUTES.sleep(10);
	}
}
