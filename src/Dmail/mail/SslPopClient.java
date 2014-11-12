package Dmail.mail;

import Dmail.model.Email;
import Dmail.model.User;
import java.io.*;
import java.net.UnknownHostException;
import javax.net.ssl.*;
import sun.misc.BASE64Decoder;
import Dmail.Utils.QpDecoding;
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
            line = r.readLine();
            //get headers
            for(int i = 1; i <= emailNumber;i++)
            {
                //request read ith email's header
                mail[i-1] = new Email();
                w.write(CMD_TOP+" "+i+" "+0+"\r\n");
                w.flush();
                while(!(line=r.readLine()).equals(".")&&!line.contains("ERR") )
                {
                    if(line.contains("Date: "))
                    {
                        String date = line.substring(line.indexOf(":") + 2);
                        mail[i-1].setMailDate(date);
                    }
                    if(line.contains("Subject: "))
                    {
                        mail[i-1].setTitle(line.substring(line.indexOf(":") + 2));
                    }
                    if(line.contains("From: "))
                    {
                        mail[i-1].setFrom(line.substring(line.indexOf(":") + 2));
                    }
                    if(line.contains("To: "))
                    {
                        mail[i-1].setToList(line.substring(line.indexOf(":") + 2));
                    }
                    if(line.contains("Content-Type: "))
                    {
                        mail[i-1].setContentType(line.substring(line.indexOf(":") + 2,line.indexOf(";")));
                    }
                    if(line.contains("boundary="))
                    {
                       // System.out.println(line.substring(line.indexOf("boundary=")+12,line.length()-3));
                        mail[i-1].setContentBoundary(line.substring(line.indexOf("boundary=")+12,line.length()-3));
                    }
                }
                mail[i-1].setEncodingType("");
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
                    mail[i].setSize(size);
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
                    mail[i].setEmailID(result);
                    i++;
                }
            }
            //get content
       //    for(i = 1; i <= emailNumber;i++)
       //    {
               i=6;
                w.write(CMD_RETR+" "+i+"\r\n");
                w.flush();
                int numberOfBoundary = 0;
                String temp = "";
                // no attachment
              if(mail[i-1].getContentType().contains("multipart/alternative"))
              {
                  while (!(line = r.readLine()).equals(".") && !line.contains("ERR"))
                  {
                      //if it is MIME
                      if (line.contains("Content-Type:") && (line.contains("text/html")))
                      {
                          //finish reading empty between header and body
                          while ((line = r.readLine()).length() != 0)
                          {
                              if (line.contains("Content-Transfer-Encoding:"))
                              {
                                  mail[i - 1].setEncodingType(line);
                              }
                          }
                          while (!(line = r.readLine()).contains(mail[i - 1].getContentBoundary()))
                          {
                              if (line.contains(mail[i-1].getContentBoundary()))
                              {
                                  break;
                              }
                              temp = temp + line + "\r\n";
                          }
                      }
                  }
              }
              else if(mail[i-1].getContentType().contains("text/plain"))
              {
                  while (!(line = r.readLine()).equals(".") && !line.contains("ERR"))
                  {
                      System.out.println(line);
                      if (line.contains("Content-Type:") && (line.contains("text/plain")))
                      {
                          //finish reading empty between header and body
                          while ((line = r.readLine()).length() != 0) {
                              if (line.contains("Content-Transfer-Encoding:")) {
                                  mail[i - 1].setEncodingType(line);
                              }
                          }
                              while (!(line = r.readLine()).equals(".")) {
                                  temp = temp + line + "\r\n";
                                  System.out.println(line);
                              }
                          if (line.equals("."))
                          {
                              break;
                          }
                      }
                  }
              }
              else while(!(line = r.readLine()).equals(".") && !line.contains("ERR"))
              {
                  //do nothing
              }
                if(mail[i-1].getEncodingType().contains("base64"))
                {
                    BASE64Decoder b64 = new BASE64Decoder();
                    temp = new String(b64.decodeBuffer(temp));
                }

                else if(mail[i-1].getEncodingType().contains("quoted-printable"))
                {
                    temp = QpDecoding.qpDecoding(temp);
                }
                mail[i-1].setContent(temp);
         //  }



            w.flush();
            r.close();
            w.close();
            c.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mail;
    }
}