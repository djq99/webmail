package Dmail.Servers;
/**
 * Created by Jiaqi on 10/21/14.
 */
import Dmail.Servlet.Register;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.server.ssl.ServletSSL;

public class WebServer {
    public static void main(String[] args) throws Exception {
        // Create a basic jetty server object that will listen on port 8080.  Note that if you set this to port 0
        // then a randomly available port will be assigned that you can either look in the logs for the port,
        // or programmatically obtain it for use in test cases.
        Server server = new Server(8080);

        // The ServletHandler is a dead simple way to create a context handler that is backed by an instance of a
        // Servlet.  This handler then needs to be registered with the Server object.
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        // Passing in the class for the servlet allows jetty to instantite an instance of that servlet and mount it
        // on a given context path.

        // !! This is a raw Servlet, not a servlet that has been configured through a web.xml or anything like that !!
      //  handler.addServletWithMapping(Register.class, "/register");

        // Start things up! By using the server.join() the server thread will join with the current thread.
        // See "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()" for more details.

        WebAppContext webAppContext = new WebAppContext("web","/src");

        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("web");
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        webAppContext.setConfigurationDiscovered(true);
        webAppContext.setParentLoaderPriority(true);
        server.setHandler(webAppContext);
        server.start();
        server.join();
    }
}
