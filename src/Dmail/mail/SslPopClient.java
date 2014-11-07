package Dmail.mail;

import Dmail.model.Email;
import Dmail.model.User;
import java.io.*;
import java.net.UnknownHostException;
import javax.net.ssl.*;
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
        returnEmailInfo(user,number);


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
    public static Email[] returnEmailInfo(User user, int emailNumber)
    {
        String popServer = user.getEmailaddress().substring(user.getEmailaddress().indexOf('@') + 1);
        popServer ="pop."+popServer;
        SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
      //  System.out.println(emailNumber);
        Email mailInfo[] = new Email[emailNumber];
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
            line = r.readLine();
            for(int i = 1; i <= emailNumber;i++)
            {
                //request read ith email's header
                mailInfo[i-1] = new Email();
                w.write(CMD_TOP+" "+i+" "+0+"\r\n");
                w.flush();
                while(!(line=r.readLine()).equals(".")&&!line.contains("ERR") )
                {
                    if(line.contains("Date: "))
                    {
                        String date = line.substring(line.indexOf(":") + 2);
                        mailInfo[i-1].setMailDate(date);
                    }
                    if(line.contains("Subject: "))
                    {
                        mailInfo[i-1].setTitle(line.substring(line.indexOf(":") + 2));
                    }
                    if(line.contains("From: "))
                    {
                        mailInfo[i-1].setFrom(line.substring(line.indexOf(":") + 2));
                    }
                    if(line.contains("To: "))
                    {
                        mailInfo[i-1].setToList(line.substring(line.indexOf(":") + 2));
                    }
                    if(line.contains("Content-Type: "))
                    {
                        mailInfo[i-1].setContentType(line.substring(line.indexOf(":") + 2));
                    }
                }
            }
            //get email size
            w.write(CMD_LIST+"\r\n");
            w.flush();
            int i = 0;
            while(!(line=r.readLine()).equals(".")&&!line.contains("ERR"))
            {
                if(line.contains("OK"))
                {
                    continue;
                }
                if(i < emailNumber)
                {
                    String arrays[] = line.split(" ");
                    String result = arrays[1];
                    int size = Integer.parseInt(result);
                    mailInfo[i].setSize(size);
                    i++;
                }
            }
            //get email ID
             w.write(CMD_UIDL+"\r\n");
             w.flush();
            i=0;
            while(!(line=r.readLine()).equals(".")&&!line.contains("ERR"))
            {
                if(line.contains("OK"))
                {
                    continue;
                }
                if(i < emailNumber)
                {
                    String arrays[] = line.split(" ");
                    String result = arrays[1];
                    mailInfo[i].setEmailID(result);
                    i++;
                }
            }
            w.flush();
            r.close();
            w.close();
            c.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mailInfo;
    }
}