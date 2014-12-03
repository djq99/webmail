package Dmail.Servlet;

import Dmail.model.Email;
import Dmail.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by jiaqi on 11/23/14.
 */
public class AttachmentUploader extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out;
        HttpSession session = request.getSession(false);
        if(session.getAttribute("userinfo") ==null)
        {
            response.sendRedirect("login");
        }
        User user = (User) session.getAttribute("userinfo");
        String savePath = getServletContext().getRealPath("/userResource/"+user.getUserid());
        File saveDir = new File(savePath);

        if(!saveDir.exists()){
            saveDir.mkdirs();
        }


        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);

        sfu.setHeaderEncoding("UTF-8");

        //set a single file max size
        sfu.setFileSizeMax(1024*1024*32);
        //set a email max size
        sfu.setSizeMax(1024*1024*50);

        try{
            List<FileItem> itemList = sfu.parseRequest(request);
            for (FileItem fileItem : itemList)
            {
                if(!fileItem.isFormField())
                {
                    Long size = fileItem.getSize();

                    String fileName = fileItem.getName();

                    if(fileName.endsWith(".exe"))
                    {
                        out = response.getWriter();
                        out.print("not supported type");

                    }
                    else
                    {
                        File file = new File(savePath,fileName);
                        fileItem.write(file);
                        Email email = new Email();
                        email.setHasAttachment(true);
                        email.setAttachmentPath(savePath);
                        email.setAttachmentName(fileName);
                        session.setAttribute("email",email);
                        out = response.getWriter();
                        out.print("success");
                    }
                }
            }
        }catch(FileUploadBase.FileSizeLimitExceededException e){
            out=response.getWriter();
            out.print("The file is oversize!");
        }catch(FileUploadException e){
            e.printStackTrace();
            out=response.getWriter();
            out.print("upload failed!");
        }catch(Exception e){
            e.printStackTrace();
            out=response.getWriter();
            out.print("upload failed!");
        }
    }
}