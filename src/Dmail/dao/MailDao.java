package Dmail.dao;

import Dmail.model.Email;
import Dmail.model.User;

import java.sql.*;
/**
 * Created by jiaqi on 11/6/14.
 */
public class MailDao extends CommonDao{
    public boolean createMailHeader(Email mail,User user,Connection conn)
    {
        String sql = "insert into [mail](mailId,isNew,size,sendto,receiveFrom,title,maildate,userId)values('"+mail.getEmailID()+"','"+"false"+"','"+mail.getSize()+"','"+mail.getToList()+"','"+mail.getFrom()+"','"+mail.getTitle()+"','"+mail.getMailDate()+"','"+user.getUserid()+"')";
        int rs = -1;
        try
        {
            rs = execUpdate(sql, conn);
            if (rs!=-1)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkMail(Email mail, User user,Connection conn)
    {
        String sql = "select mailId from [mail] where mailId ='"+mail.getEmailID()+"'";
       // System.out.println(sql);
        ResultSet rs = null;
        try
        {
            rs = execSelect(sql, conn);
            if (rs.next())
            {
                return true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(null, rs);
        }
        return false;
    }
    public boolean updateState(Email mail, User user, Connection conn)
    {
        String sql = "update [mail] set isnew = 'true' where mailId ='"+mail.getEmailID()+"'and isnew ='false'";
        int rs = -1;
        try
        {
            rs = execUpdate(sql, conn);
            if (rs!=-1)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
