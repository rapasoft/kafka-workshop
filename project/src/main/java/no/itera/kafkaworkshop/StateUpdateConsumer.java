package no.itera.kafkaworkshop;

import com.fasterxml.jackson.databind.JsonNode;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

@Component
@Profile("week3")
public class StateUpdateConsumer {

    private static final Logger logger = LoggerFactory.getLogger(StateUpdateConsumer.class);

    @Value("${kafka-workshop.groupId}")
    private String groupId;
    @Value("${kafka-workshop.bootstrapServers:localhost:9092}")
    private String bootstrapServers;
    @Value("${kafka-workshop.schemaRegistryUrl:http://127.0.0.1:8081}")
    private String schemaRegistryUrl;
    @Value("${kafka-workshop.stateUpdateTopic:state-update-topic}")
    private String stateUpdateTopic;

    record StateUpdate(
            long deviceId,
            String deviceName,
            long plantId,
            String plantName,
            String variable,
            String state,
            String alarmLevel,
            long timestamp
    ) {
    }

    class StateUpdateConsumerThread implements Runnable {

        @Override
        public void run() {
            try (Consumer<Long, JsonNode> consumer = new KafkaConsumer<>(Map.of(
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonSchemaDeserializer.class,
                    SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl,
                    KafkaJsonSchemaDeserializerConfig.FAIL_INVALID_SCHEMA, true,
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                    ConsumerConfig.GROUP_ID_CONFIG, groupId,
                    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest",
                    "json.value.type", StateUpdate.class
            ))) {
                consumer.subscribe(List.of(stateUpdateTopic));
                while (true) {
                    try {
                        var message = consumer.poll(Duration.ofMillis(1000));

                        if (!message.isEmpty()) {
                            var records = message.records(stateUpdateTopic);
                            records.forEach(m -> {
                                logger.info("Partition %d, offset %d -> %d:%s".formatted(m.partition(), m.offset() ,m.key(), m.value()));
                            });
                        }
                    } catch (RecordDeserializationException e) {
                        logger.error("Cannot deserialize record. ", e);
                        break;
                    }
                }
            }
        }
    }

    @PostConstruct
    public void startConsuming() {
        ExecutorService service = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("consumer-"));
        service.execute(new StateUpdateConsumerThread());
        service.execute(new StateUpdateConsumerThread());
        service.execute(new StateUpdateConsumerThread());
        service.shutdown();
    }

}
