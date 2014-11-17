package Dmail.mail;

import Dmail.Utils.QpDecoding;
import Dmail.model.Email;
import Dmail.model.User;
import sun.misc.BASE64Decoder;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SslPopClient{

    private static final String CMD_USER="USER";
    private static final String CMD_PASS="PASS";
    private static final String CMD_STAT="STAT";
    private static final String CMD_LIST="LIST";
    private static final String CMD_RETR="RETR";
    private static final String CMD_DELE="DELE";
    private static final String CMD_RSET="RSET";
    private static final String CMD_NOOP="NOOP";
    private static final String CMD_QUIT="QUIT";
    private static final String CMD_TOP="TOP";
    private static final String CMD_UIDL="UIDL";
    private static final int sslPort = 995;
    private SSLSocket popSocket;
    private User user;


    public SslPopClient(SSLSocket popSocket, User user) {
        this.popSocket = popSocket;
        this.user = user;
    }
    public SslPopClient()
    {

    }

    public static boolean verifyEmail(String username, String password)
    {
        boolean emailExists = false;
        String emailAddress = username.substring(username.indexOf('@')+1);
        emailAddress = "pop."+emailAddress;
        SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try{
            SSLSocket c = (SSLSocket)f.createSocket(emailAddress,sslPort);
            c.startHandshake();
            BufferedWriter w = new BufferedWriter(
                    new OutputStreamWriter(c.getOutputStream()));
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(c.getInputStream()));
            String line = r.readLine();
            w.write(CMD_USER+" "+username+"\r\n");
            w.flush();
            line = r.readLine();
            w.write(CMD_PASS+" "+password+"\r\n");
            w.flush();
            line = r.readLine();
            if(line.contains("OK"))
            {
                w.write(CMD_QUIT);
                w.flush();
                emailExists = true;
            }
            w.close();
            r.close();
            c.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return emailExists;
    }
    public static void main(String[] args) {
       User user = new User();
        user.setEmailaddress("imdjq1990@gmail.com");
        user.setEmailPassword("djq199031415926");
        int number = returnEmailNumber(user);
        Email[] mailHeaders=returnEmail(user,number);


    }
   public static int returnEmailNumber(User user)
    {
        String popServer = user.getEmailaddress().substring(user.getEmailaddress().indexOf('@') + 1);
        popServer ="pop."+popServer;
        SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
        int num = 0;
        try {
            SSLSocket c = (SSLSocket) f.createSocket(popServer, sslPort);
            c.startHandshake();
            BufferedWriter w = new BufferedWriter(
                    new OutputStreamWriter(c.getOutputStream()));
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(c.getInputStream()));
            String line = r.readLine();
            w.write(CMD_USER+" "+user.getEmailaddress()+"\r\n");
            w.flush();
            line = r.readLine();
            w.write(CMD_PASS+" "+user.getEmailPassword()+"\r\n");
            w.flush();
            line = r.readLine();
            w.write(CMD_STAT +"\r\n");
            w.flush();
            line = r.readLine();
            String arrays[] = line.split(" ");
            String result ="";
            if(line.contains("OK"))
            {
                result = arrays[1];
                num = Integer.parseInt(result);
            }
            w.close();
            r.close();
            c.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return num;
    }
    // get where email comes from
    public String getFrom(MimeMessage msg) throws Exception {
        InternetAddress address[] = (InternetAddress[]) msg.getFrom();
        String from = address[0].getAddress();
        if (from == null)
            from = "";
        String personal = address[0].getPersonal();
        if (personal == null)
            personal = "";
        String fromaddr = personal + "<" + from + ">";
        return fromaddr;
    }
    // get the subject of the email
    public String getSubject(MimeMessage msg) throws MessagingException {
        String subject = "";
        try {
            subject = MimeUtility.decodeText(msg.getSubject());
            if (subject == null)
                subject = "";
        } catch (Exception e) {}
        return subject;
    }
    //get the sentDate of the email
    public String getSentDate(MimeMessage msg) throws Exception {
        Date sentDate = msg.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat();
        return format.format(sentDate);
    }
    //is mail contains attachment?
    public boolean hasAttachment(MimeMessage msg)throws Exception{
       String disposition = msg.getDisposition();
         if(disposition == null)
             return false;
         else return true;
    }


    public static Email[] returnEmail(User user, int emailNumber)
    {
        String popServer = user.getEmailaddress().substring(user.getEmailaddress().indexOf('@') + 1);
        popServer ="pop."+popServer;
        SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Email mail[] = new Email[emailNumber];
        try
        {
            SSLSocket c = (SSLSocket) f.createSocket(popServer, sslPort);
            c.startHandshake();

            BufferedWriter w = new BufferedWriter(
                    new OutputStreamWriter(c.getOutputStream()));
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(c.getInputStream()));
            String line = r.readLine();
            w.write(CMD_USER + " " + user.getEmailaddress() + "\r\n");
            w.flush();
            line = r.readLine();
            w.write(CMD_PASS + " " + user.getEmailPassword() + "\r\n");
            w.flush();
            //get email ID
            w.write(CMD_UIDL+"\r\n");
            w.flush();
            int i=1;
            while(!(line=r.readLine()).equals(".")&&!line.contains("ERR"))
            {
                if(line.contains("OK"))
                {
                    continue;
                }
                mail[i-1] = new Email();
                if(i <=emailNumber)
                {
                    String arrays[] = line.split(" ");
                    String result = arrays[1];
                    mail[i-1].setEmailID(result);
                    i++;
                }
            }
            //get header and content
         for(i = 1; i <= emailNumber;i++)
         {
                w.write(CMD_RETR + " " + i + "\r\n");
                w.flush();
                String content = "";
            if(!(r.readLine().contains("ERR")))
            {
                while(!(line = r.readLine()).equals("."))
                {
                    content = content + line + "\r\n";
                }
            }
            mail[i-1].setContent(content);
            Properties properties = new Properties();
            Session.getDefaultInstance(properties);
            MimeMessage msg = new MimeMessage(Session.getDefaultInstance(properties),
                    new ByteArrayInputStream(content.getBytes()));
            SslPopClient ssl = new SslPopClient();
            mail[i-1].setSize(msg.getSize());
            mail[i-1].setMailDate(ssl.getSentDate(msg));
            mail[i-1].setTitle(ssl.getSubject(msg));
            mail[i-1].setFrom(ssl.getFrom(msg));
         }
            w.flush();
            r.close();
            w.close();
            c.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return mail;
    }
}