import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
			result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			} else {
				result = result +"<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
			}
			result = result +"<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div><div id='page'>";
			pw.print(result);
		} else if(file == "HeaderRetailer.html" || file == "HeaderManager.html") {
			result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				result = result + "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
			result = result +"</div></div><div id='page'>";
			pw.print(result);
		} else
			pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();

		try {		
			hm = MySQLDataStoreUtilities.selectUser();
		}
		catch(Exception e)
		{
		}

		User user = hm.get(username());
		return user;
	}
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		
			try
			{
				orderPayments = MySQLDataStoreUtilities.selectOrder();
			}
			catch(Exception e)
			{
			
			}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
					 size=size + 1;
					 
			}
			return size;		
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());

		HashMap<String,Doorbell> alldbls = new HashMap<String,Doorbell> ();
		HashMap<String,Doorlock> alldlks = new HashMap<String,Doorlock> ();
		HashMap<String,Speaker> allspkrs = new HashMap<String,Speaker> ();
		HashMap<String,Lighting> alllghts = new HashMap<String,Lighting> ();
		HashMap<String,Thermostat> alltrmsts = new HashMap<String,Thermostat> ();

		if(type.equals("spkrs")){
			Speaker spkr;
			try {
				allspkrs = MySQLDataStoreUtilities.getSpkrs();
			} catch(Exception e) {}

			spkr = allspkrs.get(name);

			OrderItem orderitem = new OrderItem(spkr.getName(), spkr.getPrice(), spkr.getImage(), spkr.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("dbls")){
			Doorbell dbl = null;
			try {
				alldbls = MySQLDataStoreUtilities.getDbls();
			} catch(Exception e) {}

			dbl = alldbls.get(name);

			OrderItem orderitem = new OrderItem(dbl.getName(), dbl.getPrice(), dbl.getImage(), dbl.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("dlks")){
			Doorlock dlk = null;
			try {
				alldlks = MySQLDataStoreUtilities.getDlks();
			} catch(Exception e) {}

			dlk = alldlks.get(name);

			OrderItem orderitem = new OrderItem(dlk.getName(), dlk.getPrice(), dlk.getImage(), dlk.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("lghts")){
			Lighting lght = null;
			try {
				alllghts = MySQLDataStoreUtilities.getLghts();
			} catch(Exception e) {}

			lght = alllghts.get(name);

			OrderItem orderitem = new OrderItem(lght.getName(), lght.getPrice(), lght.getImage(), lght.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("trmsts")){
			Thermostat trmst = null;
			try {
				alltrmsts = MySQLDataStoreUtilities.getTrmsts();
			} catch(Exception e) {}

			trmst = alltrmsts.get(name);

			OrderItem orderitem = new OrderItem(trmst.getName(), trmst.getPrice(), trmst.getImage(), trmst.getRetailer());
			orderItems.add(orderitem);
		}
		// if(type.equals("accessories")){	
		// 	Accessory accessory = SaxParserDataStore.accessories.get(name); 
		// 	OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer());
		// 	orderItems.add(orderitem);
		// }
		
	}
	// store the payment details for orders
	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		
			// get the payment details file 
			try
			{
				orderPayments = MySQLDataStoreUtilities.selectOrder();
			}
			catch(Exception e)
			{
			
			}
			if(orderPayments==null)
			{
				orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
			if(!orderPayments.containsKey(orderId)){	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(orderId, arr);
			}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
		listOrderPayment.add(orderpayment);	
			
			// add order details into file

			try
			{	
				MySQLDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}	
	}

	public String storeReview(String productname,String producttype,String productmaker,String reviewrating,String reviewdate,String  reviewtext,String reatilerpin,String price,String city){
		String message=MongoDBDataStoreUtilities.insertReview(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city);
			if(!message.equals("Successfull"))
			{ return "UnSuccessfull";
			}
			else
			{
			HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
			try
			{
				reviews=MongoDBDataStoreUtilities.selectReview();
			}
			catch(Exception e)
			{
				
			}
			if(reviews==null)
			{
				reviews = new HashMap<String, ArrayList<Review>>();
			}
				// if there exist product review already add it into same list for productname or create a new record with product name
				
			if(!reviews.containsKey(productname)){	
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(productname, arr);
			}
			ArrayList<Review> listReview = reviews.get(productname);		
			Review review = new Review(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city);
			listReview.add(review);	
				
				// add Reviews into database
			
			return "Successfull";	
			}
		}

	/* getConsoles Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, Doorbell> getDoorbells(){
			HashMap<String, Doorbell> hm = new HashMap<String, Doorbell>();
			hm.putAll(SaxParserDataStore.dbls);
			return hm;
	}
	
	/* getGames Functions returns the  Hashmap with all Games in the store.*/

	public HashMap<String, Speaker> getSpeakers(){
			HashMap<String, Speaker> hm = new HashMap<String, Speaker>();
			hm.putAll(SaxParserDataStore.spkrs);
			return hm;
	}
	
	/* getTablets Functions returns the Hashmap with all Tablet in the store.*/

	public HashMap<String, Doorlock> getDoorlocks(){
			HashMap<String, Doorlock> hm = new HashMap<String, Doorlock>();
			hm.putAll(SaxParserDataStore.dlks);
			return hm;
	}

	public HashMap<String, Lighting> getLightings(){
			HashMap<String, Lighting> hm = new HashMap<String, Lighting>();
			hm.putAll(SaxParserDataStore.lghts);
			return hm;
	}

	public HashMap<String, Thermostat> getThermostats(){
			HashMap<String, Thermostat> hm = new HashMap<String, Thermostat>();
			hm.putAll(SaxParserDataStore.trmsts);
			return hm;
	}
	
	/* getProducts Functions returns the Arraylist of consoles in the store.*/

	public ArrayList<String> getProductsDoorbell(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Doorbell> entry : getDoorbells().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of games in the store.*/

	public ArrayList<String> getProductsSpeaker(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Speaker> entry : getSpeakers().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of Tablets in the store.*/

	public ArrayList<String> getProductsDoorlock(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Doorlock> entry : getDoorlocks().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductsLighting(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Lighting> entry : getLightings().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsThermostat(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Thermostat> entry : getThermostats().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

}
