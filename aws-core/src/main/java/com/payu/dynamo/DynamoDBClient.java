package com.payu.dynamo;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by akesh.patil on 14-03-2017.
 */

@Configuration
public class DynamoDBClient {

    @Value("${aws.region}")
    private String awsRegion;

    public AmazonDynamoDBClient createDynamoDbClient() {
        AWSCredentials credentials = null;
        try {
            credentials = new DefaultAWSCredentialsProviderChain().getCredentials();
            Region region = Region.getRegion(Regions.fromName(awsRegion));
            AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentials);
            dynamoDBClient.setRegion(region);
            return dynamoDBClient;
        } catch (Exception e) {
            throw new AmazonClientException("Unable to load AWS credentials ", e);
        }
    }
}
