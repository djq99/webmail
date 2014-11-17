package Dmail.model;

/**
 * Created by jiaqi on 10/26/14.
 */
import java.util.List;
public class Email {

    private String emailID;

    private boolean isNew;

    private int size;

    private String toList;

    private String from;

    private String title;

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    private String mailDate;


    private boolean hasAttachment;


    private String userID;

    public String getEmailID()
    {
        return emailID;
    }

    public void setEmailID(String emailID)
    {
        this.emailID = emailID;
    }

    public boolean getIsNew()
    {
        return isNew;
    }

    public void setIsNew(boolean isNew)
    {
        this.isNew =isNew;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public void setToList(String toList)
    {
        this.toList = toList;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getMailDate()
    {
        return mailDate;
    }

    public void setMailDate(String mailDate)
    {
        this.mailDate = mailDate;
    }



    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }


    public boolean isHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }
}
