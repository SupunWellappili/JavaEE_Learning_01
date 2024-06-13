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


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Test", "root", "87654321");
            Statement stmt = connection.createStatement();
            ResultSet rst = stmt.executeQuery("select * from customer");
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                Double salary = rst.getDouble(4);

                System.out.println(id+" "+name+" "+address+" "+salary);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
