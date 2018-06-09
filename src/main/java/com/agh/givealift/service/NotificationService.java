package com.agh.givealift.service;

import com.agh.givealift.model.response.PushNotificationResponse;
import com.agh.givealift.model.response.PushNotificationResponses;
import com.agh.givealift.model.response.SubscriptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
     void notifyBot(List<SubscriptionResponse> check);

     void notifyWeb(List<PushNotificationResponse> collect);

     void notifyMobile(List<PushNotificationResponse> collect);

     void notifyWeb2(PushNotificationResponses result);
}
