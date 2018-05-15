package com.agh.givealift.controller;

import com.agh.givealift.model.request.SubscriptionRequest;
import com.agh.givealift.model.response.SubscriptionResponse;
import com.agh.givealift.service.SubscriptionService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<Long> add(@RequestBody SubscriptionRequest subscriptionRequest, UriComponentsBuilder ucBuilder) {

        return subscriptionService.add(subscriptionRequest)
                .map(
                        s -> {
                            HttpHeaders headers = new HttpHeaders();
                            headers.setLocation(ucBuilder.path("/api/subscription/{id}").buildAndExpand(s.getSubscriptionId()).toUri());
                            return ResponseEntity.ok().headers(headers).body(s.getSubscriptionId());
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
     public ResponseEntity<?> getAllSubscription(@PathVariable("id") long id) {
         Optional<Long> subscription = Optional.ofNullable(subscriptionService.delete(id));
              if(!subscription.isPresent()) return new ResponseEntity<>("Uzytkownik nie istnieje",HttpStatus.BAD_REQUEST);
              return new ResponseEntity<>(subscription.get(),HttpStatus.OK);      
        
        
    }


}
