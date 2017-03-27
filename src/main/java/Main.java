package main.java;

import static com.mongodb.client.model.Filters.eq;
import static spark.Spark.*;

import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;


 
public class Main {
	
public static Boolean valid=true;	


	public static void main(String[] args) {
		
		port(Integer.valueOf(System.getenv("PORT")));
		MongoClientURI uri  = new MongoClientURI("mongodb://test:test@ds135830.mlab.com:35830/heroku_z88tkdfm"); 
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB(uri.getDatabase());
        DBCollection user = db.getCollection("users");

		
		
		// basic help response to a blank call to the webpage
		get("/test/:Nickname/:Score", (request, response) -> {
			String Nickname = request.params(":Nickname");
			String Score = request.params(":Score");
			System.out.println(""+Nickname+" "+Score);

				    try {
				    	
				        BasicDBObject findQuery = new BasicDBObject("Nickname", Nickname);
				        

				        DBCursor docs = user.find(findQuery);
				        
				      
					    	
			            	/**** Insert ****/
					    	// create a document to store key and value
					    	BasicDBObject document = new BasicDBObject();
					    	document.put("Nickname", Nickname);
					    	document.put("Score", Score);
								    	
					    	
					    	user.insert(document);
					    	
					    	
			            	return "success";
			            
			
					
				    } catch (MongoException e) {
				    	e.printStackTrace();
				        }
				    
			
			return "";
			
			
		});
		
		get("/GetScores", (request, response) -> {
					
			
			
			try {

           	 ArrayList<BasicDBObject> obj = new ArrayList<BasicDBObject>();		        
		        BasicDBObject findQuery = new BasicDBObject();
		       
		        DBCursor docs = user.find();
		    	docs.sort(new BasicDBObject("Score", -1));
		        while(docs.hasNext()) {
		        	DBObject doc = docs.next();
		            obj.add((BasicDBObject) doc); 
		        }		        
		        
		        System.out.println(obj);
		        return obj ;
		           
		        }
		        catch (MongoException e) {
		    	e.printStackTrace();
		        }
		    
	
	return "";
	
		});
	}
}