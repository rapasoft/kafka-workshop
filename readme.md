# Kafka Workshop

## Prerequisites for participants

- Knowledge of Java/Spring
- JDK 17, Maven, Docker and docker-compose installed
- Intellij IDEA (or other IDE)

## First steps

1. Clone the repo
2. To run the `dummy-tunnel` project with Kafka and other dependencies:
   1. Build it via `mvn clean package` 
   2. Run it via `docker-compose up` (or with `-d` to run it in background)
   3. To rebuild dummytunnelen on changes run `docker-compose --build dummytunnel`
3. Open `project` project
    1. Work :)

## Workshop agenda

This can vary, based on the needs:

### Week 1

- Theory: Kafka main concepts
    - Broker
    - Topics/Partition
    - Producer/Consumer basics
- Practice: Simple Kafka (console/UI) Consumer

### Week 2 - 3

- Theory: Producer/Consumer API
    - Serializers/Deserializers
    - Producers
        - Partition selection
        - Fault tolerance
        - Message idempotence / retry
        - Producer properties:
            - buffer.memory
            - compression.type
            - retries
            - max.in.flight.requests.per.connection
            - delivery.timeout.ms
            - batch.size
            - linger.ms
            - request.timeout.ms
            - acks
            - enable.idempotence
    - Consumers
        - Consumer group lifecycle
        - Rebalancing process
        - Fault tolerance
        - Consumer lag
        - Consumer properties:
            - group.id
            - heartbeat.interval.ms
            - max.partition.fetch.bytes
            - session.timeout.ms
            - allow.auto.create.topics
            - auto.offset.reset
            - enable.auto.commit
            - auto.commit.interval.ms
            - fetch.max.bytes
            - max.poll.interval.ms
            - partition.assignment.strategy
            - request.timeout.ms
- Practice 1: Create consumer for dummytunnel
- Practice 2: Adjusting consumer properties for better throughput

### Week 4 - 6

- Theory: Kafka Streams API
    - Topology - processor nodes
    - Stateless / Stateful transformations
    - KTables / State stores
    - Persistent vs. in memory stores
- Practice: create simple Kafka Streams application







