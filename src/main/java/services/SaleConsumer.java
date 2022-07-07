package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import deserializers.SaleDeserializer;
import model.entities.Sale;
import model.enums.Status;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class SaleConsumer {
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SaleDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group2");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try(KafkaConsumer<String,Sale> consumer = new KafkaConsumer<>(properties)){
            consumer.subscribe(Arrays.asList("sales"));
            while(true){
                ConsumerRecords<String,Sale> records = consumer.poll(Duration.ofMillis(5000));
                for (ConsumerRecord<String,Sale> record : records) {
                    Sale sale = record.value();
                    sale.setStatus(Status.APPROVED);
                    String payload = new ObjectMapper().writeValueAsString(sale);
                    System.out.println(payload);
                    Thread.sleep(500);
                }
            }
        }
    }
}
