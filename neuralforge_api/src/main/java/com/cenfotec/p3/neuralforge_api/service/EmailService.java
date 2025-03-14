package com.cenfotec.p3.neuralforge_api.service;

import com.cenfotec.p3.neuralforge_api.exception.customTypes.NeuralForgeEmailException;
import com.cenfotec.p3.neuralforge_api.model.entity.UserEntity;
import com.cenfotec.p3.neuralforge_api.model.resource.UserResource;
import com.cenfotec.p3.neuralforge_api.util.GenerationUtil;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    protected SendGrid sendGrid;
    protected final Email from;

    @Autowired
    public EmailService(@Value("${spring.sendgrid.sender-identity}") String senderIdentity) {
        this.from = new Email(senderIdentity);
    }

    public void sendUserVerificationEmail(UserEntity user, int verificationCode) throws NeuralForgeEmailException {

        String subject = "[ NeuralForge Learning ] - Verify your Email";

        Email to = new Email(user.getEmail());

        Content content = new Content(
                "text/plain",
                "Greetings " + user.getName() + ". \n\nThis is your account verification code: "+ verificationCode
        );

        Mail mail = new Mail(from, subject, to, content);

        sendEmail(mail);
    }

    protected void sendEmail(Mail mail) throws NeuralForgeEmailException {
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            if (response.getStatusCode() > 299){
                throw new NeuralForgeEmailException("An status of " + response.getStatusCode() + " has been returned by the email client. Body: "+response.getBody());
            }
        } catch (Exception ex) {
            throw new NeuralForgeEmailException("An unknown exception has occurred while sending an email: "+ex.getMessage(), ex);
        }
    }

}
