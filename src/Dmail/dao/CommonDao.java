package Dmail.dao;

/**
 * Created by jiaqi on 10/25/14.
 */
import java.sql.*;
public class CommonDao {
    public ResultSet execSelect(String sql, Connection conn)
    {
        Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            return stmt.executeQuery(sql);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public int execUpdate(String sql, Connection conn)
    {
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(stmt, null);
        }

        return -1;
    }

    public void close(Statement stmt, ResultSet rs)
    {
        try
        {
            if (stmt != null)
            {

                stmt.close();
            }
            if (rs != null)
            {
                rs.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
