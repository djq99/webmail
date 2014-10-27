package Dmail.dao;

/**
 * Created by jiaqi on 10/25/14.
 */
import java.sql.*;
import Dmail.model.User;
public class UserDao extends CommonDao{

    public boolean findUser(User user, Connection conn)
    {
        String sql = "select username,password from userinfo where username='"+user.getUsername()+"' and password='"+user.getPassword()+"'";
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
    public boolean createUser(User user, Connection conn)
    {
        String sql = "insert into [userinfo](username,password)values('"+user.getUsername()+"','"+user.getPassword()+"')";
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
    public int returnUserID(User user, Connection conn)
    {
        String sql = "select userId from userinfo where username='"+user.getUsername()+"' and password='"+user.getPassword()+"'";
        ResultSet rs = null;
        try
        {
            rs = execSelect(sql, conn);
            if (rs.next())
            {
               rs.getInt(1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            close(null, rs);
        }
        return -1;
    }
}
