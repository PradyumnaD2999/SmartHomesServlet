import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ThermostatList")

public class ThermostatList extends HttpServlet {

	/* Games Page Displays all the Games and their Information in Game Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the Games type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Thermostat> hm = new HashMap<String, Thermostat>();
		
		if(CategoryName==null)
		{
			hm.putAll(SaxParserDataStore.trmsts);
			name = "";
		}
		else
		{
		  if(CategoryName.equals("amazon"))
		  {
			for(Map.Entry<String, Thermostat> entry : SaxParserDataStore.trmsts.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Amazon"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Amazon";
		  }
		  else if(CategoryName.equals("google"))
		  {
			for(Map.Entry<String, Thermostat> entry : SaxParserDataStore.trmsts.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Google"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Google";
		  }
		  else if(CategoryName.equals("honeywell"))
		  {
			for(Map.Entry<String, Thermostat> entry : SaxParserDataStore.trmsts.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Honeywell"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Honeywell";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Games and Games information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Thermostats</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Thermostat> entry : hm.entrySet()){
			Thermostat trmst = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+trmst.getName()+"</h3>");
			pw.print("<strong>"+ "$" + trmst.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/trmst/"+trmst.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='trmsts'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='ViewProduct'>" +
					"<input type='hidden' name='name' value='"+trmst.getName()+"'>"+
					"<input type='hidden' name='price' value='"+trmst.getPrice()+"'>"+
					"<input type='hidden' name='image' value='"+trmst.getImage()+"'>"+
					"<input type='hidden' name='desc' value='"+trmst.getDescription()+"'>"+
					"<input type='hidden' name='type' value='trmst'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnreview' value='View Product'></form></li>");
			pw.print("<li>" +
					"<form method='post' action='WriteReview'>" +
					"<input type='hidden' name='name' value='"+trmst.getName()+"'>" +
					"<input type='hidden' name='price' value='"+trmst.getPrice()+"'>" +
					"<input type='hidden' name='type' value='thermostat'>" +
					"<input type='hidden' name='maker' value='"+CategoryName+"'>" +
					"<input type='hidden' name='access' value=''>" +
					"<input type='submit' class='btnreview' value='Write Review'>" +
					"</form>" +
					"</li>" +
					"<li>" +
					"<form method='post' action='ViewReview'>" +
					"<input type='hidden' name='name' value='"+trmst.getName()+"'>" +
					"<input type='hidden' name='price' value='"+trmst.getPrice()+"'>" +
					"<input type='hidden' name='type' value='thermostat'>" +
					"<input type='hidden' name='maker' value='"+CategoryName+"'>" +
					"<input type='hidden' name='access' value=''>" +
					"<input type='submit' class='btnreview' value='View Review'>" +
					"</form>" +
					"</li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
