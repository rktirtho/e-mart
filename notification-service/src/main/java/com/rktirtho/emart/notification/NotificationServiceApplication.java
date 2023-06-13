package com.rktirtho.emart.notification;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    @SneakyThrows
    public void handleNotification(OrderPlaceEvent orderPlaceEvent){
        Thread.sleep(5000);
        log.info("An email have been send for order - {}", orderPlaceEvent.getOrderNumber());

    }

}
