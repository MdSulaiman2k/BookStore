package bookStore;

import java.sql.*;

public class BookRepository  {
	Connection con  ;
	Statement st ;
	String db_url = "jdbc:mariadb://localhost:8888/bookStore";
	String db_user_name = "root";
	String db_user_pass = "sweetsulaiman";
	   
	public BookRepository() {
	    try {
	      Class.forName("java.sql.DriverManager") ;
	      con = DriverManager.getConnection(db_url,db_user_name,db_user_pass);
	      st = con.createStatement();
	     }catch( Exception sql) {
	    	 System.out.println(sql.getMessage());
	    	 
	    	
	    }	
	}
	
		
	public String bookControler(String bName  , double cost , String bDescr ,String bLang, String authName , String authPlace , String authNickname) throws SQLException {
		  
		  ResultSet rs = st.executeQuery("select AuthId  from AuthorContainer where Name = \"" + authName + "\" ;"  ) ;
		  String bkid = null ;
		  
		  if(!(rs.next())) {
			  int authourdetails = st.executeUpdate("insert into AuthorContainer(Name , Place , Nickname )  values(\"" + authName + "\" , \"" + authPlace + "\" , \"" + authNickname + "\" );"  );
			 
			  if(authourdetails <= 0) {
				  return bkid ;
			  }
		  }
		  bkid = ResultQuery("bookContainer",bName);
		  if(bkid != null) {
			  bkid = null ;
			  return bkid ;
		  }
		  
		  int bookdetails = st.executeUpdate("insert into bookContainer(Name, Author,Cost,about,Language)  values(\"" + bName + "\" , \"" + authName + "\" ," + cost + " , \"" + bDescr + "\" , \"" + bLang + "\" );"   );
		
		  
		  if(bookdetails > 0) {
			  bkid = ResultQuery("bookContainer",bName);  
			 }
		   return bkid;
}
	
	public String ResultQuery(String tableName, String priColumnName ) throws SQLException {
		String bkId = null ;
		String insert = " select Id from " + tableName + " where Name = \"" +  priColumnName + "\";" ;
		ResultSet rs = st.executeQuery(insert) ;
		if(rs.next())
			bkId = rs.getString("Id");
		return bkId;
	}
	

	

	
}
