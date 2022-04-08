package no.itera.dummytunnel.config;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import no.itera.dummytunnel.kafka.StateUpdate;
import no.itera.dummytunnel.kafka.ValueUpdate;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

@EnableKafka
@Configuration
public class KafkaConfiguration {


    @Bean
    public NewTopic valueUpdateTopic(
            @Value("${valueUpdateTopic.name:value-update-topic}") String topicName,
            @Value("${valueUpdateTopic.partitions:10}") int partitions,
            @Value("${valueUpdateTopic.retention.ms:360000}") int retentionMillis
    ) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .configs(Map.of("retention.ms", String.valueOf(retentionMillis)))
                .build();
    }

    @Bean
    public NewTopic stateUpdateTopic(
            @Value("${stateUpdateTopic.name:state-update-topic}") String topicName,
            @Value("${stateUpdateTopic.partitions:10}") int partitions
    ) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .build();
    }

    @Bean("valueUpdateKafkaTemplate")
    public KafkaTemplate<Long, ValueUpdate> valueUpdateKafkaTemplate(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        var producerFactory = new DefaultKafkaProducerFactory<Long, ValueUpdate>(
                Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class.getName(),
                        SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081"
                )
        );
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean("stateUpdateKafkaTemplate")
    public KafkaTemplate<Long, StateUpdate> stateUpdateKafkaTemplate(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        var producerFactory = new DefaultKafkaProducerFactory<Long, StateUpdate>(
                Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class.getName(),
                        SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081"
                )
        );
        return new KafkaTemplate<>(producerFactory);
    }
}
