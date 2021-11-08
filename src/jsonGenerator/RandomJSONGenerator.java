package jsonGenerator;

import static com.mongodb.client.model.Filters.gt;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class RandomJSONGenerator {

	final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	Random rand = new Random();
	int minId = 100000;
	int maxId = 999999;

	final Set<String> identifiers = new HashSet<String>();
	static HashMap<String, String> prop = new HashMap<String, String>();

	Set<Integer> employeeidGenerator = new HashSet<Integer>();

	public String randomNameIdentifier() {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(5) + 5;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}

	public int randomBetweenRange(RandomJSONGenerator obj) {

		int result = ((int) Math.floor(Math.random() * (maxId - minId + 1) + minId));
		return result;
	}

	public String randomLevelGenerator() {
		int minlevel = 0;
		int maxlevel = 9;

		int levelint = ((int) Math.floor(Math.random() * (maxlevel - minlevel + 1) + minlevel));
		int levelstr = ((int) Math.floor(Math.random() * (maxlevel - minlevel + 1) + minlevel));

		ArrayList<String> lvl = new ArrayList<>();
		lvl.add("IC");
		lvl.add("MGR");
		lvl.add("COO");
		lvl.add("CFO");
		lvl.add("CTO");
		lvl.add("IC");
		lvl.add("IC");
		lvl.add("IC");
		lvl.add("IC");
		lvl.add("MGR");

		return lvl.get(levelstr) + levelint;

	}

	public int randomAgeGenerator() {
		int minlevel = 22;
		int maxlevel = 55;

		return ((int) Math.floor(Math.random() * (maxlevel - minlevel + 1) + minlevel));

	}

	public String randomQualGenerator() {
		int minlevel = 0;
		int maxlevel = 9;

		ArrayList<String> qualification = new ArrayList<>();
		qualification.add("Under Graduate");
		qualification.add("Graduate");
		qualification.add("College Drop");
		qualification.add("Phd");
		qualification.add("Masters");

		qualification.add("Under Graduate");
		qualification.add("Graduate");
		qualification.add("College Drop");
		qualification.add("Phd");
		qualification.add("Masters");

		return qualification.get((int) Math.floor(Math.random() * (maxlevel - minlevel + 1) + minlevel));

	}

	public ArrayList<String> getNationalityList() {
		ArrayList<String> nationalityList = new ArrayList<>();
		nationalityList.add("India");
		nationalityList.add("Canada");

		nationalityList.add("Australia");
		nationalityList.add("Austria");

		nationalityList.add("China");
		nationalityList.add("Dutch");

		nationalityList.add("France");
		nationalityList.add("Germany");

		nationalityList.add("Japan");
		nationalityList.add("Mexico");

		nationalityList.add("Russia");
		nationalityList.add("India");

		return nationalityList;
	}

	public Document randomAddressGenerator(ArrayList<String> nationalityList) {

		Document addressObj = new Document();
		try {
			addressObj.put("Street", "Street_" + rand.nextInt(100) + 1);
			addressObj.put("City", "City_" + rand.nextInt(100) + 1);
			addressObj.put("Nationality", nationalityList.get(rand.nextInt(nationalityList.size())));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return addressObj;

	}
	 public String phone() {
	        Random rand = new Random();
	        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
	        int num2 = rand.nextInt(743);
	        int num3 = rand.nextInt(10000);

	        DecimalFormat df3 = new DecimalFormat("000");
	        DecimalFormat df4 = new DecimalFormat("0000");

	        String phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
	        return phoneNumber;
	    }

	public void generateRandomData(int count, MongoCollection<Document> collection) {

		try {

			Document document = new Document();
			ArrayList<String> keyList = new ArrayList<>();
			keyList.add("Name");
			keyList.add("EmployeeId");
			keyList.add("Designation");
			keyList.add("Level");
			keyList.add("Age");
			keyList.add("Qualification");
			keyList.add("Address");

			RandomJSONGenerator obj = new RandomJSONGenerator();

			List<Document> docs = new ArrayList<>();
			for (int i = 0; i < count; i++) {
				int j = 0;
				ObjectId objid = new ObjectId();
				document.put("_id", objid);
				document.put(keyList.get(j++), obj.randomNameIdentifier()); // Name
				document.put(keyList.get(j++), obj.randomBetweenRange(obj)); // EmployeeId
				document.put(keyList.get(j++), obj.randomNameIdentifier()); // Designation
				document.put(keyList.get(j++), obj.randomLevelGenerator()); // Level
				document.put(keyList.get(j++), obj.randomAgeGenerator()); // Age
				document.put(keyList.get(j++), obj.randomQualGenerator()); // Qualification
				document.put(keyList.get(j++), obj.randomAddressGenerator(obj.getNationalityList())); // Address
				document.put("myid", i + 1); // myid
//				System.out.println(document);

				docs.add(document);
				collection.insertOne(document);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteData(MongoCollection<Document> collection, int operationsCount) {

		Bson query = gt("myid", operationsCount);
		try {
			DeleteResult result = collection.deleteMany(query);
			System.out.println("Deleted Document Count :: " + result.getDeletedCount());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAndUnsetData(MongoCollection<Document> collection, int operationsCount) {
		try {
			Bson query = gt("myid", operationsCount);
			Bson updateOperation1 = Updates.set("Comments", "Setting this key via updates set");
			Bson updateOperation2 = Updates.set("Address.Landmark", "Updating Landmark via updates set");
			Bson updateOperation3 = Updates.set("Address.Street", (rand.nextInt(100) + 1) + "Street_Updated");
			Bson updateOperation4 = Updates.unset("Address.City");
			Bson updateOperation = Updates.combine(updateOperation1, updateOperation2, updateOperation3,
					updateOperation4);
			UpdateResult updateResult = collection.updateMany(query, updateOperation);
			System.out.println(updateResult.getModifiedCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpsertDataViasetAndUnset(MongoCollection<Document> collection, RandomJSONGenerator obj,
			int operationsCount, int upsertoperationsCount) {
		try {
			UpdateOptions options = new UpdateOptions().upsert(true);
			UpdateResult updateResult = null;
			for (int i = operationsCount; i < operationsCount + upsertoperationsCount; i++) {
				Bson query = gt("myid", i);
				Bson updateOperation1 = Updates.set("comment", obj.randomNameIdentifier());
				Bson updateOperation2 = Updates.set("Address.Landmark", "Update Landmark via updates set!");
				Bson updateOperation3 = Updates.set("Address.Street", (rand.nextInt(100) + 1) + "_Street_Upsert");
				Bson updateOperation4 = Updates.unset("Address.City");
				Bson updateOperation5 = Updates.set("Name", obj.randomNameIdentifier());
				Bson updateOperation6 = Updates.set("Age", obj.randomAgeGenerator());
				Bson updateOperation7 = Updates.set("UpsertIdentifier", true);
				Bson updateOperation = Updates.combine(updateOperation1, updateOperation2, updateOperation3,
						updateOperation4, updateOperation5, updateOperation6, updateOperation7);
				updateResult = collection.updateMany(query, updateOperation, options);
			}
			System.out.println(updateResult.getModifiedCount());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void drobDB() {
		String mongoClientURI = prop.get("MONGO_CLIENT_URI").trim();
		
		MongoClientURI connectionString = new MongoClientURI(mongoClientURI);
		MongoClient mongoClient = new MongoClient(connectionString);

		try {
			MongoDatabase database = mongoClient.getDatabase(prop.get("DATABASE_NAME").trim());
			database.drop();

			
		} catch (Exception e) {
			System.out.println("Mongo is down");
			mongoClient.close();
			return;
		}
	}

	@SuppressWarnings("resource")
	public MongoCollection<Document> getMongDBCollection(String collectionName) {
		MongoCollection<Document> collection = null;
		try {

			String mongoClientURI = prop.get("MONGO_CLIENT_URI").trim();
			MongoClientURI connectionString = new MongoClientURI(mongoClientURI);
			MongoClient mongoClient = new MongoClient(connectionString);
			MongoDatabase database = mongoClient.getDatabase(prop.get("DATABASE_NAME").trim());
			collection = database.getCollection(collectionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collection;
	}

	public HashMap<String, String> readPropertiesFile() {
		HashMap<String, String> map = new HashMap<String, String>();
		Properties prop = new Properties();
		String fileName = "Properties.config";
		String filePath = "Resource/Configuration/";
		try (FileInputStream fis = new FileInputStream(filePath + fileName)) {
			prop.load(fis);
			map.putAll(prop.entrySet().stream()
					.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}


	public static void main(String[] args) {

		try {

			long startTime = System.currentTimeMillis();
			RandomJSONGenerator obj = new RandomJSONGenerator();
			prop = obj.readPropertiesFile();
			MongoCollection<Document> collection = obj.getMongDBCollection(prop.get("COLLECTION_NAME").trim());
			int insertOperationsCount = Integer.parseInt(prop.get("INSERT_OPERATION_COUNT").trim());
			int updateOperationCount = Integer.parseInt(prop.get("UPDATE_OPERATION_COUNT").trim());
			int deleteOperationCount = Integer.parseInt(prop.get("DELETE_OPERATION_COUNT").trim());
			int upsertOperationCount = Integer.parseInt(prop.get("UPSERT_OPERATION_COUNT").trim());
			if(updateOperationCount > insertOperationsCount || deleteOperationCount > insertOperationsCount) { 
	            throw new ArithmeticException("Check Config file :: UPDATE_OPERATION_COUNT and DELETE_OPERATION_COUNT should be less than INSERT_OPERATION_COUNT");    
	        } 
			obj.generateRandomData(insertOperationsCount, collection);
			Thread.sleep(10000);
			obj.deleteData(collection,
					insertOperationsCount - deleteOperationCount);
			Thread.sleep(10000);
			obj.setAndUnsetData(collection,
					insertOperationsCount - updateOperationCount);
			obj.UpsertDataViasetAndUnset(collection, obj, insertOperationsCount + 1,upsertOperationCount);
			long endTime = System.currentTimeMillis();
			System.out.println((endTime - startTime) * 0.001);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
