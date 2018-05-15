package com.agh.givealift.service.implementation;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.response.SubscriptionResponse;
import com.agh.givealift.service.NotificationService;
import com.agh.givealift.service.threads.NotifyBotThread;
import com.google.gson.Gson;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final COD cod = CODFactory.get();
    private final ApplicationContext applicationContext;
    private final TaskExecutor taskExecutor;

    @Autowired
    public NotificationServiceImpl(ApplicationContext applicationContext, TaskExecutor taskExecutor) {
        this.applicationContext = applicationContext;
        this.taskExecutor = taskExecutor;
    }


    @Override
    public void notifyBot(List<SubscriptionResponse> subscriptionResponseList) {
        if (!subscriptionResponseList.isEmpty()) {
            taskExecutor.execute(() -> {
                NotifyBotThread notifyBotThread = applicationContext.getBean(NotifyBotThread.class);
                notifyBotThread.setSubscriptionResponseList(subscriptionResponseList);
                taskExecutor.execute(notifyBotThread);
            });
        }
    }
}
