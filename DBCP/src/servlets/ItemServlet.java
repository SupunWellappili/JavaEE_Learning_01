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


@WebServlet(urlPatterns = "/book")
public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        try {
            Connection connection = bds.getConnection();//return a connection from the pool
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Book");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {
                String bId = rst.getString(1);
                System.out.println(bId + ", ");
            }
            connection.close();//return the connection to the pool from the consumer  pool
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
            PreparedStatement pstm = connection.prepareStatement("SELECT  * FROM Book");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {
                String bId = rst.getString(1);
                String tittle = rst.getString(2);
                System.out.println(bId+ ", "+ tittle);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
