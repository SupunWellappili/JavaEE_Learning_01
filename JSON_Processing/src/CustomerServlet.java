import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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

        resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions )

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");
            Statement stmt = connection.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM customer");

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            //Access the record and generate a json object
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);

                //{"id":"C001","name":"Supun","address":"Galle","salary":23000}
                //Create a json object and store values
                JsonObjectBuilder objectB = Json.createObjectBuilder();
                objectB.add("id", id);
                objectB.add("name", name);
                objectB.add("address", address);
                objectB.add("salary", salary);

                //add the json object to the json array
                arrayBuilder.add(objectB.build());
            }

            //Then build and print the json array
            PrintWriter writer = resp.getWriter();

            //Generate a customer response with json
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", "200");
            response.add("message", "Done");
            response.add("data", arrayBuilder.build());

            // writer.print(arrayBuilder.build());
            writer.print(response.build());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(500, throwables.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //name value from the input field
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");

        // System.out.println(customerID + " " + customerName + " " + customerAddress + " " + customerSalary);

        PrintWriter writer = resp.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");
            PreparedStatement pstm = connection.prepareStatement("INSERT into customer VALUES (?,?,?,?)");

            pstm.setObject(1, customerID);
            pstm.setObject(2, customerName);
            pstm.setObject(3, customerAddress);
            pstm.setObject(4, customerSalary);


            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", "200");
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", "500");
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();


        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 500);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            throwables.printStackTrace();
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

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(500, throwables.getMessage());
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(500, throwables.getMessage());
        }
    }
}