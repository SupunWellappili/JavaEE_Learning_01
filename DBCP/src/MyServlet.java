import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/hello")
public class MyServlet extends HttpServlet {

    public MyServlet() {
        System.out.println("onna Constructor heduna");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("onna initialise unaa");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("onna get request aka dunna");
    }

    @Override
    public void destroy() {
        System.out.println("onna mala");
    }
}

