package com.agh.givealift.service.implementation;

import com.agh.givealift.model.entity.*;
import com.agh.givealift.model.enums.DeviceType;
import com.agh.givealift.model.request.PushNotificationRequest;
import com.agh.givealift.model.response.PushNotificationResponse;
import com.agh.givealift.model.response.PushNotificationResponses;
import com.agh.givealift.model.response.PushSubscriptionResponse;
import com.agh.givealift.model.response.SubscriptionResponse;
import com.agh.givealift.repository.PushNotificationRepository;
import com.agh.givealift.service.PushNotificationService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import com.stefanik.cod.controller.CODGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {
    private static final COD cod = CODFactory.get();
    private final PushNotificationRepository pushNotificationRepository;

    @Autowired
    public PushNotificationServiceImpl(
            PushNotificationRepository pushNotificationRepository
    ) {
        this.pushNotificationRepository = pushNotificationRepository;
        CODGlobal.setImmersionLevel(4);
    }

    @Override
    public Optional<PushNotification> add(PushNotificationRequest pushNotificationRequest) {

        List<PushNotification> result = pushNotificationRepository.findByPushTokenAndUserId(pushNotificationRequest.getPushToken(), pushNotificationRequest.getUserId());
        if (result.size() == 0) {
            PushNotification pushNotification = new PushNotification();

            pushNotification.setUserId(pushNotificationRequest.getUserId());
            pushNotification.setDeviceType(pushNotificationRequest.getDeviceType());
            pushNotification.setPushToken(pushNotificationRequest.getPushToken());


            pushNotification = pushNotificationRepository.save(pushNotification);
            cod.c().addShowToString(DeviceType.class).i("ADDED PUSH NOTIFICATION: ", pushNotification);
            return Optional.of(pushNotification);
        }
        return Optional.empty();
    }

    @Override
    public List<PushNotificationResponse> findNotification(List<SubscriptionResponse> subscriptionResponses) {

        List<PushNotificationResponse> pushNotificationResponses = new ArrayList<>();
        for (SubscriptionResponse sr : subscriptionResponses) {
            for (PushNotification pn : pushNotificationRepository.find(Long.parseLong(sr.getSubscriber()))) {
                PushNotificationResponse pnr = new PushNotificationResponse();
                pnr.setPushSubscriptionResponse(getPushSubscriptionResponse(sr));
                pnr.setToken(pn.getPushToken());
                pnr.setDeviceType(pn.getDeviceType());
                pushNotificationResponses.add(pnr);
            }
        }
        cod.i("FOUND PUSH NOTIFICATION RESPONSES: ", pushNotificationResponses);

        return pushNotificationResponses;
    }

    @Override
    public PushNotificationResponses findNotification2(List<SubscriptionResponse> subscriptionResponses) {
        if (subscriptionResponses.size() > 0) {
            ArrayList<String> l = new ArrayList<>();
            for (PushNotification pn : pushNotificationRepository.find(Long.parseLong(subscriptionResponses.get(0).getSubscriber()))) {
                l.add(pn.getPushToken());
            }
            PushNotificationResponses pnr = new PushNotificationResponses();
            pnr.setData(getPushSubscriptionResponse(subscriptionResponses.get(0)));
            pnr.setRegistration_ids(l);
            cod.i("FOUND PUSH NOTIFICATION RESPONSES2: ", pnr);
            return pnr;
        }
        return null;
    }

    private PushSubscriptionResponse getPushSubscriptionResponse(SubscriptionResponse sr) {
        PushSubscriptionResponse psr = new PushSubscriptionResponse();
        psr.setFromCityId(sr.getFrom().getCityId());
        psr.setToCityId(sr.getTo().getCityId());
        psr.setFromCityName(sr.getFrom().getName());
        psr.setToCityName(sr.getTo().getName());
        psr.setDate(sr.getDate());
        psr.setRouteId(sr.getRouteId());
        psr.setUserId(sr.getUserId());
        return psr;
    }

}
