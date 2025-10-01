package com.email.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.email.request.EmailRequest;
import com.email.service.EmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Value("${spring.mail.username")
	private String fromEmailId;
	@Override
	public void send(EmailRequest req) {
		 try {
	            // Load FreeMarker template
	            Template template = freemarkerConfig.getTemplate("emailTemplate.ftl");

	            // Process the template into a String
	            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, req.getModel());

	            // Create a MIME message
	            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

	            // Set email details
	            helper.setFrom(fromEmailId);
	            helper.setTo(req.getRecipient());
	            helper.setSubject(req.getSubject());
	            helper.setText(htmlBody, true); // `true` enables HTML content

	            // Attach logo as an inline image
	            ClassPathResource logoResource = new ClassPathResource("static/images/medicare.jpg");
	            helper.addInline("logoImage", logoResource);
	            // Send email
	            javaMailSender.send(mimeMessage);

	        } catch (Exception e) {
	            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
	        }
		
	}

}
