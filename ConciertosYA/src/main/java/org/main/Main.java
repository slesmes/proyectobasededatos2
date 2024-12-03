package org.main;

import java.sql.Connection;
import java.util.Scanner;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.connection.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = ConexionPostgres.getConnection();
        MongoCollection<Document> collection = ConexionMongo.getCollection();

    }
}