package Dmail.mail;

import Dmail.Utils.Base64Coder;
import Dmail.Utils.DbFactory;
import Dmail.dao.MailDao;
import Dmail.model.Email;
import Dmail.model.User;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by jiaqi on 11/22/14.
 */
public class SslSmtpClient {
    private static final int sslPort = 465;

    public static void sendEMail(User user, Email email) {
        SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
        String smtpServer = user.getEmailaddress().substring(user.getEmailaddress().indexOf('@') + 1);
        smtpServer = "pop." + smtpServer;
        try {
            SSLSocket c = (SSLSocket) f.createSocket(smtpServer, sslPort);
            c.startHandshake();
            BufferedWriter w = new BufferedWriter(
                    new OutputStreamWriter(c.getOutputStream()));
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(c.getInputStream()));

            w.write("EHLO smtp.gmail.com" + "\r\n");
            w.flush();
            String line = r.readLine();
            w.write("AUTH LOGIN" + "\r\n");
            w.flush();
            line = r.readLine();
            String emailAddress = user.getEmailaddress();
            emailAddress = Base64Coder.encode(emailAddress);
            w.write(emailAddress + "\r\n");
            w.flush();
            line = r.readLine();
            String emailPassword = user.getEmailPassword();
            emailPassword = Base64Coder.encode(emailPassword);
            w.write(emailPassword + "\r\n");
            w.flush();
            line = r.readLine();
            w.write("MAIL FROM:<" + user.getEmailaddress() + ">\r\n");
            w.flush();
            for (int i = 1; i <= 7; i++) {
                line = r.readLine();
            }
            w.write("RCPT TO:<" + email.getSendTo() + ">\r\n");
            w.flush();
            line = r.readLine();
            //to many people
           /* w.write("RCPT TO:<" + email.getSendCC()+ ">\r\n");
            w.flush();
            line = r.readLine();*/
            //send data
            w.write("DATA\r\n");
            w.flush();
            line = r.readLine();

            Properties properties = new Properties();
            Session session = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(user.getEmailaddress()));
            // To , Bcc and cc
            if (!email.getSendTo().equals("")) {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getSendTo()));
            }
            if (!email.getSendCC().equals("") && email.getSendCC() != null) {
                message.setRecipient(Message.RecipientType.CC, new InternetAddress(email.getSendCC()));
            }
            message.setSubject(email.getTitle());
            message.setSentDate(new Date());

            //the email attachment
            if (email.isHasAttachment() == true) {

                MimeMultipart allMultipart = new MimeMultipart("mixed");
                MimeBodyPart HtmlBodypart = new MimeBodyPart();
                String mailContent = email.getContent();
                HtmlBodypart.setContent(mailContent, "text/html;charset=UTF-8");

                MimeBodyPart attachFileBodypart = new MimeBodyPart();
                MimeMultipart attachFileMMP = new MimeMultipart("related");

                MimeBodyPart attachFileBody = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(new File(email.getAttachmentPath() + "/" + email.getAttachmentName()));
                attachFileBody.setDataHandler(new DataHandler(fds));
                attachFileBody.setFileName(MimeUtility.encodeText(fds.getName()));
                attachFileMMP.addBodyPart(attachFileBody);
                attachFileBodypart.setContent(attachFileMMP, "text/html;charset=UTF-8");

                allMultipart.addBodyPart(HtmlBodypart);
                allMultipart.addBodyPart(attachFileBodypart);
                message.setContent(allMultipart);
                message.saveChanges();
                message.writeTo(c.getOutputStream());

                //put sent email in outbox
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                message.writeTo(baos);
                int size = baos.size();
                email.setSize(size);
                String str = baos.toString();
                String emailId = message.getMessageID();
                emailId = emailId.replace('<', 'D');
                emailId = emailId.replace('>', 'D');
                email.setEmailID(emailId);
                email.setContent(str);

                int userId = user.getUserid();
                String uid = Integer.toString(userId);
                email.setUserID(uid);
                email.setIsNew("false");
                email.setFrom(user.getEmailaddress());
                email.setMailDate(getSentDate(message));
                MailDao mailDao = new MailDao();
                Connection conn = DbFactory.getConnection();
                mailDao.createSentMail(email, user, conn);
                DbFactory.closeConn(conn);

            } else {
                MimeMultipart allMultipart = new MimeMultipart("text");
                MimeBodyPart textBodypart = new MimeBodyPart();
                String mailContent = email.getContent();
                textBodypart.setContent(mailContent, "text/plain;charset=UTF-8");

                allMultipart.addBodyPart(textBodypart);
                message.setContent(allMultipart);
                message.saveChanges();
                message.writeTo(c.getOutputStream());

                //put sent email in outbox
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                message.writeTo(baos);
                int size = baos.size();
                email.setSize(size);
                String str = baos.toString();
                String emailId = message.getMessageID();
                emailId = emailId.replace('<', 'D');
                emailId = emailId.replace('>', 'D');
                email.setEmailID(emailId);
                email.setContent(str);
                int userId = user.getUserid();
                String uid = Integer.toString(userId);
                email.setUserID(uid);
                email.setIsNew("false");
                email.setFrom(user.getEmailaddress());
                email.setMailDate(getSentDate(message));
                MailDao mailDao = new MailDao();
                Connection conn = DbFactory.getConnection();
                mailDao.createSentMail(email, user, conn);
                DbFactory.closeConn(conn);

            }

            w.write("." + "\r\n");
            w.flush();

            w.write("QUIT\r\n");
            w.flush();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void forwardEMail(User user, Email email) {
        SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
        String smtpServer = user.getEmailaddress().substring(user.getEmailaddress().indexOf('@') + 1);
        smtpServer = "pop." + smtpServer;
        try {
            SSLSocket c = (SSLSocket) f.createSocket(smtpServer, sslPort);
            c.startHandshake();
            BufferedWriter w = new BufferedWriter(
                    new OutputStreamWriter(c.getOutputStream()));
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(c.getInputStream()));

            w.write("EHLO smtp.gmail.com" + "\r\n");
            w.flush();
            String line = r.readLine();
            w.write("AUTH LOGIN" + "\r\n");
            w.flush();
            line = r.readLine();
            String emailAddress = user.getEmailaddress();
            emailAddress = Base64Coder.encode(emailAddress);
            w.write(emailAddress + "\r\n");
            w.flush();
            line = r.readLine();
            String emailPassword = user.getEmailPassword();
            emailPassword = Base64Coder.encode(emailPassword);
            w.write(emailPassword + "\r\n");
            w.flush();
            line = r.readLine();
            w.write("MAIL FROM:<" + user.getEmailaddress() + ">\r\n");
            w.flush();
            for (int i = 1; i <= 7; i++) {
                line = r.readLine();
            }
            w.write("RCPT TO:<" + email.getSendTo() + ">\r\n");
            w.flush();
            line = r.readLine();

            w.write("DATA\r\n");
            w.flush();
            line = r.readLine();


            w.write(email.getContent() + "\r\n" + "." + "\r\n");
            //    w.flush();
            line = r.readLine();

            w.write("QUIT\r\n");
            w.flush();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setEmailaddress("imdjq1990@gmail.com");
        user.setEmailPassword("djq199031415926");
    }

    //get the sentDate of the email
    public static String getSentDate(MimeMessage msg) throws Exception {
        Date sentDate = msg.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat();
        return format.format(sentDate);
    }
}
