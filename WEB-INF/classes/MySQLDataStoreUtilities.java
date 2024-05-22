import java.io.Console;
import java.sql.*;
import java.util.*;
                	
public class MySQLDataStoreUtilities {
    static Connection conn = null;

    public static void getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smarthomesdb","root","root");							
        } catch(Exception e) {}
    }


    public static void deleteOrder(int orderId,String orderName) {
        try {
            getConnection();
            String deleteOrderQuery ="Delete from orders where OrderId=? and orderName=?";
            PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
            pst.setInt(1,orderId);
            pst.setString(2,orderName);
            pst.executeUpdate();
        } catch(Exception e) {}
    }

    public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo) {
        try {
            getConnection();
            String insertIntoCustomerOrderQuery = "INSERT INTO orders(OrderId,UserName,OrderName,OrderPrice,userAddress,creditCardNo) "
            + "VALUES (?,?,?,?,?,?);";	
                
            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
            //set the parameter for each column and execute the prepared statement
            pst.setInt(1,orderId);
            pst.setString(2,userName);
            pst.setString(3,orderName);
            pst.setDouble(4,orderPrice);
            pst.setString(5,userAddress);
            pst.setString(6,creditCardNo);
            pst.execute();
        }
        catch(Exception e) {}		
    }

    public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder() {	
        HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
            
        try {					
            getConnection();
            //select the table 
            String selectOrderQuery ="select * from orders";			
            PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
            ResultSet rs = pst.executeQuery();	
            ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
            
            while(rs.next()) {
                if(!orderPayments.containsKey(rs.getInt("OrderId"))) {	
                    ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
                    orderPayments.put(rs.getInt("orderId"), arr);
                }

                ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));

                //add to orderpayment hashmap
                OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"));
                listOrderPayment.add(order);			
            }			
        } catch(Exception e) {}

        return orderPayments;
    }


    public static void insertUser(String username,String password,String repassword,String usertype) {
        try {	
            getConnection();
            String insertIntoCustomerRegisterQuery = "INSERT INTO users(username,password,repassword,usertype) "
            + "VALUES (?,?,?,?);";	
                    
            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
            pst.setString(1,username);
            pst.setString(2,password);
            pst.setString(3,repassword);
            pst.setString(4,usertype);
            pst.execute();
        } catch(Exception e) {}	
    }

    public static HashMap<String,User> selectUser() {	
        HashMap<String,User> hm=new HashMap<String,User>();
        try {
            getConnection();
            Statement stmt=conn.createStatement();
            String selectCustomerQuery="select * from  users";
            ResultSet rs = stmt.executeQuery(selectCustomerQuery);
            while(rs.next()) {
                User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
                hm.put(rs.getString("username"), user);
            }
        } catch(Exception e) {}
        
        return hm;			
    }
    
    public static void Insertproducts()
	{
		try{
			
			
			getConnection();
			
			
			// String truncatetableacc = "delete from Product_accessories;";
			// PreparedStatement pstt = conn.prepareStatement(truncatetableacc);
			// pstt.executeUpdate();
			
			String truncatetableprod = "delete from products;";
			PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
			psttprod.executeUpdate();
			
			String insertProductQuery = "INSERT INTO  products(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,description,productOnSale,productQuantity,productOnRebate)" +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
			
			for(Map.Entry<String,Doorbell> entry : SaxParserDataStore.dbls.entrySet())
			{   
				Doorbell dbl = entry.getValue();
				String name = "dbl";
				
				
				
				PreparedStatement pst = conn.prepareStatement(insertProductQuery);
				pst.setString(1,name);
				pst.setString(2,dbl.getId());
				pst.setString(3,dbl.getName());
				pst.setDouble(4,dbl.getPrice());
				pst.setString(5,dbl.getImage());
				pst.setString(6,dbl.getRetailer());
				pst.setString(7,dbl.getCondition());
				pst.setDouble(8,dbl.getDiscount());
                pst.setString(9,dbl.getDescription());
				pst.setInt(10,0);
				pst.setInt(11,0);
				pst.setInt(12,0);
				
				pst.executeUpdate();
				// try{
				// 	HashMap<String,String> acc = con.getAccessories();
				// 	String insertAccessoryQurey = "INSERT INTO  Product_accessories(productName,accessoriesName)" +
				// 	"VALUES (?,?);";
				// 	for(Map.Entry<String,String> accentry : acc.entrySet())
				// 	{
				// 		PreparedStatement pstacc = conn.prepareStatement(insertAccessoryQurey);
				// 		pstacc.setString(1,con.getId());
				// 		pstacc.setString(2,accentry.getValue());
				// 		pstacc.executeUpdate();
				// 	}
				// }catch(Exception et){
				// 	et.printStackTrace();
				// }
			}
			for(Map.Entry<String,Doorlock> entry : SaxParserDataStore.dlks.entrySet())
			{   
				String name = "dlk";
				Doorlock dlk = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQuery);
				pst.setString(1,name);
				pst.setString(2,dlk.getId());
				pst.setString(3,dlk.getName());
				pst.setDouble(4,dlk.getPrice());
				pst.setString(5,dlk.getImage());
				pst.setString(6,dlk.getRetailer());
				pst.setString(7,dlk.getCondition());
				pst.setDouble(8,dlk.getDiscount());
                pst.setString(9,dlk.getDescription());
				pst.setInt(10,0);
				pst.setInt(11,0);
				pst.setInt(12,0);
				
				pst.executeUpdate();
				
				
			}
			for(Map.Entry<String,Speaker> entry : SaxParserDataStore.spkrs.entrySet())
			{   
				String name = "spkr";
				Speaker spkr = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQuery);
				pst.setString(1,name);
				pst.setString(2,spkr.getId());
				pst.setString(3,spkr.getName());
				pst.setDouble(4,spkr.getPrice());
				pst.setString(5,spkr.getImage());
				pst.setString(6,spkr.getRetailer());
				pst.setString(7,spkr.getCondition());
				pst.setDouble(8,spkr.getDiscount());
                pst.setString(9,spkr.getDescription());
				pst.setInt(10,0);
				pst.setInt(11,0);
				pst.setInt(12,0);
				
				pst.executeUpdate();
				
				
			}

            for(Map.Entry<String,Lighting> entry : SaxParserDataStore.lghts.entrySet())
			{   
				String name = "lght";
				Lighting lght = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQuery);
				pst.setString(1,name);
				pst.setString(2,lght.getId());
				pst.setString(3,lght.getName());
				pst.setDouble(4,lght.getPrice());
				pst.setString(5,lght.getImage());
				pst.setString(6,lght.getRetailer());
				pst.setString(7,lght.getCondition());
				pst.setDouble(8,lght.getDiscount());
                pst.setString(9,lght.getDescription());
				pst.setInt(10,0);
				pst.setInt(11,0);
				pst.setInt(12,0);
				
				pst.executeUpdate();
				
				
			}

            for(Map.Entry<String,Thermostat> entry : SaxParserDataStore.trmsts.entrySet())
			{   
				String name = "trmst";
				Thermostat trmst = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQuery);
				pst.setString(1,name);
				pst.setString(2,trmst.getId());
				pst.setString(3,trmst.getName());
				pst.setDouble(4,trmst.getPrice());
				pst.setString(5,trmst.getImage());
				pst.setString(6,trmst.getRetailer());
				pst.setString(7,trmst.getCondition());
				pst.setDouble(8,trmst.getDiscount());
                pst.setString(9,trmst.getDescription());
				pst.setInt(10,0);
				pst.setInt(11,0);
				pst.setInt(12,0);
				
				pst.executeUpdate();
				
				
			}

			String update1 = "update products set productQuantity=5 where Id like '%1'";
			String update2 = "update products set productQuantity=4 where Id like '%2'";
			String update3 = "update products set productQuantity=3 where Id like '%3'";
			String update4 = "update products set productQuantity=2 where Id like '%4'";
			String update5 = "update products set productQuantity=1 where Id like '%5'";
			String update6 = "update products set productOnSale=1 where Id like '%1' or Id like '%3' or Id like '%5'";
			String update7 = "update products set productOnRebate=1 where Id like '%2' or Id like '%4'";

			PreparedStatement upd1 = conn.prepareStatement(update1);
			upd1.executeUpdate();

			PreparedStatement upd2 = conn.prepareStatement(update2);
			upd2.executeUpdate();

			PreparedStatement upd3 = conn.prepareStatement(update3);
			upd3.executeUpdate();

			PreparedStatement upd4 = conn.prepareStatement(update4);
			upd4.executeUpdate();

			PreparedStatement upd5 = conn.prepareStatement(update5);
			upd5.executeUpdate();

			PreparedStatement upd6 = conn.prepareStatement(update6);
			upd6.executeUpdate();

			PreparedStatement upd7 = conn.prepareStatement(update7);
			upd7.executeUpdate();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

public static HashMap<String,Doorbell> getDbls()
{	
		HashMap<String,Doorbell> hm=new HashMap<String,Doorbell>();
		try 
		{
			getConnection();
			
			String selectdbl="select * from  products where ProductType=?";
			PreparedStatement pst = conn.prepareStatement(selectdbl);
			pst.setString(1,"dbl");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
				{	Doorbell dbl = new Doorbell(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("description"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
			hm.put(rs.getString("Id"), dbl);
			dbl.setId(rs.getString("Id"));
			
			// try
			// {
			// 	String selectaccessory = "Select * from Product_accessories where productName=?";
			// 	PreparedStatement pstacc = conn.prepareStatement(selectaccessory);
			// 	pstacc.setString(1,rs.getString("Id"));
			// 	ResultSet rsacc = pstacc.executeQuery();
				
			// 	HashMap<String,String> acchashmap = new HashMap<String,String>();
			// 	while(rsacc.next())
			// 	{    
			// 		if (rsacc.getString("accessoriesName") != null){
						
			// 			acchashmap.put(rsacc.getString("accessoriesName"),rsacc.getString("accessoriesName"));
			// 			con.setAccessories(acchashmap);
			// 		}
					
			// 	}					
			// }catch(Exception e)
			// {
			// 	e.printStackTrace();
			// }
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Doorlock> getDlks()
{	
	HashMap<String,Doorlock> hm=new HashMap<String,Doorlock>();
	try 
	{
		getConnection();
		
		String selectdlk="select * from  products where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectdlk);
		pst.setString(1,"dlk");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Doorlock dlk = new Doorlock(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("description"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
		hm.put(rs.getString("Id"), dlk);
		dlk.setId(rs.getString("Id"));

	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Speaker> getSpkrs()
{	
	HashMap<String,Speaker> hm=new HashMap<String,Speaker>();
	try 
	{
		getConnection();
		
		String selectspkr="select * from  products where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectspkr);
		pst.setString(1,"spkr");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Speaker spkr = new Speaker(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("description"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
		hm.put(rs.getString("Id"), spkr);
		spkr.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Lighting> getLghts()
{	
	HashMap<String,Lighting> hm=new HashMap<String,Lighting>();
	try 
	{
		getConnection();
		
		String selectlght="select * from  products where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectlght);
		pst.setString(1,"lght");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Lighting lght = new Lighting(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("description"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
		hm.put(rs.getString("Id"), lght);
		lght.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Thermostat> getTrmsts()
{	
	HashMap<String,Thermostat> hm=new HashMap<String,Thermostat>();
	try 
	{
		getConnection();
		
		String selecttrmst="select * from  products where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selecttrmst);
		pst.setString(1,"trmst");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Thermostat trmst = new Thermostat(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("description"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
		hm.put(rs.getString("Id"), trmst);
		trmst.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static String addproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String description,String prod)
{
	String msg = "Product is added successfully";
	try{
		
		getConnection();
		String addProductQurey = "INSERT INTO  products(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,description)" +
		"VALUES (?,?,?,?,?,?,?,?,?);";
		
		String name = producttype;
		
		PreparedStatement pst = conn.prepareStatement(addProductQurey);
		pst.setString(1,name);
		pst.setString(2,productId);
		pst.setString(3,productName);
		pst.setDouble(4,productPrice);
		pst.setString(5,productImage);
		pst.setString(6,productManufacturer);
		pst.setString(7,productCondition);
		pst.setDouble(8,productDiscount);
        pst.setString(9,description);
		
		pst.executeUpdate();
		// try{
		// 	if (!prod.isEmpty())
		// 	{
		// 		String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
		// 		"VALUES (?,?);";
		// 		PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
		// 		pst1.setString(1,prod);
		// 		pst1.setString(2,productId);
		// 		pst1.executeUpdate();
				
		// 	}
		// }catch(Exception e)
		// {
		// 	msg = "Erro while adding the product";
		// 	e.printStackTrace();
			
		// }
		
		
		
	}
	catch(Exception e)
	{
		msg = "Erro while adding the product";
		e.printStackTrace();
		
	}
	return msg;
}
public static String updateproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String description)
{ 
	String msg = "Product is updated successfully";
	try{
		
		getConnection();
		String updateProductQurey = "UPDATE products SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=?,description=? where Id =?;" ;
		
		
		
		PreparedStatement pst = conn.prepareStatement(updateProductQurey);
		
		pst.setString(1,productName);
		pst.setDouble(2,productPrice);
		pst.setString(3,productImage);
		pst.setString(4,productManufacturer);
		pst.setString(5,productCondition);
		pst.setDouble(6,productDiscount);
        pst.setString(7,description);
		pst.setString(8,productId);
		pst.executeUpdate();
		
		
		
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
		
	}
	return msg;	
}

public static String deleteproducts(String productId)
{   String msg = "Product is deleted successfully";
try
{
	
	getConnection();
	String deleteproductsQuery ="Delete from products where Id=?";
	PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
	pst.setString(1,productId);
	
	pst.executeUpdate();
}
catch(Exception e)
{
	msg = "Proudct cannot be deleted";
}
return msg;
}

public static HashMap<String,Inventory> getAllProducts() {
	HashMap<String,Inventory> hm = new HashMap<String,Inventory>();
	try {
		getConnection();
		Statement stmt = conn.createStatement();
		String getProdQuery = "select * from products";
		ResultSet rs = stmt.executeQuery(getProdQuery);
		
		while(rs.next()) {	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productOnSale"),rs.getInt("productQuantity"), rs.getInt("productOnRebate"));
			hm.put(rs.getString("Id"), inventoryItems);	
		}
	} catch(Exception e) {}
	
	return hm;
}

public static HashMap<String,Inventory> getOnSale() {
	HashMap<String,Inventory> hm = new HashMap<String,Inventory>();
	try {
		getConnection();
		Statement stmt = conn.createStatement();
		String saleQuery="select * from products where productOnSale = 1";
		ResultSet rs = stmt.executeQuery(saleQuery);

		while(rs.next()) {	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productOnSale"),rs.getInt("productQuantity"), rs.getInt("productOnRebate"));
			hm.put(rs.getString("Id"), inventoryItems);
		}
	} catch(Exception e) {}
	
	return hm;
}

public static HashMap<String,Inventory> getOnRebate() {
	HashMap<String,Inventory> hm = new HashMap<String,Inventory>();
	try {
		getConnection();
		Statement stmt = conn.createStatement();
		String rebateQuery="select * from products where productOnRebate = 1";
		ResultSet rs = stmt.executeQuery(rebateQuery);
	
		while(rs.next()) {	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getInt("productOnSale"),rs.getInt("productQuantity"), rs.getInt("productOnRebate"));
			hm.put(rs.getString("Id"), inventoryItems);
		}
	} catch(Exception e) {}

	return hm;
}

public static HashMap<String,Inventory> getProductSales() {
	HashMap<String,Inventory> hm = new HashMap<String,Inventory>();
	try {
		getConnection();
		Statement stmt = conn.createStatement();
		String ProdSalesQuery = "select orderName,orderPrice,count(orderName) as itemsSold,(orderPrice * count(orderName)) as totalSales from orders group by orderName, orderPrice, orderPrice;";
		ResultSet rs = stmt.executeQuery(ProdSalesQuery);
		
		while(rs.next()) {	
			Inventory inventoryItems = new Inventory(rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getInt("itemsSold"),rs.getDouble("totalSales"));
			hm.put(rs.getString("orderName"), inventoryItems);
		}
	} catch(Exception e) {}

	return hm;
}

public static HashMap<String,Inventory> getDailySales() {
	HashMap<String,Inventory> hm = new HashMap<String,Inventory>();
	try {
		getConnection();
		Statement stmt = conn.createStatement();
		String dailySales = "select purchaseDate,count(orderName) as totalSales from orders group by purchaseDate";
		ResultSet rs = stmt.executeQuery(dailySales);
		
		while(rs.next()) {	
			Inventory inventoryItems = new Inventory(rs.getString("purchaseDate"),rs.getDouble("totalSales"));
			hm.put(rs.getString("purchaseDate"), inventoryItems);
		}
	} catch(Exception e) {}

	return hm;
}

}