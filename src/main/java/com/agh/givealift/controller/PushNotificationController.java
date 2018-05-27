package com.agh.givealift.controller;


import com.agh.givealift.model.request.PushNotificationRequest;
import com.agh.givealift.model.request.SubscriptionRequest;
import com.agh.givealift.service.PushNotificationService;
import com.agh.givealift.service.SubscriptionService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/api/notification")
public class PushNotificationController {
    private static final COD cod = CODFactory.get();
    private final PushNotificationService pushNotificationService;

    @Autowired
    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Long> add(@RequestBody PushNotificationRequest pushNotificationRequest, UriComponentsBuilder ucBuilder) {

        return pushNotificationService.add(pushNotificationRequest)
                .map(
                        s -> {
                            HttpHeaders headers = new HttpHeaders();
                            headers.setLocation(ucBuilder.path("/api/notification/{id}").buildAndExpand(s.getPushNotificationId()).toUri());
                            return ResponseEntity.ok().headers(headers).body(s.getPushNotificationId());
                        }
                )
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
