package org.connection;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class ConexionMongo {
    private static ConexionMongo instance;
    private String uri;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    private ConexionMongo() {
        this.uri = "mongodb://localhost:27017";
        this.mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("ConciertosYA");
        this.collection = database.getCollection("auditorias");
        System.out.println("Conexión MongoDB exitosa");
    }

    public static ConexionMongo getInstance() {
        if (instance == null) {
            instance = new ConexionMongo();
        }
        return instance;
    }

    public static MongoCollection<Document> getCollection() {
        return getInstance().collection;
    }
}
