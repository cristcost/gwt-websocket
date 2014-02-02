package net.cristcost.launcher;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
//import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyStart {
  public static void main(String[] args) throws Exception {

    Server server = new Server();

    // traditional blocking IO and threading model, used if NIO not available
    // SocketConnector connector = new SocketConnector();
    // efficient NIO buffers with a non-blocking threading model, required for WebSockets
    SelectChannelConnector connector = new SelectChannelConnector();

    // Set some timeout options to make debugging easier.
    connector.setMaxIdleTime(3600000);
    connector.setSoLingerTime(-1);
    connector.setPort(8080);
    server.addConnector(connector);


    /*
    Resource keystore = Resource.newClassPathResource("/keystore");
    if (keystore != null && keystore.exists()) {

      connector.setConfidentialPort(8443);

      SslContextFactory factory = new SslContextFactory();
      factory.setKeyStoreResource(keystore);
      factory.setKeyStorePassword("jettyjetty");
      factory.setTrustStoreResource(keystore);
      factory.setKeyManagerPassword("jettyjetty");
      SslSocketConnector sslConnector = new SslSocketConnector(factory);
      sslConnector.setMaxIdleTime(3600000);
      sslConnector.setPort(8443);
      sslConnector.setAcceptors(4);
      server.addConnector(sslConnector);

      System.out.println("SSL access to the quickstart has been enabled on port 8443");
      System.out.println("You can access the application using SSL on https://localhost:8443");
      System.out.println();
    }
    */
    
    String includeJarPattern =
        ".*/.*jsp-api-[^/]*\\.jar$|.*/.*jsp-[^/]*\\.jar$|.*/.*taglibs[^/]*\\.jar$";

    ResourceCollection resources = new ResourceCollection(new String[] {
        "src/main/webapp", "target/gwt-webapp"
    });

    WebAppContext webCtx = new WebAppContext();
    webCtx.setServer(server);
    webCtx.setContextPath("/");

    // webCtx.setDescriptor("src/test/resources/web/test-web.xml");
    webCtx.setBaseResource(resources);

    webCtx.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
        includeJarPattern);

    server.setHandler(webCtx);

    // BrowserListener gwtBrowserListener = new BrowserListener(null, "0.0.0.0",
    // 9997, null);

    try {
      System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
      server.start();
      System.in.read();
      System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
      server.stop();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
