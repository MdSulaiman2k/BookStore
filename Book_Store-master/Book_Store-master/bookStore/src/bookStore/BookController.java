package bookStore;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.*;


@WebServlet(name="AddingBooks" ,  urlPatterns = {"/AddBook"})
public class BookController extends HttpServlet {
	
 @Override
 public void doPost( HttpServletRequest request , HttpServletResponse response  ) throws IOException , ServletException {
	 StringBuffer json = new StringBuffer();
	 ServletOutputStream out = response.getOutputStream();
	 
     String line = null ;
     try {
     BufferedReader reader = request.getReader();
     while((line = reader.readLine()) != null) 
    	 json.append(line);
     }catch (Exception err) {
    	 out.println(err.getMessage());     }
     
       UserRepository bk = new UserRepository() ;
	 
     try {
    	 
    	 JSONObject  bookObj = new JSONObject(json.toString());
    	 
    	 String bkName = bookObj.getString("bkName");
    	 String bkDesc = bookObj.getString("bkDesc");
    	 double bkCost    = bookObj.getDouble("bkCost");
    	 String bkLang = bookObj.getString("bkLang") ;
         
    	 
    	 
    	 JSONArray  authorArray = bookObj.getJSONArray("bkAutor") ;
    	 JSONObject authorObj  = authorArray.getJSONObject(0) ;
    	 
    	 String autName  = authorObj.getString("aName");
    	 String autPlace =  authorObj.getString("aPlace");
    	 String autNickname = authorObj.getString("aNickname");
    	 
    	String BKid = bk.bookControler(bkName, bkCost, bkDesc , bkLang ,autName ,autPlace,autNickname);
    	 
    	 out.println(BKid);
    	 
    	 
    	 
     }catch (Exception err) {
    	 out.println(err.getMessage());
     }
	 
 }

}
