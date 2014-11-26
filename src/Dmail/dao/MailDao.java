package Dmail.dao;

import Dmail.model.Email;
import Dmail.model.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jiaqi on 11/6/14.
 */
public class MailDao {
    public boolean createMail(Email mail, User user, Connection conn) throws SQLException {

        String strsql = "insert into mail (mailId, isNew, mailSize,receiveFrom,title,content,mailDate,attachments,userId) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean s = false;
        PreparedStatement pstmt = conn.prepareStatement(strsql);
        pstmt.setString(1, mail.getEmailID());
        pstmt.setString(2, "true");
        pstmt.setInt(3, mail.getSize());
        pstmt.setString(4, mail.getFrom());
        pstmt.setString(5, mail.getTitle());
        pstmt.setString(6, mail.getContent());
        pstmt.setString(7, mail.getMailDate());
        pstmt.setBoolean(8, mail.isHasAttachment());
        pstmt.setInt(9, user.getUserid());
        s = pstmt.execute();
        return s;
    }

    public boolean checkMail(Email mail, Connection conn) throws SQLException {
        String sql="select mailId from mail where mailId = ?";
        ResultSet rs ;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,mail.getEmailID());
        rs=pstmt.executeQuery();
        if(rs.next())
        {
            return true;
        }
         return false;
    }
    public String returnMailContent(String mailId, Connection conn)throws SQLException
    {
        String sql="select content from mail where mailId = ?";
        ResultSet rs ;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,mailId);
        rs = pstmt.executeQuery();
        String content = rs.getString("content");
        return content;
    }
    public ArrayList<Email> returnMailHeader(int userId, Connection conn)throws SQLException
    {
        String sql="select isNew,mailId,receiveFrom,title,mailDate,mailSize,attachments from mail where userId = ? ";
        ResultSet rs;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userId);
        rs = pstmt.executeQuery();
        ArrayList<Email> mail = new ArrayList<Email>();
        while(rs.next())
        {
            Email e = new Email();
            e.setIsNew(rs.getString("isNew"));
            e.setFrom(rs.getString("receiveFrom"));
            e.setTitle(rs.getString("title"));
            e.setSize(rs.getInt("mailSize"));
            e.setMailDate(rs.getString("mailDate"));
            e.setEmailID(rs.getString("mailId"));
            e.setHasAttachment(rs.getBoolean("attachments"));
            mail.add(e);
        }
        return mail;
    }
    public Email returnOneMailHeader(String emailId, Connection conn) throws SQLException {
        String sql="select isNew,receiveFrom,title,mailDate from mail where mailId = ?";
        ResultSet rs;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, emailId);
        rs = pstmt.executeQuery();
        Email email = new Email();
        email.setIsNew(rs.getString("isNew"));
        email.setFrom(rs.getString("receiveFrom"));
        email.setTitle(rs.getString("title"));
        email.setMailDate(rs.getString("mailDate"));
        return email;
    }
    public int returnMailNumber(int userId, Connection conn)throws SQLException
    {
        String sql="select receiveFrom,title,mailDate,mailSize from mail where userId = ?";
        ResultSet rs ;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userId);
        rs = pstmt.executeQuery();
        int count = 0;
        while(rs.next())
        {
            count++;
        }
        return count;
    }


    public int returnNewMailNumber(int userid, Connection conn) throws SQLException {
        String sql="select count(*) from mail where userId = ? and isNew ='true'";
        ResultSet rs ;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userid);
        rs = pstmt.executeQuery();
        return rs.getInt(1);
    }

    public void deleteMail(String mailId, Connection conn) throws SQLException {

        String sql="delete from mail where mailId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, mailId);
        pstmt.execute();
    }

    public void changeState(String mailId, Connection conn) throws SQLException {
        String sql="update mail set isNew = 'false' where mailId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, mailId);
        pstmt.execute();
    }
}
