package com.banan.server;

import javax.servlet.http.HttpSession;

import com.banan.shared.SessionService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SessionServiceImpl extends RemoteServiceServlet implements SessionService {

	@Override
	public Integer get(String attr) {
		HttpSession session = this.getThreadLocalRequest().getSession();
		return (Integer)session.getAttribute(attr);
	}

	@Override
	public void set(String attr, Integer value) {
		HttpSession session = this.getThreadLocalRequest().getSession();
		session.setAttribute(attr, value);
	}

}
