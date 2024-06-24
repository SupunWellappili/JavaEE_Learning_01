package servlets;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/customer")
public class customerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //How to create the DBCP pool
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/Test");
        bds.setUsername("root");
        bds.setPassword("87654321");
        bds.setMaxTotal(5);
        bds.setInitialSize(5);

        ServletContext servletContext = req.getServletContext();//a common place for all servlet
        servletContext.setAttribute("bds",bds);//store the pool inside the servlet context


        try {
           Connection connection = bds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                String id = rst.getString(1);
                System.out.println(id+", ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        try {
            Connection connection = bds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {
                String id = rst.getString(1);
                System.out.println(id+", ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Got put Method Invoke");
    }
}
