package tech.tognati.picpay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.tognati.picpay.client.NotificationClient;
import tech.tognati.picpay.entity.Transfer;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationClient notificationClient;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public void sendNotification(Transfer transfer) {
        try {
            logger.info("Sending notification for transfer {}", transfer.getId());
            var response = notificationClient.sendNotification(transfer);
            if(response.getStatusCode().isError()) {
                logger.error("Error while sending notification. Status code: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Error while sending notification", e);
        }
    }
}
