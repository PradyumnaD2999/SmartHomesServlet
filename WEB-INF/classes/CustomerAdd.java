import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CustomerAdd")

public class CustomerAdd extends HttpServlet {	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
		Utilities util=new Utilities(request,out);
		if(request.getAttribute("checkMsg") != null)
		{
			out.println("<h3 style='color:black;background-color:blue;'>"+(String)(request.getAttribute("checkMsg"))+"</h3>");
			request.setAttribute("checkMsg","");
		}	
		util.printHtml("HeaderRetailer.html");
		out.print("<div class='post' style='float: none; width: 100%'>");
		out.print("<h2 class='title meta'><a style='font-size: 24px;'>Add New Customer</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		out.print("<form action='Registration' method='post'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<h3>Username</h3></td><td><input type='text' name='username' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Password</h3></td><td><input type='password' name='password' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Re-passowrd</h3></td><td><input type='text' name='repassword' value='' class='input' required></input>"
                + "</td></tr><tr><td>"
                + "</td><td><input type='hidden' name='usertype' value='customer' class='input'></input>"
				+ "</td><td><input type='hidden' name='override' value='yes' class='input'></input>"
				+ "</td></tr></table>" 
				+ "<button  data-toggle='modal' data-target='#myModal' class='btn btn-primary'>Create Customer</button></div><br>");
				out.println("</form>" + "</div></div></div>");
			
		util.printHtml("Footer.html");
	}
}