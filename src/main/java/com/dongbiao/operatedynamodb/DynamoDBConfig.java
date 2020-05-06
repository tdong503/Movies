package com.dongbiao.operatedynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class DynamoDBConfig {
    public AWSCredentialsProvider awsCredentialsProvider = new AWSCredentialsProvider() {
        @Override
        public AWSCredentials getCredentials() {
            Properties props = getProperties();
            String amazonAWSAccessKey = props.getProperty("amazon.aws.accesskey");
            String amazonAWSSecretKey = props.getProperty("amazon.aws.secretkey");

            return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
        }

        @Override
        public void refresh() {

        }
    };

    public String getDBEndpoint()
    {
        Properties props = getProperties();
        return props.getProperty("dynamodb.endpoint");
    }

    private Properties getProperties()
    {
        Resource resource = new ClassPathResource("application.properties");

        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props;
    }
}
