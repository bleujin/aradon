package net.ion.nradon.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = -2575396681110339309L;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String q = req.getParameter("q");
		PrintWriter out = resp.getWriter();

		out.println("<html>");
		out.println("<body>");
		out.println("The paramter q was \"" + q + "\".");
		out.println("</body>");
		out.println("</html>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String field = req.getParameter("field");
		PrintWriter out = resp.getWriter();

		out.println("<html>");
		out.println("<body>");
		out.println("You entered \"" + field + "\" into the text box.");
		out.println("</body>");
		out.println("</html>");
	}
}
