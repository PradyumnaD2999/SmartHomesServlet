import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SpeakerList")

public class SpeakerList extends HttpServlet {

	/* Console Page Displays all the Consoles and their Information in Game Speed */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("maker");
        

		/* Checks the Tablets type whether it is microsft or sony or nintendo */

		HashMap<String, Speaker> hm = new HashMap<String, Speaker>();
		if(CategoryName==null){
			hm.putAll(SaxParserDataStore.spkrs);
			name = "";
		}
		else
		{
		   if(CategoryName.equals("echo"))
		   {
			 for(Map.Entry<String,Speaker> entry : SaxParserDataStore.spkrs.entrySet())
			 {
				if(entry.getValue().getRetailer().equals("Echo"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
			 }
				name = "Echo";
		   }
		   else if(CategoryName.equals("bose"))
		    {
			for(Map.Entry<String,Speaker> entry : SaxParserDataStore.spkrs.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("Bose"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "Bose";
			}
			else if(CategoryName.equals("ihome"))
			{
				for(Map.Entry<String,Speaker> entry : SaxParserDataStore.spkrs.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("iHome"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "iHome";
			}
		}

		
		/* Header, Left Navigation Bar are Printed.

		All the Console and Console information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Speakers</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Speaker> entry : hm.entrySet())
		{
			Speaker spkr = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+spkr.getName()+"</h3>");
			pw.print("<strong>$"+spkr.getPrice()+"</strong><ul>");
			pw.print("<li id='item'><img src='images/spkr/"+spkr.getImage()+"' alt='' /></li>");
			
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='spkrs'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='ViewProduct'>" +
					"<input type='hidden' name='name' value='"+spkr.getName()+"'>"+
					"<input type='hidden' name='price' value='"+spkr.getPrice()+"'>"+
					"<input type='hidden' name='image' value='"+spkr.getImage()+"'>"+
					"<input type='hidden' name='desc' value='"+spkr.getDescription()+"'>"+
					"<input type='hidden' name='type' value='spkr'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnreview' value='View Product'></form></li>");
			pw.print("<li>" +
					"<form method='post' action='WriteReview'>" +
					"<input type='hidden' name='name' value='"+spkr.getName()+"'>" +
					"<input type='hidden' name='price' value='"+spkr.getPrice()+"'>" +
					"<input type='hidden' name='type' value='speaker'>" +
					"<input type='hidden' name='maker' value='"+CategoryName+"'>" +
					"<input type='hidden' name='access' value=''>" +
					"<input type='submit' class='btnreview' value='Write Review'>" +
					"</form>" +
					"</li>" +
					"<li>" +
					"<form method='post' action='ViewReview'>" +
					"<input type='hidden' name='name' value='"+spkr.getName()+"'>" +
					"<input type='hidden' name='price' value='"+spkr.getPrice()+"'>" +
					"<input type='hidden' name='type' value='speaker'>" +
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
