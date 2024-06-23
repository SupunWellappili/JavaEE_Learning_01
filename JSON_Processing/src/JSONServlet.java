
import jdk.nashorn.internal.scripts.JD;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/json")
public class JSONServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //"id": "c-001" , "name":"Ravi", "address: "Galle"


        //Content-Type : application/json;charset=ISO-8859-1
        resp.setContentType("application/json");

        //How to generate a single JSON object using JSON Processing
        /*JsonObjectBuilder objectB = Json.createObjectBuilder();
        objectB.add("id","c-001");
        objectB.add("name","Ravi");
        objectB.add("address","Galle");
        JsonObject build = objectB.build();

        PrintWriter writer = resp.getWriter();
        writer.print(build);*/

        //How to generate a array JSON object using JSON Processing
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();//create a json array

        JsonObjectBuilder objectB = Json.createObjectBuilder(); //create json object one
        objectB.add("id", "c-001");
        objectB.add("name", "Ravi");
        objectB.add("address", "Galle");

        JsonObjectBuilder objectB2 = Json.createObjectBuilder();//create json object two
        objectB2.add("id", "c-001");
        objectB2.add("name", "Ravi");
        objectB2.add("address", "Galle");

        arrayBuilder.add(objectB.build()); //add json object one to the json array
        arrayBuilder.add(objectB2.build()); //add json object two to the json array

        PrintWriter writer = resp.getWriter();//then print the json array as the response
        writer.print(arrayBuilder.build());

    }

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
        //How to retrieve data from JSON request using JSON Processing spec
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String id = jsonObject.getString("id");
        System.out.println(id);

        String name = jsonObject.getString("name");
        System.out.println(name);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonArray jsonArray = reader.readArray();

        for (JsonValue jsonValue : jsonArray) {
            String customerID = jsonValue.asJsonObject().getString("id");
            String customerName = jsonValue.asJsonObject().getString("name");
            String customerAddress = jsonValue.asJsonObject().getString("address");
            String customerSalary = jsonValue.asJsonObject().getString("salary");

            System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);
        }
    }
}

