package by.epamtr.totalizator.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtr.totalizator.command.Command;
import by.epamtr.totalizator.command.exception.CommandException;

public class ChangeLanguageCommand implements Command {

	private final static String LOCAL = "local";
	private final static String CURRENT_URL = "currentUrl";
	private final static String LOCALHOST = "http://localhost:8080/Totalizator/";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		String url = null;
		HttpSession session = request.getSession(false);
		session.setAttribute(LOCAL, request.getParameter(LOCAL));
		if (session.getAttribute(CURRENT_URL) != null) {
			url = LOCALHOST + session.getAttribute(CURRENT_URL);
		} else {
			url = LOCALHOST;
		}
		return url;
	}

}