package Dmail.dao;

import Dmail.model.Email;
import Dmail.model.User;

import java.sql.*;
import java.util.ArrayList;


public class MailDao {
    public boolean createMail(Email mail, User user, Connection conn) throws SQLException {

        String strsql = "insert into mail (mailId, isNew, mailSize,receiveFrom,title,content,mailDate,attachments,userId,folder) values(?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
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
        pstmt.setString(10,"inbox");
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
    public ArrayList<Email> returnMailHeader(int userId,String folder, Connection conn)throws SQLException
    {
        String sql="select isNew,mailId,receiveFrom,title,mailDate,mailSize,attachments from mail where userId = ? and folder = ?";
        ResultSet rs;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userId);
        pstmt.setString(2,folder);
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
        String sql="select count(*) from mail where userId = ? and isNew ='true' and folder ='inbox'";
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

    public ArrayList<Email> returnSort(String folderName, int userId,int page,int pageRecords, String type, Connection conn) throws SQLException {
        page = pageRecords*(page-1);
        String sql="select isNew,mailId,receiveFrom,title,mailDate,mailSize,attachments from mail where userId =? and folder =? order by "+type+" desc limit "+pageRecords+" Offset "+page;
        ResultSet rs;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userId);
        pstmt.setString(2,folderName);
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


    public int totalRecords(String folderName, int userId, Connection conn) throws SQLException {
        String sql="select count(*) from mail where userId = ? and folder = ?";
        ResultSet rs ;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userId);
        pstmt.setString(2,folderName);
        rs = pstmt.executeQuery();
        return rs.getInt(1);
    }
    public ArrayList<Email> getCurrentPage(String folderName, int userId, int page, int pageRecords,Connection conn) throws SQLException {
        //one page show 5 records
        page = pageRecords*(page-1);
        String sql="select isNew,mailId,receiveFrom,title,mailDate,mailSize,attachments from mail where userId = ? and folder = ? order by rowid desc Limit "+pageRecords+" Offset "+page;
        ResultSet rs ;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId);
        pstmt.setString(2, folderName);
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


    public boolean createFolder(int userid, String folderName,Connection conn) throws SQLException {
        String sql="insert into folder(userId,folderName) values(?,?)";
        boolean s = false;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userid);
        pstmt.setString(2, folderName);
        s = pstmt.execute();
        return s;
    }

    public void changeFolder(String mailId, String folderName, Connection conn) throws SQLException {
        String sql="update mail set folder = ? where mailId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,folderName);
        pstmt.setString(2, mailId);
        pstmt.execute();
    }

    public ArrayList<String> getFolders(int userId, Connection conn) throws SQLException {
        String sql = "select folderName from folder where userId = ?";
        ResultSet rs ;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userId);
        rs = pstmt.executeQuery();
        ArrayList<String> folder = new ArrayList<String>();
        while(rs.next())
        {
            String s = new String();
            s = rs.getString("folderName");
            folder.add(s);
        }
        return folder;

    }

    public void deleteFolder(int userid, String folderName, Connection conn) throws SQLException {
        String sql="delete from folder where folderName = ? and userId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,folderName);
        pstmt.setInt(2,userid);
        pstmt.execute();
        String sql2 = "update mail set folder = ? where folder = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setString(1,"inbox");
        pstmt2.setString(2,folderName);
        pstmt2.execute();
    }

    public void cleanTrash(int userid, Connection conn) throws SQLException {
        String sql="delete from mail where folder = 'trash' and userId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userid);
        pstmt.execute();
    }

    public void markRead(String mailId, Connection conn) throws SQLException {
        String sql="update mail set isNew = 'false' where mailId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,mailId);
        pstmt.execute();
    }
    public void markUnread(String mailId, Connection conn) throws SQLException {
        String sql="update mail set isNew = 'true' where mailId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,mailId);
        pstmt.execute();
    }

    public void createSentMail(Email email,User user, Connection conn) throws SQLException {
        String sql = "insert into mail (mailId, isNew, mailSize,receiveFrom,title,content,mailDate,attachments,userId,folder) values(?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email.getEmailID());
        pstmt.setString(2, email.getIsNew());
        pstmt.setInt(3, email.getSize());
        pstmt.setString(4, email.getFrom());
        pstmt.setString(5, email.getTitle());
        pstmt.setString(6, email.getContent());
        pstmt.setString(7, email.getMailDate());
        pstmt.setBoolean(8, email.isHasAttachment());
        pstmt.setInt(9, user.getUserid());
        pstmt.setString(10,"outbox");
        pstmt.execute();
    }
}
