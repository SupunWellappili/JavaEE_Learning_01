import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /* System.out.println("working");
        PrintWriter writer = resp.getWriter();
        writer.write("Appone!!");*/

        resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions )

        resp.addHeader("Institude", "IJSE");
        resp.addHeader("KEY", "VALUE");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");
            Statement stmt = connection.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM customer");

            String allRecords = " ";

            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);

                //System.out.println(id + " " + name + " " + address + " " + salary);

                //Convert one record for json
                //String customer = "{'id' : '001', 'name' : 'supun', 'address' : 'Galle', salary : 25000}";
                //String customer = "[{'id' : '001', 'name' : 'supun', 'address' : 'Galle', salary : 25000},{'id' : '002', 'name' : 'chandana', 'address' : 'matara', salary : 50000}]";

                String customer = "{\"id\": \"" + id + "\", \"name\":\"" + name + "\",\"address\" : \"" + address + "\", \"salary\" : " + salary + "},";
                allRecords += customer;

            }

            String finalJson = "[" + allRecords.substring(0, allRecords.length() - 1) + "]";

            PrintWriter writer = resp.getWriter();
            //  writer.write(); // text , xml , html , json
            writer.write(finalJson);

        } catch (Exception e) {
            e.printStackTrace();
        }

       /*
       //name value from the input field
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");

        System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);
        */

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*System.out.println("Customer Post Method Invoke");
        PrintWriter writer = resp.getWriter();
        writer.write("Hello Writter !!");*/

        //name value from the input field
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");

        System.out.println(customerID + " " + customerName + " " + customerAddress + " " + customerSalary);


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");
            PreparedStatement pstm = connection.prepareStatement("INSERT into customer VALUES (?,?,?,?)");

            pstm.setObject(1, customerID);
            pstm.setObject(2, customerName);
            pstm.setObject(3, customerAddress);
            pstm.setObject(4, customerSalary);

            boolean b = pstm.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();
            if (b) {
                writer.write("customer Added!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Request Received for delete");
        //if we send data form the application/www-from-urlencoded type doDelete will not
        //catch values form req.getParameter(); that type is not supported
        //but we can send data via Query String
        String customerID = req.getParameter("cusID");
        System.out.println(customerID);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");

            pstm.setObject(1, customerID);

            boolean b = pstm.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();
            if (b) {
                writer.write("customer Deleted !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //name value from the input field
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");

       // System.out.println(customerID + " " + customerName + " " + customerAddress + " " + customerSalary);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");

            PreparedStatement pstm = connection.prepareStatement("UPDATE  customer SET name=?, address=?, salary=? WHERE id=?");

            pstm.setObject(1, customerName);
            pstm.setObject(2, customerAddress);
            pstm.setObject(3, customerSalary);
            pstm.setObject(4, customerID);

            boolean b = pstm.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();
            if (b) {
                writer.write("customer updated !");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException throwables){
            throwables.printStackTrace();
            resp.sendError(500,throwables.getMessage());
        }
    }
}