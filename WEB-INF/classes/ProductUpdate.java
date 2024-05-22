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

@WebServlet("/ProductUpdate")

public class ProductUpdate extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
		HttpSession session= request.getSession();
		Utilities util=new Utilities(request,out);
		
		util.printHtml("HeaderManager.html");
		out.println("<div class='row'><a style='font-size: 24px;''>Update Product</a> ");
		out.println("<form action= \"ProductUpdate\">");
		out.println("<div class='form-group'><label>Category:</label>"
        +"<select class='form-control' name='pcategory'>"
        +"<option value='dbls' selected>Smart Doorbell</option>"
        +"<option value='dlks'>Smart Doorlock</option>"
        +"<option value='spkrs'>Smart Speaker</option>"
        +"<option value='lghts'>Smart Lighting</option>"
        +"<option value='trmsts'>Smart Thermostat</option>"
        +"</select></div>");
		out.println("<div class='form-group'><label>Name:</label><input type='text' class='form-control' name='pname' placeholder='Enter Product Name'  /></div>");
		out.println("<div class='form-group'><label>Description:</label></br><textarea class='form-control' placeholder='Enter Product Description' name='pdescription' ></textarea></div>");
		out.println("<div class='form-group'><label>Color:</label></br><input class='form-control' type='text' placeholder='Enter Product Color' name='pcolor'  \"></div>");
		out.println("<div class='form-group'><label>Price:</label><input class='form-control' type='text' placeholder='Enter Product Price' name='pprice' \"></div>");
		out.println("<div class='form-group'><label>Image Name:</label></br><input class='form-control' type='text'  placeholder='Enter Product Image Name(with Extension)' name='pimage'  ></div>");
		out.println("<div class='form-group'><label>Condition:</label></br><input class='form-control' type='text' placeholder='Enter Product Condition' name='pcondition'  \"></div>");
		out.println("<div class='form-group'><label>Company:</label></br><input class='form-control' type='text' placeholder='Enter Product Company' name='pcompany'  \"></div>");
		out.println("</form></div>");
		out.println("<button  data-toggle='modal' data-target='#myModal' class='btn btn-primary'>Update Product</button></div>");
		out.println("<div id='myModal' class='modal fade' role='dialog'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'> <button type='button' class='close' data-dismiss='modal'>&times;</button> <h4 class='modal-title'>Product Status</h4> </div>");  
		out.println("<div class='modal-body'><p>Product Updated</p></div><div class='modal-footer'><button type='button' class='btn btn-default' data-dismiss='modal'>Close</button></div></div></div></div>");
	}
}