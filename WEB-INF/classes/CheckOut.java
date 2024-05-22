import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.time.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 

		//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    String userName = session.getAttribute("username").toString();
        String orderTotal = request.getParameter("orderTotal");
		String orderType = request.getParameter("orderType");
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		LocalDate orderDate = LocalDate.now();

		pw.print("<form name ='CheckOut' action='Payment' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<br/><strong>Your " + orderType + " date will be "+(orderDate.plusDays(14)) + "</strong><br/>");
		pw.print("<table  class='gridtable'><tr><td>Customer Name:</td><td>");
		pw.print(userName);
		pw.print("</td></tr>");
		// for each order iterate and display the order name price
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			pw.print("<tr><td> Product Purchased:</td><td>");
			pw.print(oi.getName()+"</td></tr><tr><td>");
			pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
			pw.print("Product Price:</td><td>"+ oi.getPrice());
			pw.print("</td></tr>");
		}
		pw.print("<tr><td>");
        pw.print("Total Order Cost</td><td>"+orderTotal);
		pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
		pw.print("</td></tr></table><br/>");

		if(orderType.equals("pickup")) {
			pw.print("<table class = 'custDeets'><tr><td>");
			pw.print("<p>Select Pickup Location:</p></td>");
			pw.print("<td><select name='location' id='location'>"+
					 "<option value='60108'>Bloomingdale 60108</option>"+
					 "<option value='60564'>Naperville 60564</option>"+
					 "<option value='60453'>Oak Lawn 60453</option>"+
					 "<option value='60173'>Shaumburg 60173</option>"+
					 "<option value='60559'>Westmont 60559</option>"+
					 "<option value='60053'>Morton Grove 60053</option>"+
					 "<option value='60563'>Naperville 60563</option>"+
					 "<option value='60546'>North Riverside 60546</option>"+
					 "<option value='60706'>Norridge 60706</option>"+
					 "<option value='60616'>Chicago 60616</option>"+
					 "</select>");
			pw.print("</td></tr></table>");
		}
		
		pw.print("<table class = 'custDeets'><tr></tr><tr></tr><tr><td>");
		pw.print("First Name:</td>");
		pw.print("<td><input type='text' name='firstname'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("Last Name:</td>");
		pw.print("<td><input type='text' name='lastname'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("Address 1:</td>");
		pw.print("<td><input type='text' name='address1'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("Address 2(optional):</td>");
		pw.print("<td><input type='text' name='address2'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("City:</td>");
		pw.print("<td><input type='text' name='city'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("State:</td>");
		pw.print("<td><input type='text' name='state'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("Zip/Postal:</td>");
		pw.print("<td><input type='text' name='zipcode'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("Phone Number:</td>");
		pw.print("<td><input type='text' name='phone'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
     	pw.print("Credit/accountNo</td>");
		pw.print("<td><input type='text' name='creditCardNo'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
		

		pw.print("<tr><td colspan='2'>");
		pw.print("<input type='submit' name='submit' class='btnbuy'>");
        pw.print("</td></tr></table></form>");
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    }
}
