package com.agh.givealift.service.implementation;

import com.agh.givealift.configuration.Configuration;
import com.agh.givealift.model.Tuple;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.entity.Subscription;
import com.agh.givealift.model.enums.DeviceType;
import com.agh.givealift.model.enums.NotificationType;
import com.agh.givealift.model.request.SubscriptionRequest;
import com.agh.givealift.model.response.PushNotificationResponse;
import com.agh.givealift.model.response.PushNotificationResponses;
import com.agh.givealift.model.response.SubscriptionResponse;
import com.agh.givealift.repository.SubscriptionRepository;
import com.agh.givealift.service.CityService;
import com.agh.givealift.service.NotificationService;
import com.agh.givealift.service.PushNotificationService;
import com.agh.givealift.service.SubscriptionService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import com.stefanik.cod.controller.CODGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private static final COD cod = CODFactory.get();
    private final SubscriptionRepository subscriptionRepository;
    private final CityService cityService;
    private final NotificationService notificationService;
    private final PushNotificationService pushNotificationService;

    @Autowired
    public SubscriptionServiceImpl(
            SubscriptionRepository subscriptionRepository,
            CityService cityService,
            NotificationService notificationService,
            PushNotificationService pushNotificationService
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.cityService = cityService;
        this.notificationService = notificationService;
        this.pushNotificationService = pushNotificationService;
        CODGlobal.setImmersionLevel(4);
    }

    @Override
    public Optional<Subscription> add(SubscriptionRequest subscriptionRequest) {
        Optional<City> fromCity = cityService.get(subscriptionRequest.getFromCityId());
        Optional<City> toCity = cityService.get(subscriptionRequest.getToCityId());

        if (fromCity.isPresent() && toCity.isPresent()) {
            Subscription subscription = new Subscription();
            subscription.setSubscriber(subscriptionRequest.getSubscriber());
            subscription.setEmail(subscriptionRequest.getSubscriber());
            subscription.setDate(subscriptionRequest.getDate());
            subscription.setNotificationType(subscriptionRequest.getNotificationType());
            subscription.setFrom(fromCity.get());
            subscription.setTo(toCity.get());

            subscription = subscriptionRepository.save(subscription);
            cod.c().addShowToString(NotificationType.class, DeviceType.class).i("ADDED SUBSCRIPTION: ", subscription);
            return Optional.of(subscription);
        }
        return Optional.empty();
    }

    @Override
    public void checkAndNotify(Route route) {
        try {
            List<SubscriptionResponse> all = check(route);

            cod.c().addShowToString(NotificationType.class, DeviceType.class).i(all);
            notificationService.notifyBot(all.stream()
                    .filter(s -> s.getNotificationType().equals(NotificationType.BOT))
                    .collect(Collectors.toList())
            );

            List<PushNotificationResponse> pushNotificationResponses = pushNotificationService.findNotification(
                    all.stream()
                            .filter(s -> s.getNotificationType().equals(NotificationType.PUSH))
                            .collect(Collectors.toList())
            );
            PushNotificationResponses result = pushNotificationService.findNotification2(
                    all.stream()
                            .filter(s -> s.getNotificationType().equals(NotificationType.PUSH))
                            .collect(Collectors.toList())
            );
            notificationService.notifyWeb2(result);

            notificationService.notifyWeb(pushNotificationResponses.stream()
                    .filter(pnr -> pnr.getDeviceType().equals(DeviceType.WEB))
                    .collect(Collectors.toList())
            );

            notificationService.notifyMobile(pushNotificationResponses.stream()
                    .filter(pnr -> pnr.getDeviceType().equals(DeviceType.MOBILE))
                    .collect(Collectors.toList())
            );


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    private List<SubscriptionResponse> check(Route route) throws ParseException {

        ArrayList<Subscription> result = new ArrayList<>();
        for (Tuple<Localization, List<Long>> t : allRoutes(route)) {
            Date date = t.getLeft().getDate();

            //Convert to data without time
            LocalDate lDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date cDate = Date.from(Instant.from(lDate.atStartOfDay(ZoneId.systemDefault())));


            Long l = t.getLeft().getCity().getCityId();
            List<Long> r = t.getRight();
            List<Subscription> subs = subscriptionRepository.findSubscriptions(l, r, cDate);
            cod.i(" | l:" + l +
                            " | r: " + r +
                            " | date: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(date) +
                            " | lDate: " + lDate +
                            " | cDate: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(cDate),
                    subs
            );
            result.addAll(subs);

        }
        return result.stream().map(r ->
                {
                    SubscriptionResponse response = new SubscriptionResponse();
                    response.setSubscriber(r.getSubscriber());
                    response.setEmail(r.getEmail());
                    response.setDate(r.getDate());
                    response.setFrom(route.getFrom().getCity());
                    response.setTo(route.getTo().getCity());
                    response.setRouteId(route.getRouteId());
                    response.setNotificationType(r.getNotificationType());
                    return response;
                }
        ).collect(Collectors.toList());
    }

    private List<Tuple<Localization, List<Long>>> allRoutes(Route route) {

        List<Tuple<Localization, List<Long>>> result = new ArrayList<>();

        List<Long> to = route.getStops().stream().map(r -> r.getCity().getCityId()).collect(Collectors.toList());
        to.add(route.getTo().getCity().getCityId());
        result.add(new Tuple<>(route.getFrom(), to));

        List<Localization> ids = route.getStops();
        for (int i = 0; i < ids.size(); i++) {
            to = new ArrayList<>();
            for (int j = i + 1; j < ids.size(); j++) {
                to.add(ids.get(j).getCity().getCityId());
            }
            to.add(route.getTo().getCity().getCityId());
            result.add(new Tuple<>(ids.get(i), to));
        }
        return result;

    }

    public SubscriptionResponse mapToSubscriptionResponse(Subscription subscription) {
        SubscriptionResponse response = new SubscriptionResponse();
        response.setSubscriptionId(subscription.getSubscriptionId());
        response.setSubscriber(subscription.getSubscriber());
        response.setEmail(subscription.getEmail());
        response.setDate(subscription.getDate());
        response.setFrom(subscription.getFrom());
        response.setNotificationType(subscription.getNotificationType());
        response.setTo(subscription.getTo());
        return response;


    }

    @Override
    public Long delete(long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (subscription.isPresent()) {
            subscriptionRepository.deleteById(id);
            return subscription.get().getSubscriptionId();
        }
        return null;


    }

}
