package Dmail.Servers;
import Dmail.misc.STListener;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileNotFoundException;

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
        Server server = new Server();

        ServletContextHandler context = new
        ServletContextHandler(ServletContextHandler.SESSIONS);
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


        HandlerCollection handlers = new HandlerCollection();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        // your context specific handlers are added to "contexts" here
       // server.setHandler(handlers);
        // log using NCSA (common log format)
        // http://en.wikipedia.org/wiki/Common_Log_Format

        WebAppContext webAppContext = new WebAppContext("web","/src");
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("web");
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        webAppContext.setConfigurationDiscovered(true);
        webAppContext.setParentLoaderPriority(true);
        handlers.addHandler(webAppContext);
       // server.setHandler(webAppContext);

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
        handlers.addHandler(requestLogHandler);
        requestLogHandler.setServer(server);
        handlers.addHandler(requestLogHandler);
        server.setHandler(handlers);


        String jettyDistKeystore = "./jetty-distribution-9.2.3.v20140905/etc/keystore";
        String keystorePath = System.getProperty( "example.keystore", jettyDistKeystore);
        File keystoreFile = new File(keystorePath);
        if (!keystoreFile.exists())
        {
            throw new FileNotFoundException(keystoreFile.getAbsolutePath());
        }
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(8443);
        http_config.setOutputBufferSize(32768);

        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
        http.setPort(8080);
        http.setIdleTimeout(30000);

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keystoreFile.getAbsolutePath());
        sslContextFactory.setKeyStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
        sslContextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");

        HttpConfiguration https_config = new HttpConfiguration(http_config);
        https_config.addCustomizer(new SecureRequestCustomizer());

        ServerConnector https = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https_config));
        https.setPort(8443);
        https.setIdleTimeout(500000);

        server.setConnectors(new Connector[] { http, https });

        server.start();
        server.join();
    }

}
