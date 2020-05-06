package com.dongbiao.operatedynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MovieGetData {
    public List<String> get()
    {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new DynamoDBConfig().awsCredentialsProvider)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(new DynamoDBConfig().getDBEndpoint(), "us-west-2"))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        List<String> result = new ArrayList<>();

        TableCollection<ListTablesResult> tables = dynamoDB.listTables();

        if(tables.getLastLowLevelResult() == null || tables.getLastLowLevelResult().getTableNames().size() == 0)
        {
            MovieCreateTable.createTable();
            new MovieInsertData().insert();
        }

        Table table = dynamoDB.getTable("Movies");
        if(table == null)
        {
            System.err.println("no table");
        }
        else
        {
            HashMap<String, String> nameMap = new HashMap<>();
            nameMap.put("#yr", "year");

            HashMap<String, Object> valueMap = new HashMap<>();
            valueMap.put(":yyyy", 1985);

            QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                    .withValueMap(valueMap);

            ItemCollection<QueryOutcome> items = null;
            Iterator<Item> iterator = null;
            Item item = null;


            try {
                System.out.println("Movies from 1985");
                items = table.query(querySpec);

                iterator = items.iterator();
                while (iterator.hasNext()) {
                    item = iterator.next();
                    result.add(item.getNumber("year") + ": " + item.getString("title"));
                }
            }
            catch (Exception e)
            {
                System.err.println("Unable to query movies from 1985");
                System.err.println(e.getMessage());
            }
        }
        for(String item : result)
        {
            System.out.println(item);
        }
        return result;
    }
}
