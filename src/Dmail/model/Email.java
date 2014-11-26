package Dmail.model;

/**
 * Created by jiaqi on 10/26/14.
 */
import java.util.List;
public class Email {

    private String emailID;

    private String isNew;

    private int size;

    private String sendTo;

    private String sendCC;

    private String sendBcc;

    private String from;

    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    private String mailDate;

    private boolean hasAttachment;

    private String attachmentPath;

    private String attachmentName;

    private String userID;

    public String getEmailID()
    {
        return emailID;
    }

    public void setEmailID(String emailID)
    {
        this.emailID = emailID;
    }

    public String getIsNew()
    {
        return isNew;
    }

    public void setIsNew(String isNew)
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

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSendCC() {
        return sendCC;
    }

    public void setSendCC(String sendCC) {
        this.sendCC = sendCC;
    }

    public String getSendBcc() {
        return sendBcc;
    }

    public void setSendBcc(String sendBcc) {
        this.sendBcc = sendBcc;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
}
