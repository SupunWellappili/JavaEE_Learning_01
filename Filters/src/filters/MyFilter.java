package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter(urlPatterns = "/item")
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
        System.out.println("Stat");
       // System.out.println("Do Filter Method Called");

        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("End");

    }

    @Override
    public void destroy() {
        System.out.println("Destroy Filter Method Invoked");

    }
}
