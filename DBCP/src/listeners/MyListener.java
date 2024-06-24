package listeners;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;


@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("context initializer");
        //This method invoke suddenly just after the creation of servlet context

        //How to create the DBCP pool
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/Test");
        bds.setUsername("root");
        bds.setPassword("87654321");
        bds.setMaxTotal(5);
        bds.setInitialSize(5);

        ServletContext servletContext = servletContextEvent.getServletContext();//a common place for all servlet
        servletContext.setAttribute("bds", bds);//store the pool inside the servlet context

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("context destroyed");

        try {
            ServletContext servletContext = servletContextEvent.getServletContext();
            BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
            bds.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
