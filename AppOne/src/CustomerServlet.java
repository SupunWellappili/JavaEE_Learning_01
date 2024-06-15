import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /* System.out.println("working");
        PrintWriter writer = resp.getWriter();
        writer.write("Appone!!");*/

/*            resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions )

        resp.addHeader("Institude", "IJSE");
        resp.addHeader("KEY", "VALUE");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");
            Statement stmt = connection.createStatement();
            ResultSet rst = stmt.executeQuery("select * from customer");

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
                allRecords = allRecords + customer;

            }

            String finalJson = "[" + allRecords.substring(0, allRecords.length() - 1) + "]";

            PrintWriter writer = resp.getWriter();
            //  writer.write(); // text , xml , html , json
            writer.write(finalJson);

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //name value from the input field
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");

        System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);

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

        System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);

    }
}
