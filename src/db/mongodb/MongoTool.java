package db.mongodb;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoTool {
  private static MongoTool instance = new MongoTool();
  private MongoClient mongoClient=null;
  private MongoTool(){
	  mongoClient = new MongoClient("localhost",27017);
  }
  public synchronized static MongoTool instance(){
	  return instance;
  }
  
  public MongoClient getMongoClient() throws NullPointerException{
	  if(mongoClient==null)throw new  NullPointerException("instance of mongoClient is NULL");
	  return mongoClient;
  }
  
  public MongoDatabase getMongoDatabase(final String dataBaseName)throws NullPointerException{
	  if(this.mongoClient==null)throw new  NullPointerException("instance of mongoClient is NULL");
	  if(dataBaseName==null || StringUtils.trimToNull(dataBaseName)==null)throw new  NullPointerException("paramster of dataBaseName is NULL");
	  return this.mongoClient.getDatabase(dataBaseName);
  }
  
  public MongoCollection getMongoCollection(final String dataBaseName,final String collectionName) throws NullPointerException{
	  if(this.mongoClient==null)throw new NullPointerException("instance of mongoClient is NULL");
	  if(dataBaseName==null || StringUtils.trimToNull(dataBaseName)==null)throw new  NullPointerException("paramster of dataBaseName is NULL");
	  if(collectionName==null || StringUtils.trimToNull(collectionName)==null)throw new  NullPointerException("paramster of collectionName is NULL");
  	  MongoDatabase mongoDatabase = this.mongoClient.getDatabase(dataBaseName);
  	  MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
	  return mongoCollection;
  }
  
}
