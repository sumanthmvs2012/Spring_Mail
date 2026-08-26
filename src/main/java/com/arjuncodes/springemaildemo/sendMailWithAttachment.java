package com.arjuncodes.springemaildemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class sendMailWithAttachment  {
   @Autowired
    private JavaMailSender mailSender;

    public void setDataNsendMail(List<String> toEmails, String fromEmail, String bccEmail) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(toEmails.stream()
                .map(String::trim)
                .filter(emailId -> !emailId.isBlank())
                .toArray(String[]::new));
        if (bccEmail != null && !bccEmail.isBlank()) {
            simpleMailMessage.setBcc(bccEmail);
        }

        if (simpleMailMessage.getTo() == null || simpleMailMessage.getTo().length == 0) {
            throw new IllegalArgumentException("At least one toEmail is required");
        }

        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setCompanyName("you");
        getMailMessageTextNSub(simpleMailMessage, companyDetails);
        sendMail(simpleMailMessage, "", "");
        System.out.println("Email sent to " + simpleMailMessage.getTo().length + " recipients");
    }


    public void sendMail(SimpleMailMessage simpleMailMessage, String dear, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            if (simpleMailMessage.getBcc() != null && simpleMailMessage.getBcc().length > 0) {
                helper.setBcc(simpleMailMessage.getBcc());
            }
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(String.format(simpleMailMessage.getText(), dear, content));
            String filePath = "C:\\Users\\suman\\Documents\\SMTP Email\\SpringBootEmail-master\\src\\main\\resources\\Sumanth_Java.pdf";
            FileSystemResource file = new FileSystemResource(filePath);
            helper.addAttachment(file.getFilename(), file);
            mailSender.send(message);
        }catch (MessagingException e) {
            throw new MailParseException(e);
        }


    }

    private void getMailMessageTextNSub(SimpleMailMessage simpleMailMessage,CompanyDetails companyDetails) {
        simpleMailMessage.setSubject("Sumanth MVS || Aspiring for \"I-140 2028\" || Java FullStack Microservices Developer");
        simpleMailMessage.setText("Good morning/evening,\n" +
                "\n" +
                "Glad to reach out to "+companyDetails.getCompanyName()+" for requesting I-140 sponsorship."+"\n" +
                "\n" +
                "I have been working as a Senior Java FullStack Microservices Developer for the last 5 years.\n" +
                "Please find my resume attached, and kindly let me know if you'd like to consider my profile for the upcoming I-140 2028 lottery.\n" +
                "\n" +
                "Thanks for reading my E-mail, hoping to hearing back from "+companyDetails.getCompanyName()+"."+"\n" +
                "\n" +
                "\n" +
                "Best Regards,\n" +
                "Sumanth MVS\n" +
                "+1 6827728043\n" +
                "\n" +
                "https://www.linkedin.com/in/sumanth-mvs-softwaredeveloper/\n" +
                "\n" +
                "\n");
    }
}
