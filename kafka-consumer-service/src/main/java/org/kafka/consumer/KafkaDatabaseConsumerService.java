package org.kafka.consumer;

import org.kafka.consumer.entity.WikimediaDataEntity;
import org.kafka.consumer.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumerService {
		
	private static final Logger logger = LoggerFactory.getLogger(KafkaDatabaseConsumerService.class);
	
	private WikimediaDataRepository datarepo;
	
	public KafkaDatabaseConsumerService(WikimediaDataRepository datarepo) {
		super();
		this.datarepo = datarepo;
	}

	@KafkaListener(topics="wikimediaChange", groupId="myGroup")
	public void consume(String eventMessage) {
		//logger.info(String.format("Event Message received -> %s" + eventmessage));
		logger.info(String.format("Event message received -> %s", eventMessage));

		WikimediaDataEntity wikimediaDataEntity = new WikimediaDataEntity();
		wikimediaDataEntity.setWikimediaData(eventMessage);
		
		datarepo.save(wikimediaDataEntity);
	}
}
