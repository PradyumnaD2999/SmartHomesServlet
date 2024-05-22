import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InventoryReport")

public class InventoryReport extends HttpServlet {
	HashMap<String, Inventory> allProducts = new HashMap<String, Inventory>();
	HashMap<String, Inventory> onRebate = new HashMap<String, Inventory>();
	HashMap<String, Inventory> onSale = new HashMap<String, Inventory>();

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		allProducts = MySQLDataStoreUtilities.getAllProducts();		
		onRebate = MySQLDataStoreUtilities.getOnRebate();		
		onSale = MySQLDataStoreUtilities.getOnSale();		

		String CategoryName = request.getParameter("button");
		
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("HeaderManager.html");
		pw.print("<br></br>");
		
		if(CategoryName.equals("AllProducts")) {
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Inventory</a>");
			pw.print("</h2><div class='entry'><table id='bestseller'>");
			for(Map.Entry<String, Inventory> entry : allProducts.entrySet()) {
                Inventory inventory = entry.getValue();
                
                pw.print("<table style='width:100%'>"
                        +"<tr><td><h3>Product Name</h3></td><td>"+inventory.getName()+ "</td></tr>"
                        +"<tr><td><h3>Product Price</h3></td><td>"+inventory.getPrice()+ "</td></tr>"
                        +"<tr><td><h3>Total Items</h3></td><td>"+inventory.getproductQuantity()+"</td></tr>"
                        + "</td></tr></table>");
			}

			pw.print("</div></div></div>");
		} else if(CategoryName.equals("BarGraph")) {
			pw.print("Total Available Items For Products");
            pw.println("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
            pw.println("<script type='text/javascript'>");
            pw.println("google.charts.load('current', {packages: ['corechart', 'bar']});");
            pw.println("google.charts.setOnLoadCallback(drawBasic);");
            pw.println("function drawBasic() {");
            pw.println("var data = google.visualization.arrayToDataTable([");
            pw.println("['Product', 'Total Items'],");
               
			for(Map.Entry<String, Inventory> entry : allProducts.entrySet()){
			    Inventory inventory = entry.getValue();
                   
				String productName = inventory.getName();
                int quantity = inventory.getproductQuantity();
                pw.println("[' " +productName+ " ', "+quantity+ "],");
            }

            pw.println("]);");
            pw.println("var options = {");
            pw.println("title: 'Total Available Items For Products',");
            pw.println("chartArea: {width: '50%', height: '50%'},");
            pw.println("hAxis: {");
            pw.println("title: 'Total Items',");
            pw.println("minValue: 0");
            pw.println("},");
            pw.println("vAxis: {");
            pw.println("title: 'Product'");
            pw.println("}");
            pw.println("};");
            pw.println("var chart = new google.visualization.BarChart(document.getElementById('chart_div'));");
            pw.println("chart.draw(data, options);");
            pw.println("}");
            pw.println("</script>");
			pw.println("<div id='chart_div' style='width:900px; height:500px'></div>");
			
		} else if(CategoryName.equals("OnSale")) {
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Products On Sale</a>");
			pw.print("</h2><div class='entry'><table id='bestseller'>");

			for(Map.Entry<String, Inventory> entry : onSale.entrySet()){
                Inventory inventory = entry.getValue();
                
                pw.print("<table style='width:100%'>"
                        +"<tr><td><h3>Product Name</h3></td><td>"+inventory.getName()+ "</td></tr>"
                        +"<tr><td><h3>Product Price</h3></td><td>"+inventory.getPrice()+ "</td></tr>"
                        + "</td></tr></table>");
			}

			pw.print("</div></div></div>");
		} else if(CategoryName.equals("OnRebate")) {
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Products On Rebate</a>");
			pw.print("</h2><div class='entry'><table id='bestseller'>");
			
			for(Map.Entry<String, Inventory> entry : onRebate.entrySet()){
			    Inventory inventory = entry.getValue();
                
                pw.print("<table style='width:100%'>"
                        +"<tr><td><h3>Product Name</h3></td><td>"+inventory.getName()+ "</td></tr>"
                        +"<tr><td><h3>Product Price</h3></td><td>"+inventory.getPrice()+ "</td></tr>"
                        + "</td></tr></table>");
			}
            
			pw.print("</div></div></div>");
		}
		
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
