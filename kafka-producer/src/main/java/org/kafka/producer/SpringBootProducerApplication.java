package org.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication

public class SpringBootProducerApplication implements CommandLineRunner
{
    
	public static void main( String[] args )
    {
        SpringApplication.run(SpringBootProducerApplication.class);
    }
	
	@Autowired
	private WikimediaAccessService wikimediaAccessService;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		wikimediaAccessService.sendMaessage();
	} 
}
