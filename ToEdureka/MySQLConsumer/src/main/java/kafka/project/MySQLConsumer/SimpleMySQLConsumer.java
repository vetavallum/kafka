package kafka.project.MySQLConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

public class SimpleMySQLConsumer {

	public static void main(String[] args) {
    	try{  
    		
    		Gson gson = new Gson();
    		Properties props = new Properties();
    		props.put("bootstrap.servers", "192.168.99.101:9101");
    		props.put("group.id", "grp-2");
	        props.put("enable.auto.commit", "true");
	        props.put("auto.commit.interval.ms", "1000");
	        props.put("session.timeout.ms", "30000");
	        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://192.168.99.101:3306/edureka?autoReconnect=true&useSSL=false","root","password");
			Statement stmt=con.createStatement();
		
			consumer.subscribe(Arrays.asList("productlisttopic"));
			while (true)
			{
				ConsumerRecords<String, String> records = consumer.poll(1000);
				for (ConsumerRecord<String, String> record : records)
				{
					ProductModelMySQL product = gson.fromJson(record.value(),ProductModelMySQL.class);
					System.out.println(product.getPogId());
					stmt.executeUpdate("INSERT INTO productlist (pogId,  supC, price, quantity) \r\n" + 
						"VALUES ('"+product.getPogId()+"', '"+product.getSupC()+"' , '"+product.getPrice()+"', '"+product.getQuantity()+"');" );
				}
				//con.close();
			}
    	}
		catch(Exception e)
		{ 
			System.out.println(e);
		}
	}
}