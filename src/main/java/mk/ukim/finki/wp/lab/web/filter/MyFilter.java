package mk.ukim.finki.wp.lab.web.filter;

import mk.ukim.finki.wp.lab.model.Course;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Course course = (Course)request.getSession().getAttribute("course");
        String path = request.getServletPath();

        ///courses/add
        if (course == null && !"/favicon.ico".equals(path) && !"/createTeacher".equals(path) &&!"/AddStudent".equals(path) &&!path.contains("/courses") && !"/main.css".equals(path)) {
            response.sendRedirect("/courses?error=CourseNotSelected");
        } else {
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
