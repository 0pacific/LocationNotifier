package jp.ac.titech.itpro.sdl.locationnotifier;

import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private Properties properties;

    public MailSender(){
        properties = System.getProperties();
    }

    public void send(double lat,double lon,String mail){

        Log.d("mail","sending...");

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        properties.put("mail.smtp.connectiontimeout", "10000");
        properties.put("mail.smtp.timeout", "10000");

        properties.put("mail.debug", "true");
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("itsp.wosd@gmail.com", "develop2016");
            }
        });
        MimeMessage message = new MimeMessage(session);

        try {
            String from = "itsp.wosd@gmail.com";
            String[] to = {mail};
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for(int i = 0; i < to.length; i++ ){
                toAddress[i] = new InternetAddress(to[i]);
            }
            for(int i = 0; i < toAddress.length; i++){
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject("現在の位置情報のお知らせ");
            message.setText("LocationNotifierより、あなたのスマートフォン、タブレットの現在位置のお知らせです。\n現在の位置は〜〜です。");
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}