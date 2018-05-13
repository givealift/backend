package com.agh.givealift.service;

import com.agh.givealift.model.response.SubscriptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
     void notifyBot(List<SubscriptionResponse> check);
}
