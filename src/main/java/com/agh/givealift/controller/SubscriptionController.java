package com.agh.givealift.controller;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.entity.Subscription;
import com.agh.givealift.model.request.SubscriptionRequest;
import com.agh.givealift.model.response.RouteResponse;
import com.agh.givealift.model.response.SubscriptionResponse;
import com.agh.givealift.service.RouteService;
import com.agh.givealift.service.SubscriptionService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import com.sun.mail.util.logging.CollectorFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private static final COD cod = CODFactory.get();
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody SubscriptionRequest subscriptionRequest, UriComponentsBuilder ucBuilder) {

        return subscriptionService.add(subscriptionRequest)
                .map(
                        s -> {
                            HttpHeaders headers = new HttpHeaders();
                            headers.setLocation(ucBuilder.path("/api/subscription/{id}").buildAndExpand(s.getSubscriptionId()).toUri());
                            return new ResponseEntity<>(headers, HttpStatus.CREATED);
                        }
                )
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscription(){
        List<SubscriptionResponse> response =  subscriptionService.getAll()
                .stream().map(subscriptionService::mapToSubscriptionRseponse).collect(Collectors.toList());
          return new ResponseEntity<>(response,HttpStatus.OK);               
    }
    
    
     @DeleteMapping("/{id}")
    public ResponseEntity<?> getAllSubscription(@RequestParam("email") String email){
                Optional<Long> subscription = Optional.ofNullable(subscriptionService.delete(email));
              if(!subscription.isPresent()) return new ResponseEntity<>("Uzytkownik nie istnieje",HttpStatus.BAD_REQUEST);
              return new ResponseEntity<>(subscription.get(),HttpStatus.OK);      
        
        
    }


}
