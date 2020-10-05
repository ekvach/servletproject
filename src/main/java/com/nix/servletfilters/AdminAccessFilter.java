package com.nix.servletfilters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter({ "/homepageadmin", "/edituserform", "/edituser", "/deleteuser", "/addnewuserform", "/addnewuser" })
public class AdminAccessFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession(false);

		Long adminId = 1L;
		Object o = session.getAttribute("roleId");

		if (o != null && o.equals(adminId)) {
			chain.doFilter(request, response);
		} else {
			throw new IllegalArgumentException("OOps. You have no access to this page");
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
