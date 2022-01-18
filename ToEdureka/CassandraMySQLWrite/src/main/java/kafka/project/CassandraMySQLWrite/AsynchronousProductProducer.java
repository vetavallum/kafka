package kafka.project.CassandraMySQLWrite;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.*;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.sql.Timestamp;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException; 

public class AsynchronousProductProducer
{
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Properties props = new Properties();
    	String line = "";  
    	String splitBy = ",";  
    	Gson gson = new Gson();

        props.put("bootstrap.servers", "192.168.99.101:9101");
        props.put("acks", "1");  //"0" -No ack, "1" only Leader ,"all" ALL
        props.put("retries", 0);  // "0" doesn't re try ; positive value will retry

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", JsonSerializer.class);
        props.put("buffer.memory", "104857600"); // 100Mb   default is 32MB
        props.put("max.block.ms", "30000"); //    default is 60000
        props.put("batch.size", 16384);  // batch size in bytes "0" means no batching
        props.put("linger.ms", 20000); //default is 0

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        System.out.println("ProducerRecord Created " + new Timestamp(System.currentTimeMillis()));
       
//        for(int i = 0; i < 30; i++)
//        {
//        	ProductModelNew product = new ProductModelNew(i+100, "sup"+i, "brand"+i, "description"+i, i+10, "category"+i, "subCategory"+i, i+1000, i, "country"+i, "sellerCode"+i, new Timestamp(System.currentTimeMillis()), i);
//            String jsonProductString = gson.toJson(product);
//            ProducerRecord<String, String> record = new ProducerRecord<String, String>("productinformations", product.getCategory()+ i, jsonProductString);
//            producer.send(record, new ProducerCallBack());
//
//        }

    	try   
    	{  
    		BufferedReader br = new BufferedReader(new FileReader("C:\\src\\products.csv"));  
    		line = br.readLine();
    		while ((line = br.readLine()) != null)   //returns a Boolean value  
    		{  
    			String[] productColumn = line.split(splitBy);    // use comma as separator
    			
   	        	ProductModelNew product = new ProductModelNew(productColumn[0], productColumn[1], productColumn[2], 
   	        								productColumn[3], productColumn[4], productColumn[5], productColumn[6], 
   	        								Float.parseFloat(productColumn[7]), Integer.parseInt(productColumn[8]), 
   	        								productColumn[9], productColumn[10], productColumn[11], productColumn[12]);
   	        	
	            String jsonProductString = gson.toJson(product);
	            ProducerRecord<String, String> record = new ProducerRecord<String, String>("productlisttopic", jsonProductString);
	            producer.send(record, new ProducerCallBack());
	            
    		}  
    		br.close();
            producer.close();
            System.out.println("Json String published " + new Timestamp(System.currentTimeMillis()));
    	}   
    	catch (IOException e)   
    	{  
    		e.printStackTrace();
    	}  
    }
}

class ProducerCallBack implements Callback
{
    public void onCompletion(RecordMetadata recordMetadata, Exception e)
    {
        if( e!=null)
        {
            e.printStackTrace();
        }
        else
        {
            System.out.println("Json record published Asynchronously to [partition:" + recordMetadata.partition() + ",offset:" + recordMetadata.offset() + "]");
        }
    }
}
