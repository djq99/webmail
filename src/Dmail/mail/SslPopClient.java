package Dmail.mail;

import Dmail.model.User;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
        user.setEmailaddress("djq1990@126.com");
        user.setEmailPassword("djq199031415926");
       ArrayList<String>result = returnEmailHeader(user);
        System.out.println(result.get(1));

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
    public static ArrayList<String> returnEmailHeader(User user)
    {
        String popServer = user.getEmailaddress().substring(user.getEmailaddress().indexOf('@') + 1);
        popServer ="pop."+popServer;
        SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
        ArrayList<String> emailHeader = new ArrayList<String>();
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
            //get email number
            w.write(CMD_STAT +"\r\n");
            w.flush();
            line = r.readLine();
            String arrays[] = line.split(" ");
            int emailNumber = Integer.parseInt(arrays[1]);
            String tempHeader = "";
            for(int i = 1; i<=emailNumber;i++)
            {
                //request read ith email's header
                w.write(CMD_TOP+" "+i+" "+0+"\r\n");
                w.flush();
                tempHeader = "";
                while(!(line=r.readLine()).equals(".")&&!line.contains("ERR") )
                {
                    tempHeader = tempHeader+line+"\r\n";
                }
                if(tempHeader!="")
                {
                    emailHeader.add(tempHeader);
                }
            }
            r.close();
            w.close();
            c.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emailHeader;
    }
}