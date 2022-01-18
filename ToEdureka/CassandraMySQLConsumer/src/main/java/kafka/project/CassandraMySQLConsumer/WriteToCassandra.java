package kafka.project.CassandraMySQLConsumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

public class WriteToCassandra
{
    public static void main(String[] args)
    {
    	Gson gson = new Gson();
    	Properties props = new Properties();
    	props.put("bootstrap.servers", "192.168.99.101:9101");
    	props.put("group.id", "grp-1");
    	props.put("enable.auto.commit", "true");
    	props.put("auto.commit.interval.ms", "1000");
    	props.put("session.timeout.ms", "30000");
    	props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    	props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    	KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

    	final CassandraConnector client = new CassandraConnector();
    	client.connect("192.168.99.101", 9042);

    	consumer.subscribe(Arrays.asList("productlisttopic"));
		while (true)
		{
			ConsumerRecords<String, String> records = consumer.poll(1000);
			for (ConsumerRecord<String, String> record : records)
			{
				ProductModel product = gson.fromJson(record.value(),ProductModel.class);
				client.getSession().execute("INSERT INTO edureka.productlist(pogId,supC,brand,description,size,category,subCategory,country,sellerCode)" +
											"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
											product.getPogId(),
		                                	product.getSupC(),
		                                	product.getBrand(),
		                                	product.getDescription(),
		                                	product.getSize(),
		                                	product.getCategory(),
		                                	product.getSubCategory(),
		                                	product.getCountry(),
		                                	product.getSellerCode());
			}
		}
   	}
}
