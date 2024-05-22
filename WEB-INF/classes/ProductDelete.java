import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductDelete")

public class ProductDelete extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
		HttpSession session= request.getSession();
		Utilities util=new Utilities(request,out);
		
		util.printHtml("HeaderManager.html");
		out.println("<div class='row'><a style='font-size: 24px;''>Delete Product</a> ");
		out.println("<form action=\"ProductDelete\" >");
		out.println("<div class='form-group'><label>Select Product:</label>"
        +"<select class='form-control' name='pcategory'>"
        +"<option value='dbl1' selected>Google Nest Doorbell (Wired)</option>"
        +"<option value='dbl2'>EKEN Smart Video Doorbell</option>"
        +"<option value='dbl3'>REOLINK Video Doorbell PoE Camera</option>"
        +"<option value='dbl4'>Google Nest Doorbell (Wireless)</option>"
        +"<option value='dbl5'>REOLINK Doorbell WiFi Camera</option>"
        +"<option value='dlk1'>Philips Smart Lock</option>"
        +"<option value='dlk2'>Philips Keyless Entry Door Lock with Keypad</option>"
        +"<option value='dlk3'>Veise Smart Lock</option>"
        +"<option value='dlk4'>Veise Smart Lock (Keyless Entry)</option>"
        +"<option value='dlk5'>Wyze Lock Bolt</option>"
        +"<option value='spkr1'>Echo Dot (5th Gen, 2022 release)</option>"
        +"<option value='spkr2'>Echo Pop</option>"
        +"<option value='spkr3'>Bose Home Speaker 500</option>"
        +"<option value='spkr4'>iHome iBT158 Smart Bluetooth Speaker</option>"
        +"<option value='spkr5'>Echo Studio</option>"
        +"<option value='lght1'>Philips Hue White and Color Ambiance Smart Light</option>"
        +"<option value='lght2'>Govee Smart LED Light Bars</option>"
        +"<option value='lght3'>Govee Smart Light Bulbs</option>"
        +"<option value='lght4'>Kasa Smart LED Light Strip</option>"
        +"<option value='lght5'>Philips Hue 2-Pack Bluetooth Smart Lightstrip</option>"
        +"<option value='trmst1'>Amazon Smart Thermostat</option>"
        +"<option value='trmst2'>Google Nest Thermostat</option>"
        +"<option value='trmst3'>Google Nest Learning Thermostat</option>"
        +"<option value='trmst4'>Honeywell Home Wi-Fi Smart Color Thermostat</option>"
        +"<option value='trmst5'>Honeywell Home Wi-Fi Programmable Thermostat</option>"
        +"</select></div>");
		out.println("</form></div>");
		out.println("<button  data-toggle='modal' data-target='#myModal2' class='btn btn-primary'>Delete</button></div>");
		out.println("<div id='myModal2' class='modal fade' role='dialog'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'> <button type='button' class='close' data-dismiss='modal'>&times;</button> <h4 class='modal-title'>Product Status</h4> </div>");  
		out.println("<div class='modal-body'><p>Product Deleted</p></div><div class='modal-footer'><button type='button' class='btn btn-default' data-dismiss='modal'>Close</button></div></div></div></div>");
	}
}