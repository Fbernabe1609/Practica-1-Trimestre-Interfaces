package org.example.models;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Arrays;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class DbHelper {

    String username = "Fbernabe";
    String password = "pPZXijuT4UaLaL80";
    String databaseName = "sample_mflix";
    String collectionName = "movies";
    ConnectionString connectionString = new ConnectionString("mongodb+srv://" + username + ":" + password + "@cluster0.dmu6ezu.mongodb.net/?retryWrites=true&w=majority");
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build())
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    MongoDatabase database = mongoClient.getDatabase(databaseName);
    MongoCollection<Document> collection = database.getCollection(collectionName);

    public void viewDocument() {
        Document doc = collection.find(eq("title", "Back to the Future")).first();
        //Lo muestra en formato json
        System.out.println(Objects.requireNonNull(doc).toJson());
        //extrae algo del json, no hace falta poner .toString()
        System.out.println(Objects.requireNonNull(doc).get("plot"));
    }

    public void addDataDocument() {
        try {
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("title", "Ski Bloopers")
                    .append("genres", Arrays.asList("Documentary", "Comedy")));
            System.out.println("Success! Inserted document id: " + result.getInsertedId());
        } catch (MongoException e) {
            System.err.println("Unable to insert due to an error: " + e);
        }
    }
}
