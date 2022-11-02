package br.com.kafka.exemplo.kafkaconsumer.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import br.com.kafka.exemplo.kafkaconsumer.entity.Contact;
import br.com.kafka.exemplo.kafkaconsumer.service.ContactService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TopicConsumer {

    @Value("${topic.name.consumer}")
    private String topicName;
 
    @Autowired
    private ContactService contactService;
    
    private final KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(producerFactory());
	


    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload){
    	log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());

    }
    
    @KafkaListener(topics = "topico.contato.teste", groupId = "group_id")
    public void consumeBody(ConsumerRecord<String, String> payload){
    	log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());
        Contact contact = new Gson().fromJson(payload.value(), Contact.class);
        contactService.insertContact(contact);

    }
    
    @KafkaListener(topics = "topico.lista.consumidor", groupId = "group_id")
    public void consumeListContacts(ConsumerRecord<String, String> payload){
    	log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());
        List<Contact> contacts = new ArrayList<Contact>();
        contacts = contactService.listContact();
        sendList(contacts);

    }
    
	public void sendList(List<Contact> contacts) {
		String topic = "topico.lista.producer";
		Gson gson = new Gson();
		kafkaTemplate.send(topic, gson.toJson(gson.toJson(contacts)));
	}
    
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          "127.0.0.1:9092");
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
