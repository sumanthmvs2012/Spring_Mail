package com.arjuncodes.springemaildemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class sendMailWithAttachment  {
   @Autowired
    private JavaMailSender mailSender;


    SimpleMailMessage simpleMailMessage= new SimpleMailMessage();

        //SimpleMailMessage message = new SimpleMailMessage();

    public void setDataNsendMail() throws IOException {

        String[] emailsList = readFromFile();
        simpleMailMessage.setFrom("betanapallisravanraj@gmail.com");
        simpleMailMessage.setBcc("betanapallisravanraj@gmail.com");
        int count =0;
        for (String emailId:emailsList ) {
            CompanyDetails companyDetails = new CompanyDetails();
            companyDetails.setCompanyEmailId(emailId);
            String cmpName = emailId.split("@")[1].split("[.]", 0)[0].toLowerCase();
            if(cmpName.equals("gmail")||cmpName.equals("GMAIL")||cmpName.equals("hotmail")||cmpName.equals("HOTMAIL")){
                companyDetails.setCompanyName("you");
            }
            else {
                companyDetails.setCompanyName(cmpName);
            }
            getMailMessageTextNSub(simpleMailMessage, companyDetails);
            simpleMailMessage.setTo(companyDetails.getCompanyEmailId());
            sendMail(simpleMailMessage, "", "");
            count++;
            System.out.println(count+" Email sentTo : "+emailId);

        }

    }


    public void sendMail(SimpleMailMessage simpleMailMessage, String dear, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(String.format(simpleMailMessage.getText(), dear, content));
            String filePath = "C:\\Users\\sravan\\Downloads\\SpringBootEmail-master\\SpringBootEmail-master\\src\\main\\resources\\Resume_Sravan_Betanapalli.pdf";
            FileSystemResource file = new FileSystemResource(filePath);
            helper.addAttachment(file.getFilename(), file);
            mailSender.send(message);
        }catch (MessagingException e) {
            throw new MailParseException(e);
        }


    }

    private void getMailMessageTextNSub(SimpleMailMessage simpleMailMessage,CompanyDetails companyDetails) throws IOException {
        simpleMailMessage.setSubject("SRAVAN.B || Aspiring for \"H1B CAP-2024\" || Sr. Java FullStack Microservices Developer");
        simpleMailMessage.setText("Good morning/evening,\n" +
                "\n" +
                "Glad to reach out to "+companyDetails.getCompanyName()+" for requesting H1B sponsorship."+"\n" +
                "\n" +
                "I have been working as a Senior Java FullStack Microservices Developer for the last 6.7 years in India and am aspiring for H1-B Visa FY 2024.\n" +
                "Please find my resume attached, and kindly let me know if you'd like to consider my profile for the upcoming H1B CAP-2024 lottery (FY2024).\n" +
                "\n" +
                "Thanks for reading my E-mail, hoping to hearing back from "+companyDetails.getCompanyName()+"."+"\n" +
                "\n" +
                "\n" +
                "Best Regards,\n" +
                "Sravan Betanapalli\n" +
                "+91 8790763818\n" +
                "\n" +
                "https://www.linkedin.com/in/bsravanraj/\n" +
                "\n" +
                "\n");

    }

    private String[] readFromFile() throws IOException {
        String allEmailString = Files.readString(Path.of("C:\\Users\\sravan\\Downloads\\SpringBootEmail-master\\SpringBootEmail-master\\src\\main\\resources\\emails.txt"));
        System.out.println("Count of emails :"+allEmailString.split(",").length);
        return allEmailString.split(",");
    }
}
