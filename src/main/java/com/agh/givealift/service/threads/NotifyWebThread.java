package com.agh.givealift.service.threads;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.response.PushNotificationResponse;
import com.agh.givealift.model.response.SubscriptionResponse;
import com.google.gson.Gson;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Scope("prototype")
public class NotifyWebThread extends Thread {

    private static final COD cod = CODFactory.get();
    private List<PushNotificationResponse> pushNotificationResponses;
    public String notificationKey;


    @Autowired
    public NotifyWebThread(String notificationKey) {
        this.notificationKey = notificationKey;
    }

    public void setPushNotificationResponses(List<PushNotificationResponse> pushNotificationResponses) {
        this.pushNotificationResponses = pushNotificationResponses;
    }

    @Override
    public void run() {

        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            StringEntity postingString = new StringEntity(new Gson().toJson(pushNotificationResponses));
            HttpPost post = new HttpPost(Configuration.WEB_NOTIFY_URL);
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            post.setHeader("Authorization", "key=" + notificationKey);
            HttpResponse response = httpClient.execute(post);
            cod.c().addShowToString(Enum.class).i("WEB NOTIFY ", pushNotificationResponses, response.getStatusLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}