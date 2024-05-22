import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DoorlockList")

public class DoorlockList extends HttpServlet {

	/* Trending Page Displays all the Tablets and their Information in Game Speed */

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	/* Checks the Tablets type whether it is microsft or apple or samsung */

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Doorlock> hm = new HashMap<String, Doorlock>();

		if (CategoryName == null)	
		{
			hm.putAll(SaxParserDataStore.dlks);
			name = "";
		} 
		else 
		{
			if(CategoryName.equals("philips")) 
			{	
				for(Map.Entry<String,Doorlock> entry : SaxParserDataStore.dlks.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Philips"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Philips";
			} 
			else if (CategoryName.equals("veise"))
			{
				for(Map.Entry<String,Doorlock> entry : SaxParserDataStore.dlks.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Veise"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Veise";
			} 
			else if (CategoryName.equals("wyze")) 
			{
				for(Map.Entry<String,Doorlock> entry : SaxParserDataStore.dlks.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Wyze"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Wyze";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the tablets and tablet information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name + " Doorlocks</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Doorlock> entry : hm.entrySet()) {
			Doorlock dlk = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + dlk.getName() + "</h3>");
			pw.print("<strong>" + dlk.getPrice() + "$</strong><ul>");
			pw.print("<li id='item'><img src='images/dlk/"
					+ dlk.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='dlks'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='ViewProduct'>" +
					"<input type='hidden' name='name' value='"+dlk.getName()+"'>"+
					"<input type='hidden' name='price' value='"+dlk.getPrice()+"'>"+
					"<input type='hidden' name='image' value='"+dlk.getImage()+"'>"+
					"<input type='hidden' name='desc' value='"+dlk.getDescription()+"'>"+
					"<input type='hidden' name='type' value='dlk'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnreview' value='View Product'></form></li>");
			pw.print("<li>" +
					"<form method='post' action='WriteReview'>" +
					"<input type='hidden' name='name' value='"+dlk.getName()+"'>" +
					"<input type='hidden' name='price' value='"+dlk.getPrice()+"'>" +
					"<input type='hidden' name='type' value='doorlock'>" +
					"<input type='hidden' name='maker' value='"+CategoryName+"'>" +
					"<input type='hidden' name='access' value=''>" +
					"<input type='submit' class='btnreview' value='Write Review'>" +
					"</form>" +
					"</li>" +
					"<li>" +
					"<form method='post' action='ViewReview'>" +
					"<input type='hidden' name='name' value='"+dlk.getName()+"'>" +
					"<input type='hidden' name='price' value='"+dlk.getPrice()+"'>" +
					"<input type='hidden' name='type' value='doorlock'>" +
					"<input type='hidden' name='maker' value='"+CategoryName+"'>" +
					"<input type='hidden' name='access' value=''>" +
					"<input type='submit' class='btnreview' value='View Review'>" +
					"</form>" +
					"</li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
	}
}
