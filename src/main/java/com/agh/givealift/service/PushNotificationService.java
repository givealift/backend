package com.agh.givealift.service;


import com.agh.givealift.model.entity.PushNotification;
import com.agh.givealift.model.request.PushNotificationRequest;
import com.agh.givealift.model.response.PushNotificationResponse;
import com.agh.givealift.model.response.PushNotificationResponses;
import com.agh.givealift.model.response.SubscriptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PushNotificationService {

    Optional<PushNotification> add(PushNotificationRequest pushNotificationRequest);

    List<PushNotificationResponse> findNotification(List<SubscriptionResponse> collect);

    PushNotificationResponses findNotification2(List<SubscriptionResponse> collect);
}
