import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProductCrud")

public class ProductCrud extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			String action = request.getParameter("button");
			
			String msg = "good";
			String producttype= "",productId="",productName="",productImage="",productManufacturer="",productCondition="",prod = "",description="";
			double productPrice=0.0,productDiscount = 0.0;

			HashMap<String,Doorbell> alldbls = new HashMap<String,Doorbell> ();
			HashMap<String,Doorlock> alldlks = new HashMap<String,Doorlock> ();
			HashMap<String,Speaker> allspkrs = new HashMap<String,Speaker> ();
            HashMap<String,Lighting> alllghts = new HashMap<String,Lighting> ();
            HashMap<String,Thermostat> alltrmsts = new HashMap<String,Thermostat> ();
			HashMap<String,Accessory> allaccessories=new HashMap<String,Accessory>();
            
			if (action.equals("add") || action.equals("update"))
			{	
				 producttype = request.getParameter("producttype");
				 productId   = request.getParameter("productId");
				 productName = request.getParameter("productName"); 
				 productPrice = Double.parseDouble(request.getParameter("productPrice"));
				 productImage = request.getParameter("productImage");
				 productManufacturer = request.getParameter("productManufacturer");
				 productCondition = request.getParameter("productCondition");
				 productDiscount = Double.parseDouble(request.getParameter("productDiscount"));
				 description = request.getParameter("desc");
			}
			else{
				productId   = request.getParameter("productId");
			}	
			utility.printHtml("HeaderManager.html");

			if(action.equals("add"))
			{
			  			if(producttype.equals("dbls")){
				  alldbls = MySQLDataStoreUtilities.getDbls();
				  if(alldbls.containsKey(productId)){
					  msg = "Product already available";
					  
				  }
					  
			  }else if(producttype.equals("spkrs"))
			  {
				  allspkrs = MySQLDataStoreUtilities.getSpkrs();
				  if(allspkrs.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }else if (producttype.equals("dlks"))
			  {
				  alldlks = MySQLDataStoreUtilities.getDlks();
				  if(alldlks.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }else if (producttype.equals("lghts"))
			  {
				  alllghts = MySQLDataStoreUtilities.getLghts();
				  if(alllghts.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }else if (producttype.equals("trmsts"))
			  {
				  alltrmsts = MySQLDataStoreUtilities.getTrmsts();
				  if(alltrmsts.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }
            // else if (producttype.equals("accessories"))
			//   {  
			// 		if(!request.getParameter("product").isEmpty())
			// 			{
			// 				prod = request.getParameter("product");
			// 				alldbls = MySqlDataStoreUtilities.getDbls();
			// 				if(alldbls.containsKey(prod))
			// 				{
			// 					allaccessories = MySqlDataStoreUtilities.getAccessories();
			// 					if(allaccessories.containsKey(productId)){
			// 						msg = "Product already available";
			// 					}
			// 				}else{
			// 					msg = "The product related to accessories is not available";
			// 				}
						
						
			// 			}else{
			// 				msg = "Please add the prodcut name";
			// 			}
				  	
			  if (msg.equals("good"))
			  {  
				  try
				  {
					  msg = MySQLDataStoreUtilities.addproducts(producttype,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,prod,description);
				  }
				  catch(Exception e)
				  { 
					msg = "Product cannot be inserted";
				  }
				  msg = "Product has been successfully added";
			  }					
			}else if(action.equals("update"))
			{
				
			  if(producttype.equals("dbls")){
				  alldbls = MySQLDataStoreUtilities.getDbls();
				  if(!alldbls.containsKey(productId)){
					  msg = "Product not available";
				  }
					  
			  }else if(producttype.equals("spkrs"))
			  {
				  allspkrs = MySQLDataStoreUtilities.getSpkrs();
				  if(!allspkrs.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }else if (producttype.equals("dlks"))
			  {
				  alldlks = MySQLDataStoreUtilities.getDlks();
				  if(!alldlks.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }else if (producttype.equals("lghts"))
			  {
				  alllghts = MySQLDataStoreUtilities.getLghts();
				  if(!alllghts.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }else if (producttype.equals("trmsts"))
			  {
				  alltrmsts = MySQLDataStoreUtilities.getTrmsts();
				  if(!alltrmsts.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }
            //   else if (producttype.equals("accessories"))
			//   {
			// 	  allaccessories = MySqlDataStoreUtilities.getAccessories();
			// 	  if(!allaccessories.containsKey(productId)){
			// 		  msg = "Product not available";
			// 	}
			//   }	
			  if (msg.equals("good"))
			  {		
				
				  try
				  {
					msg = MySQLDataStoreUtilities.updateproducts(producttype,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,description);
				  }
				  catch(Exception e)
				  { 
					msg = "Product cannot be updated";
				  }
				  msg = "Product has been successfully updated";
			  } 
			}else if(action.equals("delete"))
			{
				  msg = "bad";
				  alldbls = MySQLDataStoreUtilities.getDbls();
				  if(alldbls.containsKey(productId)){
					  msg = "good";
					 
				  }
					  
			  
				  allspkrs = MySQLDataStoreUtilities.getSpkrs();
				  if(allspkrs.containsKey(productId)){
					  msg = "good";
				  }
			  
				  alldlks = MySQLDataStoreUtilities.getDlks();
				  if(alldlks.containsKey(productId)){
					  msg = "good";
				  }

                  alllghts = MySQLDataStoreUtilities.getLghts();
				  if(alllghts.containsKey(productId)){
					  msg = "good";
				  }

                  alltrmsts = MySQLDataStoreUtilities.getTrmsts();
				  if(alltrmsts.containsKey(productId)){
					  msg = "good";
				  }
			  
				//   allaccessories = MySqlDataStoreUtilities.getAccessories();
				//   if(allaccessories.containsKey(productId)){
				// 	  msg = "good";
				// }
		       		
				  if (msg.equals("good"))
				  {		
					
					  try
					  {  
						
						 msg = MySQLDataStoreUtilities.deleteproducts(productId);
					  }
					  catch(Exception e)
					  { 
						msg = "Product cannot be deleted";
					  }
					   msg = "Product has been successfully deleted";
				  }else{
					  msg = "Product not available";
				  }
			}	
				
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<h4 style='color:blue'>"+msg+"</h4>");
			pw.print("</div></div></div>");		
			utility.printHtml("Footer.html");
			
	}
}