package Dmail.Utils;

import Dmail.model.User;
import com.sun.mail.util.MimeUtil;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * Created by jiaqi on 11/20/14.
 */
public class ParseEmail {
    private StringBuffer sb = new StringBuffer();
    User user;
    boolean hasAttachment = false;
    boolean hasText = false;

    public ParseEmail(User user)
    {
        this.user = user;
    }


    public String getEmailContent(Object o) throws MessagingException, IOException {
        String result = "";
        if(o instanceof String)
        {
            result = (String)o;
        }
        else if(o instanceof Part)
        {
            result = parseContent((Part)o);
        }
        else if(o instanceof Multipart)
        {
            Multipart multipart = (Multipart)o;
            result = parseMultipart(multipart);
        }
        return result;
    }

    public String parseMultipart(Multipart multipart) throws MessagingException, IOException
    {
        String result = "";
      for (int i= 0,n = multipart.getCount(); i<n; i++)
      {
          Part part = multipart.getBodyPart(i);
          if(part.getContent() instanceof Multipart)
          {
              Multipart p = (Multipart)part.getContent();
              parseMultipart(p);
          }
          else
          {
              result = parseContent(part);
          }

      }
        return result;
    }

        public String parseContent(Part part) throws MessagingException, IOException {
            sb.append(new String(""));
            if(part.getContentType().startsWith("text/plain"))
            {
                hasText = true;
                String s = (String) part.getContent();
                sb.append(s);
            }
            if(part.getContentType().startsWith("text/html"))
            {
                String s = (String) part.getContent();
                if(hasText == true)
                {
                    sb.delete(0,sb.length());
                    sb.append(s);
                }
                else
                {
                    sb.append(s);
                }
            }
            else if(part.getDisposition()!=null)
            {
                String header[] = part.getHeader("Content-ID");
                if(header == null)
                {
                    //is an attachment not in the html
                    if(part.getDisposition().equals(part.ATTACHMENT))
                    {
                        hasAttachment = true;
                        String fileName = part.getFileName();
                        fileName = MimeUtility.decodeText(fileName);
                        InputStream is = part.getInputStream();
                        String path = getFilePath(user);
                        File dir = new File(path);
                        File f = new File(dir,fileName);
                        FileOutputStream fos = new FileOutputStream(f);
                        byte[] buf = new byte[4096];
                        int bytesRead;
                        while((bytesRead = is.read(buf))!=-1) {
                            fos.write(buf, 0, bytesRead);
                        }
                    }

                }
                else
                {
                    String imgPath ="";
                    String cid = "";
                    MimeBodyPart jpgBody = (MimeBodyPart) part;
                    cid = getCid(part);
                    String fileName = part.getFileName();
                    InputStream is = part.getInputStream();
                    fileName = MimeUtility.decodeText(fileName);
                    String path = getFilePath(user);
                    File dir = new File(path);
                    dir.mkdirs();
                    File f = new File(dir,fileName);
                    FileOutputStream fos = new FileOutputStream(f);
                    byte[] buf = new byte[4096];
                    int bytesRead;
                    while((bytesRead = is.read(buf))!=-1) {
                        fos.write(buf, 0, bytesRead);
                    }
                    imgPath = "userResource/"+user.getUserid()+"/"+fileName;
                    String s = sb.toString();
                    if(s.contains(cid))
                    {
                        s = s.replaceAll(cid,imgPath);
                    }
                    sb.replace(0,sb.length()-1,s);
                }
            }
            return sb.toString();
        }
    public boolean hasAttachment()
    {
        return hasAttachment;
    }


    private static String getCid(Part p) throws MessagingException {
        String cidraw = null, cid = null;
        String[] headers = p.getHeader("Content-id");
        if (headers != null && headers.length > 0) {
            cidraw = headers[0];
        } else {
            return null;
        }
        if (cidraw.startsWith("<") && cidraw.endsWith(">")) {
            cid = "cid:" + cidraw.substring(1, cidraw.length() - 1);
        } else {
            cid = "cid:" + cidraw;
        }
        return cid;

    }
    private static String getFilePath(User user)
    {
        String path = String.valueOf(user.getUserid());
        path = "./web/userResource/"+path;
        return path;
    }
}



