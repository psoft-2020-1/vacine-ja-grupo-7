package com.ufcg.psoft.vacinaja.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {

    private final static String EMAIL_REMETENTE = "noreply.test.psoft@gmail.com";
    private final static String SENHA_REMETENTE = "noreply.test.psoft@gmail.com";

    public static void enviarMensagem(String destinatario, String titulo, String corpoTexto) {
        Properties propriedades = new Properties();

        propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.port", "587");
        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.starttls.enable", "true");

        propriedades.setProperty("mail.smtp.user", EMAIL_REMETENTE);
        propriedades.setProperty("mail.smtp.password", SENHA_REMETENTE);
        Session sessao = Session.getDefaultInstance(propriedades, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, SENHA_REMETENTE);
            }
        });

        try {
            MimeMessage mensagem = new MimeMessage(sessao);
            mensagem.setFrom(new InternetAddress(EMAIL_REMETENTE));
            mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mensagem.setSubject(titulo);
            mensagem.setText(corpoTexto);
            Transport.send(mensagem);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
