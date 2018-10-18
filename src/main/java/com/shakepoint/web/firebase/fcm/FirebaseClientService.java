package com.shakepoint.web.firebase.fcm;

import com.google.gson.Gson;
import com.shakepoint.web.firebase.fcm.msg.FirebaseMessage;
import com.shakepoint.web.firebase.fcm.msg.FirebaseMessageEnvelope;
import com.shakepoint.web.firebase.fcm.msg.FirebaseMessageRequest;
import com.shakepoint.web.firebase.fcm.msg.FirebaseNotification;
import com.shakepoint.web.firebase.fcm.msg.FirebaseResponse;
import com.shakepoint.web.firebase.fcm.msg.MessageType;
import okhttp3.ResponseBody;
import org.apache.log4j.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
@Startup
public class FirebaseClientService {

    @Inject
    private FirebaseRetrofitConfiguration configuration;

    private final Logger log = Logger.getLogger(getClass());
    private FirebaseClient client;

    private final Gson gson = new Gson();

    @PostConstruct
    public void init() {
        client = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(configuration.getServerUrl())
                .build().create(FirebaseClient.class);
    }

    public void sendPushNotification(String jsonMessage, String type) {
        MessageType message = MessageType.valueOf(type);
        //TODO:depending on message type, use gson to get message and send it with respective data
        switch(message) {
            default:
                break;
        }
        /**FirebaseMessageRequest request = gson.fromJson(jsonMessage, FirebaseMessageRequest.class);
        if (request.getToken() == null) {
            throw new IllegalArgumentException("Invalid FCM token, cannot send push notification");
        }
        try{
            Response<FirebaseResponse> responseBody = client.sendPushNotification(configuration.getApiKey(),
                    createFirebaseEnvelope(request.getToken(), request.getTitle(), request.getMessage()), configuration.getProjectId())
                    .execute();
            if (! responseBody.isSuccessful()) {
                //log raw response
                log.debug(responseBody.raw());
            }
        }catch(IOException ex) {
            log.info("Could not send push notification", ex);
        }**/
    }

    private FirebaseMessageEnvelope createFirebaseEnvelope(String token, String title, String body) {
        FirebaseMessageEnvelope envelope = new FirebaseMessageEnvelope();
        envelope.setMessage(new FirebaseMessage(token, new FirebaseNotification(title, body)));
        return envelope;
    }
}
