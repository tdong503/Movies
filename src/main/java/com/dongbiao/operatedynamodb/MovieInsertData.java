package com.dongbiao.operatedynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class MovieInsertData {

    public void insert() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new DynamoDBConfig().awsCredentialsProvider)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(new DynamoDBConfig().getDBEndpoint(), "us-west-2"))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("Movies");

        JsonParser parser = null;
        try {
            parser = new JsonFactory().createParser(new File("moviedata.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode rootNode = null;
        try {
            rootNode = new ObjectMapper().readTree(parser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<JsonNode> iter = rootNode.iterator();

        ObjectNode currentNode;

        while (iter.hasNext()) {
            currentNode = (ObjectNode) iter.next();

            int year = currentNode.path("year").asInt();
            String title = currentNode.path("title").asText();

            try {
                table.putItem(new Item().withPrimaryKey("year", year, "title", title).withJSON("info",
                        currentNode.path("info").toString()));
                System.out.println("PutItem succeeded: " + year + " " + title);

            }
            catch (Exception e) {
                System.err.println("Unable to add movie: " + year + " " + title);
                System.err.println(e.getMessage());
                break;
            }
        }
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
