package dev.practice.order.infrastructure;

import dev.practice.order.domain.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class NotificationExecutor implements NotificationService {
    @Override
    @Transactional
    public void sendEmail(String email, String title, String description) {
        log.info("sendEmail");
    }

    @Override
    @Transactional
    public void sendKakao(String phoneNo, String description) {
        log.info("sendKakao");
    }

    @Override
    @Transactional
    public void sendSms(String phoneNo, String description) {
        log.info("sendSms");
    }
}
