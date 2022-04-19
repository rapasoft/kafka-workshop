package no.itera.dummytunnel.simulator;

import no.itera.dummytunnel.kafka.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InstructionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(InstructionConsumer.class);

    @KafkaListener(
            groupId = "${instructionsConsumer.groupId}",
            topics = "${instructionsTopic.name}",
            properties = {
                    "value.deserializer=io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer",
                    "key.deserializer=org.apache.kafka.common.serialization.LongDeserializer",
                    "schema.registry.url=${spring.kafka.properties.schema.registry.url}",
                    "json.value.type=no.itera.dummytunnel.kafka.Instruction"
            },
            autoStartup = "true"

    )
    public void consumeInstruction(Instruction message) {
        logger.info("Instruction consumed: {}", message);
    }

}
