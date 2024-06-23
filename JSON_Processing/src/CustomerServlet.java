import javax.json.*;
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

        //resp.setStatus(100);
       // resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        //name value from the input field
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions )

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
               // resp.setStatus(201);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);//201
                response.add("status", "201");
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());

            }else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);//201
                response.add("status", "400");
                response.add("message", "Successfully Deleted");
                response.add("data", "Wrong ID Inserted");
                writer.print(response.build());
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            //resp.setStatus(500); // Your Choice Request show Display
            //resp.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
            response.add("status", "404");
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            //resp.setStatus(400);
           // resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//400
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            throwables.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String customerID = req.getParameter("cusID");
        System.out.println(customerID);

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions )

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");

            pstm.setObject(1, customerID);


            if ( pstm.executeUpdate() > 0) {
                JsonObjectBuilder objectB = Json.createObjectBuilder();
                objectB.add("status","200");
                objectB.add("data","");
                objectB.add("message" , "Successfully Deleted");

                writer.print(objectB.build());
            }else {
                JsonObjectBuilder objectB = Json.createObjectBuilder();
                objectB.add("status","400");
                objectB.add("data","Wrong ID Inserted");
                objectB.add("message" , "");

                writer.print(objectB.build());
            }

        } catch (ClassNotFoundException e) {
           // resp.sendError(500,e.getMessage());

            resp.setStatus(200);

            JsonObjectBuilder objectB = Json.createObjectBuilder();
            objectB.add("status","500");
            objectB.add("message" , e.getLocalizedMessage());
            objectB.add("data","");

            writer.print(objectB.build());
            e.printStackTrace();

        } catch (SQLException throwables) {
           // resp.sendError(500, throwables.getMessage());

           // resp.setStatus(HttpServletResponse.SC_OK);
            resp.setStatus(200);


            JsonObjectBuilder objectB = Json.createObjectBuilder();
            objectB.add("status","500");
            objectB.add("message" , throwables.getLocalizedMessage());
            objectB.add("data",throwables.getLocalizedMessage());

            writer.print(objectB.build());
            throwables.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //We have to get updated date form JSON Format
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String customerID = jsonObject.getString("id");
        String customerName = jsonObject.getString("name");
        String customerAddress = jsonObject.getString("address");
        String customerSalary = jsonObject.getString("salary");

        // System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);

        PrintWriter writer = resp.getWriter();


        resp.setContentType("/application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");

            PreparedStatement pstm = connection.prepareStatement("UPDATE  customer SET name=?, address=?, salary=? WHERE id=?");

            pstm.setObject(1, customerName);
            pstm.setObject(2, customerAddress);
            pstm.setObject(3, customerSalary);
            pstm.setObject(4, customerID);


            if (pstm.executeUpdate() > 0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("data","");
                objectBuilder.add("message","Successfully Updated");
                objectBuilder.add("status","200");
                writer.print(objectBuilder.build());

            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("data","");
                objectBuilder.add("message","Updated Flied");
                objectBuilder.add("status","400");
                writer.print(objectBuilder.build());
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("data",e.getLocalizedMessage());
            objectBuilder.add("message","Updated Flied");
            objectBuilder.add("status","500");
            writer.print(objectBuilder.build());
            e.printStackTrace();
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("data",throwables.getLocalizedMessage() );
            objectBuilder.add("message","Updated Flied");
            objectBuilder.add("status","500");
            writer.print(objectBuilder.build());
            throwables.printStackTrace();
            resp.sendError(500, throwables.getMessage());
        }
    }
}