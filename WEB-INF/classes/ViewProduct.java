import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@WebServlet("/ViewProduct")


public class ViewProduct extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html");

		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String image = request.getParameter("image");
		String desc = request.getParameter("desc");
		String type =  request.getParameter("type");
        String manufacturer = request.getParameter("maker");
		String result; 

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'>"+
                    "<h3 class='title meta'><a style='font-size: 24px;'>" + name +"</a></h3>" +
					"<div class='productContainer'> <img src = 'images/"+ type + "/" + image +"' style = 'max-width: 200px; max-height: 170px; width:200px; height:170px' alt='' />"+
                                                    "<br/><h5><strong> Price - $" + price + "</strong><h5>"+
                                                    "<h5>" + desc + "</h5>"+
                                                    "</div></div></div>" );

		utility.printHtml("Footer.html");
		
}

}