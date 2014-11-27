package Dmail.Servers;
/**
 * Created by Jiaqi on 10/21/14.
 */
import Dmail.Servlet.Register;
import Dmail.misc.STListener;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.server.ssl.ServletSSL;

import java.util.HashMap;
import java.util.Map;

public class WebServer {

    public static final String WEBMAIL_TEMPLATES_ROOT = "resources/templates";

    public static final STListener stListener = new STListener();


    public static void main(String[] args) throws Exception {
        if ( args.length<2 ) {
            System.err.println("java Dmail.Server static-files-dir log-dir");
            System.exit(1);
        }
        String staticFilesDir = args[0];
        String logDir = args[1];
        Server server = new Server(8081);

        ServletContextHandler context = new
                ServletContextHandler(ServletContextHandler.SESSIONS);
        HandlerCollection hc = new HandlerCollection();
        context.setContextPath("/");
        server.setHandler(context);


        // add a simple Servlet at "/dynamic/*"


        // add special pathspec of "/home/" content mapped to the homePath
        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        holderHome.setInitParameter("resourceBase",staticFilesDir);
        holderHome.setInitParameter("dirAllowed","true");
        holderHome.setInitParameter("pathInfoOnly","true");
        context.addServlet(holderHome, "/files/*");

        // Lastly, the default servlet for root content (always needed, to satisfy servlet spec)
        // It is important that this is last.
        ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
        holderPwd.setInitParameter("resourceBase","/tmp/foo");
        holderPwd.setInitParameter("dirAllowed","true");
        context.addServlet(holderPwd, "/");



        WebAppContext webAppContext = new WebAppContext("web","/src");
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("web");
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        webAppContext.setConfigurationDiscovered(true);
        webAppContext.setParentLoaderPriority(true);
        server.setHandler(webAppContext);

        // log using NCSA (common log format)
        // http://en.wikipedia.org/wiki/Common_Log_Format
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename(logDir + "/yyyy_mm_dd.request.log");
        requestLog.setFilenameDateFormat("yyyy_MM_dd");
        requestLog.setRetainDays(90);
        requestLog.setAppend(true);
        requestLog.setExtended(true);
        requestLog.setLogCookies(false);
        requestLog.setLogTimeZone("GMT");
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(requestLog);
        requestLogHandler.setServer(server);

        server.start();
        server.join();
    }





    /*
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);


        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);



        WebAppContext webAppContext = new WebAppContext("web","/src");

        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("web");
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        webAppContext.setConfigurationDiscovered(true);
        webAppContext.setParentLoaderPriority(true);
        server.setHandler(webAppContext);
        server.start();
        server.join();
    }*/
}
