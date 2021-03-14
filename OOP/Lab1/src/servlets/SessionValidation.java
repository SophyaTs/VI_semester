package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionValidation {
	private static List<HttpSession> sessions = new ArrayList<>();
	
	public static boolean validateSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session != null && sessions.contains(session)) {
			System.out.println("Valid session");
			return true;
		}
		
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return false;		
	}
	
	public static void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			sessions.remove(session);
			session.invalidate();
			System.out.println("Session invalidated");
		} else {
			System.out.println("Session was already null");
		}
	}
	
	public static void createSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		sessions.add(session);
		System.out.println("Created new session");
	}
}
