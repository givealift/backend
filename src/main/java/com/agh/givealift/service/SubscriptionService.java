package com.agh.givealift.service;


import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.entity.Subscription;
import com.agh.givealift.model.request.SubscriptionRequest;
import com.agh.givealift.model.response.SubscriptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubscriptionService {

    Optional<Subscription> add(SubscriptionRequest subscriptionRequest);

    void checkAndNotify(Route route);

    List<Subscription> getAll();
    SubscriptionResponse mapToSubscriptionRseponse(Subscription subscription) ;


    Long delete(long id);
}
