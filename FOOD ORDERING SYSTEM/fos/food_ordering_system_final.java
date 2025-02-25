import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
class OnlineFoodOrderingSystem
{   
	public Connection sqlconnect()throws SQLException{
		Connection con = null;
	    String url = "jdbc:mysql://localhost:3306/oopd";
	    String UserName = "root";
	    String Password = ""; 
	    try {
	        con = DriverManager.getConnection(url,UserName,Password);
	        return con;

	    } catch (SQLException e) {
	        System.out.println("Connection Failed! Check output console");
	        e.printStackTrace();
	        System.exit(0);
	}
		return con;
	}
	
	void User(Connection con) throws SQLException, InterruptedException
	{
		Scanner sc = new Scanner(System.in);
        int cust_id=0;
		int choice1=0;
        while(choice1!=3)
        {
            
            System.out.println("\nWelcome To Our Food Ordering System Platform");
            System.out.println("1. New user");
            System.out.println("2. Existing user");
            System.out.println("3. Exit");
			try {
				choice1 = sc.nextInt();	
			} catch (Exception e) {
				System.out.println("Input invalid");
				sc.nextLine();
				continue;
			}
            
            switch(choice1){
           
            
            case 1:
            {
                SignUp sgn_up = new SignUp();
                sgn_up.sign_up_attempt(con);
				break;
            }
            case 2:           
			{
          	    System.out.println("Enter your email");
          	    String customer_email = sc.next();
             	System.out.println("Enter your password");
             	String customer_password = sc.next();
                LogIn lg_in = new LogIn();
                cust_id = lg_in.log_in_attempt(con,customer_email,customer_password);
                if(cust_id==-1) 
                	User(con);
                else
                	choice2(con,cust_id);
					break;
                		
            }
            case 3: 
            {
                System.out.println("Thank you for visiting Our Platform!");
                System.exit(0);
                break;
            }
			default:System.out.println("Invalid input");
			break;
        }
	}}
	
	void choice2(Connection con, int cust_id) throws SQLException, InterruptedException
	{int choice2=0;
		Scanner sc = new Scanner(System.in);
        do {
            
            Restaurant r = new Restaurant();
            Cart c = new Cart();
            Wishlist w = new Wishlist();
            Order o = new Order();
           
            System.out.println("\nPlease select an option:");
            System.out.println("1. View restaurants and their menu");
            System.out.println("2. View cart");
            System.out.println("3. View wish list");
            System.out.println("4. Move items from wishlist to cart");
            System.out.println("5. Place order");
            System.out.println("6. View order history"); 
            System.out.println("7. Exit");
            try {
				choice2 = sc.nextInt();
			} catch (Exception e) {
				System.out.println("invalid input");
				sc.nextLine();
				continue;
			}
            
           switch(choice2){
            
            case 1:
            
            	r.view_restaurants(con,cust_id);
                 continue;
            
            case 2:
            {    
            	int rest_id=c.know_cart_rest_id(con, cust_id);
            	if(rest_id>0)
            		c.view_cart(con,cust_id,rest_id);
            	else
            		System.out.println("You have no items in cart");
					continue;
            }
            case 3:
            {   
            	
                int choice3; 
                int rest_id=w.know_wishlist_rest_id(con, cust_id);
            	if(rest_id>0)
            		w.view_wishlist(con , cust_id, rest_id); 
            	else
            		System.out.println("No item in wishlist");
					continue;
            }
            
       
            case 4: {
            	
            	int rest_id=w.know_wishlist_rest_id(con, cust_id);
            	if(rest_id>0) {
            		               		
            		PreparedStatement t4=con.prepareStatement("select * from oopd.wishlist where customer_id = ?");	
             		t4.setInt(1, cust_id);
            		ResultSet r4 = t4.executeQuery();
            	    
            		while(r4.next()) {
                	    PreparedStatement stmt=con.prepareStatement("insert into oopd.cart values(?,?,?,?)");
                		stmt.setInt(1,r4.getInt("customer_id"));
   					 	stmt.setInt(2,r4.getInt("restaurant_id")); 
   					 	stmt.setInt(3,r4.getInt("food_id"));
   					 	stmt.setInt(4,r4.getInt("food_quantity"));
   					 	stmt.executeUpdate(); 
            		}
					 	System.out.println("Record Inserted");
					   
             		PreparedStatement t5 = con.prepareStatement("DELETE FROM oopd.wishlist where customer_id=?");
           	    	t5.setInt(1, cust_id);
        	    	t5.executeUpdate();                 		
            	}
            	
            	else
            		System.out.println("You have no items in wishlist");    
					continue;   	
            }
            
            case 5:
            {    
            	Order oo= new Order();
  	    		int rest_id=c.know_cart_rest_id(con, cust_id);
            	if(rest_id>0)
            		oo.place_order(con, cust_id, rest_id);
            	else
            		System.out.println("You have no items in your cart to place order.");
					continue;
                
            }
            case 6: {
            	Order oo= new Order();
            	oo.view_orders(con, cust_id);
				continue;
            }
            
            case 7:
            { 
                System.out.println("Thank You For Visiting Our PlatFlorm");
                break;
            
			}
			default: System.out.println("Invalid input");
			continue;
		 }
		
        
        User(con);
	} while(choice2!=7);
	}
	
    final void connect_all(Connection con) throws SQLException, InterruptedException
    {
    	User(con);
    }
}

class SignUp extends OnlineFoodOrderingSystem 
{
    private static final Connection con = null;

	void sign_up_attempt(Connection con) throws SQLException     
    {  
  	    Scanner sc=new Scanner(System.in);
  	    System.out.println("Enter your name");
  	    String customer_name = sc.next();
  	    System.out.println("Enter your email");
  	    String customer_email = sc.next();
     	System.out.println("Enter your password");
     	String customer_password = sc.next();
     	System.out.println("Enter your address");
     	String customer_address = sc.next();
     	System.out.println("Enter your city");
     	String customer_city = sc.next();
     	System.out.println("Enter your area");
     	String customer_area = sc.next();
 
 	   
     	PreparedStatement cs=con.prepareCall("call insertcustomer(?,?,?,?,?,?)"); 
	    cs.setString(1,customer_name); 
	    cs.setString(2,customer_email); 
	    cs.setString(3,customer_password);
	    cs.setString(4,customer_address); 
	    cs.setString(5,customer_city);  
	    cs.setString(6,customer_area);  
  	
	   int i=cs.executeUpdate();  
	   if(i>0){
	   System.out.println("Account Created Successfully");
    }else{
		System.out.println("Account not created");
	}
    }
}
class LogIn extends OnlineFoodOrderingSystem 
{
    int log_in_attempt(Connection con,String email, String password) throws SQLException
    {
    	  int cust_id;
		  PreparedStatement st = con.prepareStatement("select * from oopd.customer where customer_email=? and customer_password= ? ");
   	      st.setString(1, email);
   	      st.setString(2, password);
 	      ResultSet r1=st.executeQuery();
          String nme = null;
          String ps = null;
        
 	      if(r1.next()) {
 	          nme =  r1.getString("customer_email");
 	          ps=r1.getString("customer_password");
 	          if(nme.equalsIgnoreCase(email) && ps.equals(password) )
 	          {
 	        	  System.out.println( "\nUser found");
 	        	  cust_id=r1.getInt("customer_id");
 	        	  return cust_id;  
 	          }
 	      }
 	      System.out.println( "\nUser not found. Select option below");      
 	      return -1;
    }
}

class Order extends OnlineFoodOrderingSystem
{
    private boolean order_successful = false;
    boolean get_order_successful() {return order_successful;}
    void set_order_successful(boolean flag) {order_successful = flag;}
    int order_amount=0;
  	double delivery_amount=0;
  	double final_amount=0;
  	
  ArrayList<Order> al1=new ArrayList<>();
  int id;
  int amount;
  int tamount;

  public Order(){

  }

    public Order(int id, int amount, int tamount) {
	this.id = id;
	this.amount = amount;
	this.tamount = tamount;
}

	public int getId() {
		return id;
	}
	public int getAmount() {
		return amount;
	}
	public int getTamount() {
		return tamount;
	}
	final void view_orders(Connection con, int cust_id) throws SQLException
    {   
    	PreparedStatement ot;
    	ResultSet ro;
    	ot = con.prepareStatement("select * from oopd.order where customer_id = ?");
	    ot.setInt(1, cust_id);
	    ro = ot.executeQuery();
	    System.out.println("All your orders are:-");
	    System.out.println("order id,order amount,total amount");
	    while(ro.next()) {

	    	 ro.getInt("order_id");
	    	 ro.getInt("order_amount");
	    	 ro.getInt("total_amount");
			 al1.add(new Order(ro.getInt("order_id"), ro.getInt("order_amount"),ro.getInt("total_amount")));
	    }

		for(Order o : al1){
        System.out.println("\nOrder id = "+o.getId());
		System.out.println("Order Amount = "+o.getAmount());
		System.out.println("Order Total Amount = "+o.getTamount());
		}
    }      
    final void place_order(Connection con, int cust_id, int rest_id) throws SQLException, InterruptedException
    {    
    	calc_final_amount(con, cust_id, rest_id);	
        System.out.println("Your order total is :-  "+ final_amount);
        PreparedStatement stmt=con.prepareStatement("insert into oopd.order (customer_id,order_amount,total_amount) values(?,?,?)");
        stmt.setInt(1,cust_id); 
		stmt.setDouble(2,order_amount);
		stmt.setDouble(3,final_amount);
		stmt.executeUpdate();
		PaymentMode ob=new PaymentMode();
		ob.pay_mode();
		rating rate=new rating();
		rate.rating_res_app(con,rest_id);
		
    }     
   
	void calc_final_amount(Connection con, int cust_id, int rest_id) throws SQLException, InterruptedException{
	    	calc_order_amount(con, cust_id, rest_id);
	    	final_amount = order_amount;

	 }
	
    void calc_order_amount(Connection con, int cust_id, int rest_id) throws SQLException, InterruptedException{
    	
    	PreparedStatement st4 = con.prepareStatement("select * from oopd.cart where customer_id=? and restaurant_id=? ");
    	st4.setInt(1, cust_id);
     	st4.setInt(2, rest_id);
   	    ResultSet r4=st4.executeQuery();
        int curr_food_id=0, curr_rest_id = 0,curr_food_quantity=0;
      	int f_price=0;
      	System.out.println("\n__ WELCOME TO ORDER PAGE __");
      	System.out.println("\nYou have following items in your cart .");
      	System.out.println("Name"+"		"+"Quantity"	);
      	while(r4.next()) {
      		
      		curr_rest_id = r4.getInt("restaurant_id");
      		curr_food_id = r4.getInt("food_id");
      		curr_food_quantity = r4.getInt("food_quantity");
      		PreparedStatement st5 = con.prepareStatement("select * from oopd.menu where food_id=?");
	      	st5.setInt(1, curr_food_id);
	      	ResultSet r5=st5.executeQuery();
	      	if(r5.next()) {
	      		System.out.println(r5.getString("food_name")+"		"+curr_food_quantity);
	      	}
      	}
      	
      	String change_food_name="";
      	int new_quantity=0;
      	int change_food_id=0;
      	System.out.println("Press 'Yes' to get Bill.");
      	Scanner sc = new Scanner(System.in);
 
        do{
        	
        	change_food_name=sc.next();	
        	if(change_food_name.equalsIgnoreCase("yes")) {
        		 ResultSet rrt=st4.executeQuery();
        	     System.out.println("Name"+"		"+"Quantity"	);
        	     while(rrt.next()) {
        	      		
        	      		curr_food_id = rrt.getInt("food_id");
        	      		new_quantity = rrt.getInt("food_quantity");
        	      		PreparedStatement st5 = con.prepareStatement("select * from oopd.menu where food_id=?");
        		      	st5.setInt(1, curr_food_id);
        		      	ResultSet r5=st5.executeQuery();
        		      
        		      	if(r5.next()) {
        		      		System.out.println(r5.getString("food_name")+"		"+new_quantity);
        		      	}
        	    }
        		break;
        	}
        	else {
        		System.out.println("Invalid input.Try again");

        	}
        	
        }
        while(true);

   	    ResultSet rnew=st4.executeQuery();
   	    		
   	    while(rnew.next()) {
   	    	curr_food_id = rnew.getInt("food_id");
   	    	curr_food_quantity = rnew.getInt("food_quantity");
   	    	PreparedStatement st5 = con.prepareStatement("select * from menu where food_id=?");
	      	st5.setInt(1, curr_food_id);
	      	ResultSet r5=st5.executeQuery();
	      	if(r5.next()) {
	      		f_price = r5.getInt("food_price");
	      	}
   	    	order_amount = order_amount + f_price*curr_food_quantity;
   	    	PreparedStatement ds = con.prepareStatement("DELETE FROM cart where customer_id=? and food_id=?");
   	    	ds.setInt(1, cust_id);
	  	    ds.setInt(2, curr_food_id);
	    	ds.executeUpdate();
   	    }
   	    
   	    	System.out.println("\n\nYour Food total is - "+ order_amount);
    }
 
    
    
}


class Restaurant extends Order
{
    void view_restaurants(Connection con,int cust_id) throws SQLException, InterruptedException
    {Scanner sc=new Scanner(System.in);
    	if(cust_id==0)
    		return;
    	
    	PreparedStatement st = con.prepareStatement("select * from restaurant ");
  	    ResultSet r1=st.executeQuery();
        int rest_id=0;
  	    while(r1.next()) {
  	    		String res_c;
  	    		res_c = r1.getString("restaurant_name");
  	    		System.out.println("\nRestaurant Name:-"+res_c);
  	    		System.out.println("Menu of "+res_c);
  	    		rest_id = r1.getInt("restaurant_id");
  	    		Menu res_menu=new Menu();
  	    		res_menu.view_menu(con,rest_id);
  	    	}
  	    
  	 
		  int order_choice=0;
		  try{
		  System.out.println("\n1.Cart\n2.Wishlist");
		  order_choice=sc.nextInt();
		  boolean flag1=false;
		  if(order_choice==1 || order_choice==2){
			flag1=true;
		  }
		  while(!flag1){
			System.out.print("Enter valid choice : ");
			order_choice=sc.nextInt();
			if(order_choice==1 || order_choice==2){
				flag1=true;
			  }
		  }
  	    System.out.println("Enter the Restauarnt name from which you want to order");}catch(Exception e){
			System.out.println();
			System.out.println(e);
			return;
		}
  	    
  	    String ord_res=sc.next();
  	    PreparedStatement stm = con.prepareStatement("select * from oopd.restaurant where restaurant_name=?");
    	stm.setString(1, ord_res);
  	    ResultSet r2=stm.executeQuery();
    	if(r2.next())
    	{
    		String t=r2.getString("restaurant_name");
    		if(t.equalsIgnoreCase(ord_res))
  	    	{
    			rest_id=r2.getInt("restaurant_id");
  	    	}
    	}
    	System.out.println("Enter the Food items and their quantity which you want to order from "+ord_res+" Restaurant and enter 'null' to stop");
    	String food_name;
		int quant ;
		int fod_id;
		int res;
		String t;
		try{
  	    do 
  	    {
  	    	food_name=sc.next();
  	    	if(food_name.equalsIgnoreCase("null") )
  	    		break;
  	    	 quant = sc.nextInt();
  	    	 fod_id=0;
  	  	    PreparedStatement s = con.prepareStatement("select * from menu where restaurant_id=? and food_name=?");
  	  	    s.setInt(1, rest_id);
  	  	    s.setString(2, food_name);
  	  	    ResultSet r3=s.executeQuery();
  	    	if(r3.next())
  	    	{
  	    		 res = r3.getInt("restaurant_id"); 
  	    		 t=r3.getString("food_name");
  	    		if(t.equalsIgnoreCase(food_name)&&res==rest_id)
  	  	    	{
  	    			fod_id=r3.getInt("food_id");
  	  	    	}
  	    	} 
  	
			if (order_choice==1)
			{
				PreparedStatement ss = con.prepareStatement("select * from cart where customer_id=? and food_id=?");
				ss.setInt(1, cust_id);
				ss.setInt(2, fod_id);
				ResultSet rr=ss.executeQuery();
				if(rr.next()) {
					quant = quant+rr.getInt("food_quantity");
					PreparedStatement ds = con.prepareStatement("DELETE FROM cart where customer_id=? and food_id=?");
					ds.setInt(1, cust_id);
					ds.setInt(2, fod_id);
					ds.executeUpdate();
  	            }
				 PreparedStatement stmt=con.prepareStatement("insert into cart values(?,?,?,?)");
				 stmt.setInt(1,cust_id);
				 stmt.setInt(2,rest_id); 
				 stmt.setInt(3,fod_id); 
				 stmt.setInt(4,quant);
				 stmt.executeUpdate();  
				 System.out.println("Record inserted");
			 }
				
			 if (order_choice==2)
			{
				PreparedStatement ss = con.prepareStatement("select * from wishlist where customer_id=? and food_id=?");
				ss.setInt(1, cust_id);
				ss.setInt(2, fod_id);
				ResultSet rr=ss.executeQuery();
				if(rr.next()) {
					quant = quant+rr.getInt("food_quantity");
					PreparedStatement ds = con.prepareStatement("DELETE FROM wishlist where customer_id=? and food_id=?");
					ds.setInt(1, cust_id);
					ds.setInt(2, fod_id);
					ds.executeUpdate();
  	            }
				 PreparedStatement stmt=con.prepareStatement("insert into wishlist values(?,?,?,?)");
				 stmt.setInt(1,cust_id);
				 stmt.setInt(2,rest_id); 
				 stmt.setInt(3,fod_id); 
				 stmt.setInt(4,quant);
				 stmt.executeUpdate();  
				 System.out.println("Record inserted");
			 }
			 
  	    }while(true);}catch(Exception e){
			System.out.println();
			System.out.println(e);
			sc.next();
			return;
		}
  	    
		
    }
}

class Menu extends Restaurant
{
	void view_menu(Connection con, int rest_id) throws SQLException
	{
		PreparedStatement st = con.prepareStatement("select * from menu where restaurant_id=?");
    	st.setInt(1, rest_id);
  	    ResultSet r1=st.executeQuery();
        int res = 0;
        String fod_nm = null;
        int fod_prc = 0;
        System.out.println("Food name,,Food price");
  	    while(r1.next()) {
  	    	res =  r1.getInt("restaurant_id");
  	    	if(res==rest_id)
  	    	{
  	    		fod_nm = r1.getString("food_name");
  	    		fod_prc = r1.getInt("food_price");
  	    		System.out.println(fod_nm+","+fod_prc);
  	    	}
  	    }
	}
}

class Cart extends Order
{  
	int know_cart_rest_id(Connection con, int cust_id) throws SQLException {
		 PreparedStatement stt = con.prepareStatement("select * from cart where customer_id=?");
	 	 stt.setInt(1, cust_id);
		 ResultSet rtt=stt.executeQuery();
		 if(rtt.next())
			 return rtt.getInt("restaurant_id");	 
		 else
			 return -1;	 
	}
	
    void view_cart(Connection con, int cust_id, int rest_id) throws SQLException, InterruptedException    
    {
    	
    	System.out.println("You have added following things in your cart");
 	    PreparedStatement st = con.prepareStatement("select * from restaurant where restaurant_id=?");
 	    st.setInt(1, rest_id);
 	    ResultSet r1=st.executeQuery();
 	    int res = 0;
 	    String res_nm = null;
 	   
	    if(r1.next()) {

	    	res =  r1.getInt("restaurant_id");

	    	if(res==rest_id)
	    	{
	    		res_nm = r1.getString("restaurant_name");
	    		System.out.println("From "+res_nm +" restaurant");
	    		System.out.println("Food item,Quantity");
	    		PreparedStatement st3 = con.prepareStatement("select * from cart where restaurant_id=?");
	      	    st3.setInt(1, rest_id);
	    	    ResultSet r3=st3.executeQuery();
	    	    int fod_id=0;
	    	    
	    	    while(r3.next()) {

	    	    	int res3 =  r3.getInt("restaurant_id");
	    	    	if(res3==rest_id)
	    	    		fod_id=r3.getInt("food_id");
	    	    
	    	    	PreparedStatement st2 = con.prepareStatement("select * from menu where restaurant_id=? and food_id=?");
	    	    	st2.setInt(1, rest_id);
	    	    	st2.setInt(2, fod_id);
	    	    	ResultSet r2=st2.executeQuery();
	    	    	
	    	    	int res2 = 0;
	    	    	int tmp;
	    	    	String food_item = null;
	    	    	
	    	    	while(r2.next()) {
	    	    		res2 =  r2.getInt("restaurant_id");
	    	    		tmp= r2.getInt("food_id");
	    	    		if(res2==rest_id && tmp==fod_id)
	    	    			food_item=r2.getString("food_name");
	    	    	}  	    	
	    	    	System.out.println(food_item+","+r3.getInt("food_quantity"));

	    	      }
	    	}
	    } 
	 
  	    	Order o= new Order();
 	    	o.place_order(con, cust_id, rest_id);
  	 }   
}

class Wishlist extends Order
{    
	int know_wishlist_rest_id(Connection con, int cust_id) throws SQLException {
		 PreparedStatement stt = con.prepareStatement("select * from wishlist where customer_id=?");
	 	 stt.setInt(1, cust_id);
		 ResultSet rtt=stt.executeQuery();
		 if(rtt.next())
			 return rtt.getInt("restaurant_id");	 
		 else
			 return -1;	 
	}
	
    void view_wishlist(Connection con, int cust_id, int rest_id) throws SQLException    
    {
    
          System.out.println("You have following items in your wish-list");
          PreparedStatement st = con.prepareStatement("select * from restaurant where restaurant_id=?");
          st.setInt(1, rest_id);
          ResultSet r1=st.executeQuery();
          int res = 0;
          String res_nm = null;
         
          if(r1.next()) {
            res =  r1.getInt("restaurant_id");
            if(res==rest_id)
            {
            	res_nm = r1.getString("restaurant_name");
                System.out.println("Food item,Quantity");
                PreparedStatement st3 = con.prepareStatement("select * from wishlist where restaurant_id=?");
                st3.setInt(1, rest_id);
                ResultSet r3=st3.executeQuery();
                int fod_id=0;
                while(r3.next()) {
                    int res3 =  r3.getInt("restaurant_id");
                    if(res3==rest_id)
                    {
                        fod_id=r3.getInt("food_id");                    
                    }
                PreparedStatement st2 = con.prepareStatement("select * from oopd.menu where restaurant_id=? and food_id=?");
                st2.setInt(1, rest_id);
                st2.setInt(2, fod_id);
                ResultSet r2=st2.executeQuery();
                int res2 = 0;
                int tmp;
                String food_item = null;

                while(r2.next()) {
                    res2 =  r2.getInt("restaurant_id");
                    tmp= r2.getInt("food_id");
                    if(res2==rest_id && tmp==fod_id)
                    	food_item=r2.getString("food_name");
                }
                System.out.println(food_item+","+r3.getInt("food_quantity"));
              }
            }
        }        
     }
}

class PaymentMode extends OnlineFoodOrderingSystem
{
	void pay_mode() throws InterruptedException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Following are the available payment options:-");
		System.out.println("1. Credit Card");
		System.out.println("2. Debit Card");
		System.out.println("3. Net Banking");
		System.out.println("4. UPI");
		System.out.println("5. Cash on Delivery");
		System.out.println("Enter the mode of the payment which you want to option");	
		int mode=sc.nextInt();
		if(!(mode==1||mode==2||mode==3||mode==4||mode==5))
		{
			System.out.println("Please enter a valid payment mode");
			pay_mode();
		}
		System.out.println("---Work in Progress----");
		TimeUnit.SECONDS.sleep(1);
		if(mode==1||mode==2)
		{
			String name,date;
			long card_no;
			int cvv_no;
			System.out.println("\n__Enter the card details__");
			System.out.println("\nEnter the name of cardholder");
			name=sc.next();
			System.out.println("Enter the card number");
			card_no=sc.nextLong();
			System.out.println("Enter the expiration date of the card in dd/mm/yyyy format");
			date=sc.next();
			System.out.println("Enter the cvv number");
			cvv_no=sc.nextInt();
			System.out.println("Verifying your card details");
			Thread.sleep(1000);
			System.out.println("Congratulations payment is successful, thanks for ordering");
		}
		else if(mode==3)
		{
			int opt,pin;
			String email,password;
			System.out.println("\nChoose your Bank from which you want to pay");
			System.out.println("1. SBI");
			System.out.println("2. HDFC");
			System.out.println("3. Nutan Nagrik Bank");
			System.out.println("4. Axis Bank");
			System.out.println("5. ICICI Bank.");
			System.out.println("6. Kotak Bank");
			opt=sc.nextInt();
			System.out.println("Connecting...");
			Thread.sleep(1000);
			System.out.println("Enter your email");
			email=sc.next();
			System.out.println("Enter our password");
			password=sc.next();
			System.out.println("Enter your pin");
			pin=sc.nextInt();
			System.out.println("Verifying your credentials");
			Thread.sleep(1000);
			System.out.println("Congratulations payment is successful, thanks for ordering");
		}
		else if(mode==4)
		{

			int opt,pin;
			System.out.println("\nChoose the UPI app from which you want to pay");
			System.out.println("1. Patym");
			System.out.println("2. PhonePe");
			System.out.println("3. Google Pay");
			System.out.println("4. Amazon Pay");
			opt=sc.nextInt();
			System.out.println("Enter UPI Id ");
			String UPI=sc.nextLine();
			System.out.println("Enter the pin");
			pin=sc.nextInt();
			System.out.println("Connecting...");
			Thread.sleep(1000);
			System.out.println("Congratulations payment is successful, thanks for ordering");
		}
		else 
		{
			System.out.println("\nOrder is succesfuly placed, thanks for ordering");
			System.out.println(" the agent will collect the cash.");
		}
	}
}
//when order is received then we have to call this function
class rating extends OnlineFoodOrderingSystem
{
	void rating_res_app(Connection con,int res_id) throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		int f=0,res_rate=0,app_rate=0;
		while(true) {
		System.out.println("Enter the rating for the restaurant in the range 1 to 5");
		res_rate=sc.nextInt();
		System.out.println("Enter the rating for this App in the range 1 to 5");
		app_rate=sc.nextInt();
		if(res_rate>=0&&res_rate<=5&&app_rate>=0&&app_rate<=5)
			f=1;
		if(f==1)
			break;
		else
			System.out.println("Please enter a valid rating in the range 1 to 5");
		}
		System.out.println("Thanks for rating our App and the restaurant");
		PreparedStatement stmt=con.prepareStatement("insert into rating values(?,?,?)");
        stmt.setInt(1,res_id);
        stmt.setInt(2,res_rate);
        stmt.setInt(3, app_rate);
		stmt.executeUpdate();
	}
}

public class food_ordering_system_final
{
    public static void main(String args[]) throws SQLException, InterruptedException 
    {    
	    Scanner sc=new Scanner(System.in);
        OnlineFoodOrderingSystem ofos = new OnlineFoodOrderingSystem();
        Connection con = ofos.sqlconnect();
        ofos.connect_all(con);
    }
}
 class Queue {
    int rear;
    int front;
    int size;
    Order a[];
    Queue(int s){
        a=new Order[s];
        rear=front=-1;
        size=s;
    }

    void enqueue(Order o){
        if(rear==size-1){
            System.out.println("Queue is Full Wait a While");
        }
        else{
            if(front==-1){
                front=0;
            }
            rear=rear+1;
            a[rear]=o;
            System.out.println("Order Proceeded for Making");
        }
    }

    Order dequeue(){
        if(front==-1){
            System.out.println("Queue is Empty");
            return null;
        }
        else{
            Order o;
            o=a[front];
            if(front==rear){
                front=rear=-1;
            }
            else{
                front=front+1;
            }
            System.out.println("Order with dispatched for Delivery");
            return o;
        }
    }

    void print(){
        //when the queue is Empty it handles the exception
        try{
            if(front==-1){
                System.out.println("Queue is Empty");
            }
            else{
                for(int i=front;i<=rear;i++){
                    System.out.println(a[i].toString());
                }
            }
        }
        catch(Exception e){
            System.out.println("Queue is Empty");
        }
    }
}
