package com.nix.customtagmanager;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.nix.jdbcdaotask.User;

public class UserListPrinterTagHandler extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	private List<User> userList;
	private Iterator<User> iterator;
	private User user;

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public int doStartTag() {
		if (userList.size() <= 0) {
			return SKIP_BODY;
		} else {
			return EVAL_BODY_BUFFERED;
		}
	}

	@Override
	public void doInitBody() throws JspException {
		iterator = userList.iterator();
		if (iterator.hasNext()) {
			user = (User) iterator.next();
			this.setUserAttributes(user);
		}
	}

	private void setUserAttributes(User u) {

		pageContext.setAttribute("login", u.getLogin());
		pageContext.setAttribute("firstName", u.getFirstName());
		pageContext.setAttribute("lastName", u.getLastName());
		pageContext.setAttribute("age", u.getAge());
		pageContext.setAttribute("roleName", u.getRoleName());
		pageContext.setAttribute("userId", u.getId());
	}

	@Override
	public int doAfterBody() throws JspException {
		try {
			if (iterator.hasNext()) {
				user = (User) iterator.next();
				this.setUserAttributes(user);
				return EVAL_BODY_AGAIN;
			} else {
				JspWriter out = bodyContent.getEnclosingWriter();
				bodyContent.writeOut(out);
				return SKIP_BODY;
			}
		} catch (IOException ioe) {
			System.err.println("doAfterBody: " + ioe.getMessage());
			return SKIP_BODY;
		}
	}
}
