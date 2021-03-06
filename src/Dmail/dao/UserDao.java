package Dmail.dao;

/**
 * Created by jiaqi on 10/25/14.
 */
import java.sql.*;
import Dmail.model.User;
public class UserDao  {

    public boolean findUser(User user, Connection conn) throws SQLException {
        String sql = "select username,password from userinfo where username=? and password=?";
        ResultSet rs;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());
        rs = pstmt.executeQuery();
        return rs.next();

    }
    public User returnUserInfo(String username, String password, Connection conn) throws SQLException {
        ResultSet rs = null;
        String sql = "select * from userinfo where username=? and password=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,username);
        pstmt.setString(2,password);
        rs = pstmt.executeQuery();
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmailaddress(rs.getString("emailAddress"));
        user.setEmailPassword(rs.getString("emailPassword"));
        user.setUserid(rs.getInt("userId"));
        if(rs.next())
        {
            return user;
        }
        return null;
    }
    public void createUser(User user, Connection conn) throws SQLException {
        String sql = "insert into userinfo(username,password,emailAddress,emailPassword)values(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getEmailaddress());
        pstmt.setString(4,user.getEmailPassword());
         pstmt.execute();
    }
    public int returnUserID(User user, Connection conn) throws SQLException {
        String sql = "select userId from userinfo where username=? and password=? ";
        ResultSet rs = null;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());
        rs = pstmt.executeQuery();
        return rs.getInt("userId");
    }
    public void updatePassword(User user,Connection conn) throws SQLException {
        String sql="update userinfo set password = ? where userId  = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,user.getPassword());
        pstmt.setInt(2,user.getUserid());
        pstmt.execute();

    }
    public boolean checkUser(String username, Connection conn) throws SQLException {
        String sql=" select * from userinfo where username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs;
        pstmt.setString(1,username);
        rs = pstmt.executeQuery();
        return rs.next();
    }

}
