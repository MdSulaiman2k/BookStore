package bookStore;
import java.io.BufferedReader;
import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.compiler.ServletWriter;
import org.json.JSONException;
import org.json.JSONObject;



public class UserController extends HttpServlet{
	
	@Override
	public void  doPost(HttpServletRequest request , HttpServletResponse response) throws IOException {
		
		String requestURI = request.getRequestURI() ;
		String  user_id = null ;
		
		// userRepository is a class where all  the user details was stores  
		
		UserRepository user = new UserRepository();
		 ServletOutputStream out = response.getOutputStream() ;
		 
		// this Action check whether you want to Register or login
		 
		 String Action = requestURI.substring("/bookStore/User/".length());
		 
		 
		 StringBuffer jb = new StringBuffer();
		 String line = null ;
	    	try {
	    		BufferedReader reader = request.getReader() ;
	    		while((line=reader.readLine()) != null) 
	    			jb.append(line);
	    		}catch(Exception js) {
	    			out.println(js.getMessage());
	    	}
		 
		
		 
		if(Action.equals("Register")) {
			 // user.Register is use to fetch the data of user and return the user_id 
			try {
				
				JSONObject jsonObject = new JSONObject(jb.toString());
				try {
				try {	
				user_id = user.Register(jsonObject.getString("name"), jsonObject.getString("mail_Id"), jsonObject.getString("password"));
				}catch (JSONException js) {
					out.println(js.getMessage());
				}
				if(user_id != null) {
					out.println(user_id) ; }
				else {
					out.println(user_id)  ;
					out.println(" Type your name , mail_Id and password properly to register your account  ") ;
				}
				}catch (Exception sq) {
					out.println(sq.getMessage());				}
			}catch (Exception e){
				out.println(e.getMessage() );	
			}
		 }
		
		 else if(Action.equals("Login")) {
			 
			
				try {
					JSONObject jsonObject = new JSONObject(jb.toString());
					try {
					user_id = user.Login(jsonObject.getString("mail_Id"),jsonObject.getString("password"));
					
					if(user_id != null) {
						out.println(user_id) ; }
					else {
						out.println(user_id)  ;
						out.println(" Type valid  mail_Id and password properly to enter into your account \nif you dont have a account create new account  ") ;
					}
					}catch (SQLException e) {
						
						 out.println(e.getMessage()) ;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					out.println(e.getMessage());
				} 
				
			} 
		 else {
			 out.println("your URL is wrong ") ;
		 }
	}
	}
