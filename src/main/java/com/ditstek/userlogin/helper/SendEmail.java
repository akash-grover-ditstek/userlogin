package com.ditstek.userlogin.helper;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class SendEmail {

    public void sendEmail(String email, String url) {
        try {
            final String sendGridApi = "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";
            Email from = new Email("harsh.sindhwani@ditstek.com");
            //Email to = new Email("testdits@yopmail.com"); // use your own email address here
            Email to = new Email(email); // use your own email address here
            String subject = "This is test subject";
            Content content = new Content("text/html", "<strong>Please Click on this link to change your password:</strong>"+url);

            Mail mail = new Mail(from, subject, to, content);
            SendGrid sg = new SendGrid(sendGridApi);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
