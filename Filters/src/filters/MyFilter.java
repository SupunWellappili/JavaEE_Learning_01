package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


//@WebFilter(urlPatterns = {"/customer","/item","/order"})
@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {
    public MyFilter() {
        System.out.println("Object Created From My Filter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("My Filter initialized");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       // System.out.println("Do Filter Method Called");

        //before the request send
        System.out.println("Stat");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //without this line the request will not proceed to the servlet
        filterChain.doFilter(servletRequest,servletResponse);//process request to the servlet

        PrintWriter writer = servletResponse.getWriter();
        writer.print("MY-COMPANY" +" "+ "IJSE");

        resp.addHeader("MY-COMPANY","IJSE");

        //after the request response
        System.out.println("End");

    }

    @Override
    public void destroy() {
        System.out.println("Destroy Filter Method Invoked");

    }
}
