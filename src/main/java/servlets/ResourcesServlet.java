package servlets;

import resourceServer.ResourceServer;
import resourceServer.ResourceServerController;
import resourceServer.ResourceServerControllerMBean;
import resources.TestResource;
import sax.ReadXMLFileSAX;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;

public class ResourcesServlet extends HttpServlet {

    public static final String PAGE_URL = "/resources";

    private final ResourceServer resourceServer;

    public ResourcesServlet(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getParameter("path");
        resourceServer.setTestResource(getTestResource(path));

        ResourceServerControllerMBean mBean = new ResourceServerController(resourceServer);
        try {
            ObjectName name = new ObjectName("Admin:type=ResourceServerController");
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            if (!mbs.isRegistered(name)) {
                mbs.registerMBean(mBean, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private TestResource getTestResource(String path) {
        TestResource resource = null;
        try {
            resource = (TestResource) ReadXMLFileSAX.readXML(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resource == null ? new TestResource() : resource;
    }
}