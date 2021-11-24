package mongodb_java;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
public class MongoMain {
	public static void main(String[] args) {
		//Mongo DB 연결 
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		//test 라는 데이터베이스 연결 
		MongoDatabase database = mongoClient.getDatabase("adam");
		//소유한 모든 컬렉션 확인 
		for (String name : database.listCollectionNames()) {
			System.out.println(name);
		}
	}
}
