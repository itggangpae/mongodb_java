package mongodb_java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class GoodDAO{
	// 싱글톤 클래스를 만드는 코드
	private GoodDAO() {}

	private static GoodDAO obj;

	public static GoodDAO getInstance() {
		if (obj == null)
			obj = new GoodDAO();
		return obj;
	}

	// 데이터베이스 연동 메소드에서 사용할 변수 선언
	MongoClient mongoClient;
	MongoDatabase database;

	// 데이터베이스 연결 메소드
	private void connect() {
		mongoClient = MongoClients.create("mongodb://localhost:27017");
		database = mongoClient.getDatabase("test");
	}

	// 데이터베이스 연결 해제 메소드
	private void close() {
		mongoClient.close();
	}

	// Goods 테이블의 모든 데이터를 읽어서 리턴하는 메소드
	public List<Document> allGoods() {
		// 데이터를 저장해서 리턴할 인스턴스 생성
		List<Document> list = new ArrayList<Document>();
		connect();
		try {
			MongoCollection<Document> collection = database.getCollection("goods");
			try (MongoCursor<Document> cur = collection.find().iterator()) 			{
				while (cur.hasNext()) {
					Document doc = cur.next();
					list.add(doc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	//하나의 데이터를 가져오는 메서드
	public Document getGood(String code) {
		Document document = null;
		connect();
		try {
			MongoCollection<Document> collection = database.getCollection("goods");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			BasicDBObject object = new BasicDBObject(map);
			FindIterable<Document> fit = collection.find(object);
			ArrayList<Document> docs = new ArrayList<Document>();
			fit.into(docs);
			if(docs.size() > 0) {
				document = docs.get(0);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return document;
	}
	
	// 하나의 데이터를 삽입하는 메소드
	public Boolean insertGood(Document document) {
		Boolean result = false;
		connect();
		try {
			MongoCollection<Document> collection = database.getCollection("goods");
			collection.insertOne(document);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	// 하나의 데이터를 수정하는 메소드
	public Boolean updateGood(Document document) {
		Boolean result = false;
		connect();
		try {
			MongoCollection<Document> collection = database.getCollection("goods");
			collection.updateOne(new Document("code", document.get("code")),
					new Document("$set", document));
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	//데이터를 삭제하는 메소드
	public Boolean deleteGood(String code) {
		Boolean result = false;
		connect();
		try {
			MongoCollection<Document> collection = database.getCollection("goods");
			Document document = new Document();
			document.append("code",code);
			collection.deleteOne(document);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
}
