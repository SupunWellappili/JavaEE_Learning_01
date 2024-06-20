
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Someone called DO PUT method");

      /* ServletInputStream inputStream = req.getInputStream();

        int read = inputStream.read();
        System.out.println((char) read);

        System.out.println("////////");

        while ((read=inputStream.read()) != -1){
            System.out.print(read+", ");
        }*/

        /*int read;
        while ((read = inputStream.read()) != -1){
            System.out.print((char)read);
        }*/



        //How to work with JSON Processing
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        
        String id = jsonObject.getString("id");
        System.out.println(id);

        String name = jsonObject.getString("name");
        System.out.println(name);


    }
}

