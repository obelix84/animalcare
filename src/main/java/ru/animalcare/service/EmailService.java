package ru.animalcare.service;


import javax.mail.MessagingException;
import java.util.List;

public interface EmailService {

   void sendEmail(String receiver,String message);

   void sendEmailWithAttachments(String receiver, String message, List<Object> attachments) throws MessagingException;
}
