# Kafka Messaging Application

Kafka Messaging Application is a Spring Boot application comprising two modules: kafka-producer and kafka-consumer-service. This application enables the streaming of events from the Wikimedia API to Kafka topics, where consumers can subscribe to these topics and process the events accordingly. The kafka-consumer-service module additionally persists event details into a Microsoft SQL Server database.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- Kafka broker (for running locally)

## Installation and Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/KafkaMessagingApplication.git
    ```

2. **Build the projects:**

    ```bash
    cd KafkaMessagingApplication
    mvn clean install
    ```

## Running the Applications

### Kafka Producer

To run the Kafka producer module, execute the following command:

```bash
java -jar kafka-producer/target/kafka-producer-0.0.1-SNAPSHOT.jar
```

###Kafka Consumer Service

To run the Kafka consumer service module, execute the following command:

```bash
java -jar kafka-consumer-service/target/kafka-consumer-service-0.0.1-SNAPSHOT.jar
```

## Configuration

### Kafka Configuration
Ensure that Kafka broker properties are correctly configured in application.properties files of both kafka-producer and kafka-consumer-service modules.

### Database Configuration
Configure the Microsoft SQL Server database connection properties in the application.properties file of the kafka-consumer-service module.

Kafka Consumer Service Configuration
The Kafka consumer service module can be configured further for various purposes, such as scaling and optimization. Additional configurations, such as adjusting concurrency or tuning consumer properties, can be done in the application.properties file of the kafka-consumer-service module.

## Usage
The Kafka producer fetches data from the Wikimedia API (https://stream.wikimedia.org/v2/stream/recentchange) and publishes events to Kafka topics. The Kafka consumer service subscribes to these topics based on topic and group ID, processes the events, and saves the event details into the configured Microsoft SQL Server database.
Once the Kafka consumer service is running, it continuously listens to the subscribed Kafka topics and processes incoming events. Users can monitor the behavior and performance of the consumer service by analyzing logs and metrics generated during its operation.
