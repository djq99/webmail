package Dmail.Utils;

import java.io.ByteArrayOutputStream;

/**
 * Created by jiaqi on 11/11/14.
 */
public class QpDecoding {
    public final static String qpDecoding(String str)
    {
        String result ="";
        if (str == null)
        {
            return "";
        }
        try
        {
            StringBuffer sb = new StringBuffer(str);
            for (int i = 0; i < sb.length(); i++)
            {
                if (sb.charAt(i) == ' ' && sb.charAt(i - 1) == '=')
                {
                    sb.deleteCharAt(i - 1);
                }
            }
            str = sb.toString();
            byte[] bytes = str.getBytes("US-ASCII");
            for (int i = 0; i < bytes.length; i++)
            {
                byte b = bytes[i];
                if (b != 95)
                {
                    bytes[i] = b;
                }
                else
                {
                    bytes[i] = 32;
                }
            }
            if (bytes == null)
            {
                return "";
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            for (int i = 0; i < bytes.length; i++)
            {
                int b = bytes[i];
                if (b == '=')
                {
                    try
                    {
                        int u = Character.digit((char) bytes[++i], 16);
                        int l = Character.digit((char) bytes[++i], 16);
                        if (u == -1 || l == -1)
                        {
                            continue;
                        }
                        buffer.write((char) ((u << 4) + l));
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    buffer.write(b);
                }
            }
            result = new String(buffer.toByteArray(), "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }
}


