package no.itera.dummytunnel.config;

import no.itera.dummytunnel.kafka.Instruction;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

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

    @Bean
    public NewTopic instructionsTopic(
            @Value("${instructionsTopic.name:state-update-topic}") String topicName,
            @Value("${instructionsTopic.partitions:10}") int partitions
    ) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .build();
    }
}
