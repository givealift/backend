package com.agh.givealift.service.implementation;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.response.SubscriptionResponse;
import com.agh.givealift.service.NotificationService;
import com.google.gson.Gson;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final COD cod = CODFactory.get();

    @Override
    public void notifyBot(List<SubscriptionResponse> check) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            StringEntity postingString = new StringEntity(new Gson().toJson(check));
            HttpPost post = new HttpPost(Configuration.BOT_NOTIFY_URL);
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);
            cod.c().off().i(response.getStatusLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
