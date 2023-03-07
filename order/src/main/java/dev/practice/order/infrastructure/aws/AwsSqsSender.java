package dev.practice.order.infrastructure.aws;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsSqsSender {

    private final AmazonSQSAsync amazonSQSAsync;

    //@Configuration 이 없으므로 싱글톤 아님.
    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }
}
