package db.mongodb;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class SimpleMongoDB {
    public static void main(String[] args) {
    	MongoClient mongoClient = new MongoClient("localhost",27017);
    	MongoDatabase mongoDatabase = mongoClient.getDatabase("local");
    	MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("user");
    	
    	BasicDBObject bson = new BasicDBObject();
    	DBRef dbRef = new DBRef("local.userInfo","5");
    	bson.append("id", dbRef);
    	
    	FindIterable<Document> findIterable = mongoCollection.find(bson);
    	findIterable.skip(0);//跳过多少条
    	findIterable.limit(900);//限制返回多少条
    	
    	MongoCursor<Document> mongoCursor = findIterable.iterator();
    	while(mongoCursor.hasNext())
    	{
    		Map<String,Object> next = mongoCursor.next();
    		System.out.println(next);
    	}
    	
    	if(1==1)return;
    	Mongo mongo = new Mongo("localhost", 27017);
    	DB db = mongo.getDB("local");
    	DBCollection dbCollection = db.getCollection("user");
    	
    	Map<String,Object> paramsMap = new HashMap<String, Object>(3);
    	BasicDBObject basicDBObject = new BasicDBObject(paramsMap);//表示条件
    	basicDBObject.append("id","5").append("name","java操作1");
    //	dbCollection.insert(basicDBObject);
    	
    	paramsMap.put("id","44");
    	paramsMap.put("name","java操作的");
    	paramsMap.put("address","北京市中南海");
    	
    	BasicDBObject updateBasicDBObject = new BasicDBObject(paramsMap);
    	
    	WriteResult writeResult =  dbCollection.update(basicDBObject,updateBasicDBObject);
    	System.out.println(writeResult.toString());
    	
    	DBCursor dbCursor =  dbCollection.find();
       	while(dbCursor.hasNext())
       	{
       		DBObject dbObject = dbCursor.next();
       		//System.out.println(dbObject.get("name"));
       		Map map = dbObject.toMap();
       		System.out.println(map.toString());
       	}
    	//dbCollection.insert(documents)
    	//System.out.println(dbCollection.count());
	}
}

