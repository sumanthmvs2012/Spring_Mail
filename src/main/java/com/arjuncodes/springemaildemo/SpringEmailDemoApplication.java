package com.arjuncodes.springemaildemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class SpringEmailDemoApplication {

	@Autowired
	private EmailSenderService senderService;

	@Autowired
	private sendMailWithAttachment sendMailWithAttachment;
	public static void main(String[] args) {
		SpringApplication.run(SpringEmailDemoApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail() throws MessagingException, IOException {
		/*senderService.sendSimpleEmail("betanapallisravanraj@gmail.com",
				"This is email body",
				"This is email subject");

*/
		/*senderService.sendMailWithAttachment("betanapallisravanraj@gmail.com",
				"This is email body",
				"This is email subject","");*/

		Instant start = Instant.now();
		//sendMailWithAttachment.sendMail("dear","content");
		sendMailWithAttachment.setDataNsendMail();
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");

	}
}
