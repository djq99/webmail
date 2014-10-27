package Dmail.model;

/**
 * Created by jiaqi on 10/25/14.
 */
public class User {

    private int userid;

    private String password;

    private String username;

    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

}
