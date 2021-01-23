package bookStore;
import java.sql.*;
public class UserRepository  {
	   String db_url = "jdbc:mariadb://localhost:8888/bookStore";
	   String db_user_name = "root";
	   String db_user_pass = "sweetsulaiman";
	   Connection con  ;
	   Statement st   ;
	   
	   public UserRepository() {
		   try {
				
				Class.forName("java.sql.DriverManager");
				con = DriverManager.getConnection(db_url,db_user_name,db_user_pass);
				 st = con.createStatement() ;
			} catch (SQLException | ClassNotFoundException e  ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  
	   public String Register(String reg_name , String reg_email , String  reg_pass ) throws SQLException {
		
		   String userid = null ;
		
		   if(reg_name != null && reg_email != null && reg_pass!=null) {
		        //ResultSet Res = st.executeQuery("select Id from userContainer where mailId = \"" + reg_email +"\" ;") ; 
				
			    if(ResultQuery("userContainer" , "mailId" ,reg_email) == null) {
				int  n  = st.executeUpdate("insert into userContainer(name ,mailId , password ) values ( \"" + reg_name +"\",\""+ reg_email + "\" , \"" + reg_pass + "\" ) ;" ) ;
			    if(n>0)
			    	userid = ResultQuery("userContainer" , "mailId" , reg_email) ;
			       
			}
				con.close();
				st.close();
		    }
	    return userid ; 
	   }
	
	public String Login(String mail_Id , String password) throws SQLException {
		String userid = null  ;
		 if(mail_Id != null && password != null ) {
			    ResultSet rs = st.executeQuery("select Id from userContainer where mailId  = \"" + mail_Id +"\"  AND   password = \"" + password + "\" ;");
			   
				if(rs.next()) {
					userid = rs.getString("Id");
					
		        }
				 
				
				st.close();
				con.close();
			}	
			
		return userid ;
	}
	
	public String AdminLogin(String admin_Id , String password) throws SQLException {
        String userid = null  ;
		ResultSet rs = st.executeQuery("select Id from adminDetail where Id  = \"" + admin_Id  +"\" AND   password = \"" + password + "\" ;");
			if(rs.next()) {
				userid = "success" ;
				
			}
			st.close();
			con.close();
		return userid ;
}
	
	public String bookControler(String bName  , double cost , String bDescr ,String bLang, String authName , String authPlace , String authNickname) throws SQLException {
		  String insert1 = "insert into AuthorContainer(Name , Place , Nickname )  values(\"" + authName + "\" , \"" + authPlace + "\" , \"" + authNickname + "\" );" ;
		  String insert2 = "insert into bookContainer(Name, Author,Cost,about,Language)  values(\"" + bName + "\" , \"" + authName + "\" ," + cost + " , \"" + bDescr + "\" , \"" + bLang + "\" );" ;
		  String Id = null ;
		  
		  //ResultSet rs = st.executeQuery("select Id  from AuthorContainer where Name = \"" + authName + "\" ;"  ) ;
		  
		  Id = ResultQuery("bookContainer","Name",bName);
		  if(Id != null) 
			  return null ;
		  
		  Id = ResultQuery("AuthorContainer","Name",authName);

		  if(Id == null)
			  st.addBatch(insert1);
		  
		  st.addBatch(insert2);
		  
		  st.executeBatch();
		  
		  Id = ResultQuery("bookContainer","Name",bName) ;
		   
		  return Id;
}
	
	public String ResultQuery( String tableName , String priColumnName , String Column  ) throws SQLException {
		String bkId = null ;
		String insert = " select Id from " + tableName + " where " + priColumnName + " = \"" +  Column + "\";" ;
		ResultSet rs = st.executeQuery(insert) ;
		if(rs.next())
			bkId = rs.getString("Id");
		rs.close();
		return bkId;
	}
	

	

}



