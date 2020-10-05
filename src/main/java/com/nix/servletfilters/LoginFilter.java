package com.nix.servletfilters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.nix.utils.IsUserExistValidator;

@WebFilter("/loginpage")
public class LoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setContentType("text/html");
		String login = request.getParameter("uname");
		String pass = request.getParameter("pass");

		PrintWriter out = response.getWriter();

		RequestDispatcher rd;

		if (IsUserExistValidator.validate(login, pass)) {
			chain.doFilter(request, response);
		} else {
			rd = request.getServletContext().getRequestDispatcher("/index.html");
			rd.include(request, response);
			out.print("<h4 style=\"color: red\" align=\"center\">Password or username is incorrect</h4>");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
