package com.shakepoint.web.firebase.mdb;

import com.shakepoint.web.firebase.fcm.FirebaseClientService;
import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@MessageDriven(name = "SendPushNotificationMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "send_push_notification_queue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class SendPushNotificationMDB implements MessageListener {

    private final Logger log = Logger.getLogger(getClass());
    private static final String MESSAGE_TYPE = "com.shakepoint.jms.messageType";

    @Inject
    private FirebaseClientService firebaseClientService;

    @Override
    public void onMessage(Message message) {
        try{
            TextMessage textMessage = (TextMessage) message;
            String messageType = textMessage.getStringProperty(MESSAGE_TYPE);
            firebaseClientService.sendPushNotification(textMessage.getText(), messageType);
        }catch(JMSException ex) {
            log.error("Could not get data from message", ex);
        }
    }
}
