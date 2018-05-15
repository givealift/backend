package com.agh.givealift.service.init;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.service.CityService;
import com.agh.givealift.service.RouteService;
import com.agh.givealift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Service
public class Init {
    private final CityService cityService;
    private final RouteService routeService;
    private final UserServiceInit userServiceInit;


    @Autowired
    public Init(CityService cityService, RouteService routeService, UserServiceInit userServiceInit) {
        this.cityService = cityService;
        this.routeService = routeService;
        this.userServiceInit = userServiceInit;
    }


    @Transactional
    @PostConstruct
    public void init() {

        List<City> cities = cityService.generate(100);
        Long uId1 = signUp01();
        Long uId2 = signUp02();
        try {
            generateRoute01(uId1, cities);
            generateRoute02(uId2, cities);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private Long signUp01() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("user1@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("name");
        signUp.setLastName("last");
        signUp.setGender("male");
        signUp.setPhone("666666666");
        return userServiceInit.signUp(signUp);
    }

    private Long signUp02() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("user2@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("name");
        signUp.setLastName("last");
        signUp.setGender("female");
        signUp.setPhone("999999999");
        return userServiceInit.signUp(signUp);
    }

    private void generateRoute01(Long ownerId, List<City> cities) throws ParseException {

        Route r = new Route();
        r.setRouteId(1L);
        r.setNumberOfSeats(4);
        r.setNumberOfOccupiedSeats(0);
        r.setOwnerId(ownerId);
        r.setPrice(10.0);
        r.setDescription("desc 1");

        Localization from = new Localization();
        from.setCity(cities.get(0));
        from.setPlaceOfMeeting("s1 1");
        from.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(Configuration.HOURS_DIFFERENCE))));
        r.setFrom(from);

        Localization s1 = new Localization();
        s1.setCity(cities.get(1));
        s1.setPlaceOfMeeting("s2 2");
        s1.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(1+Configuration.HOURS_DIFFERENCE))));

        Localization s2 = new Localization();
        s2.setCity(cities.get(2));
        s2.setPlaceOfMeeting("s3 3");
        s2.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(3+Configuration.HOURS_DIFFERENCE))));

        r.setStops(Arrays.asList(s1, s2));

        Localization to = new Localization();
        to.setCity(cities.get(3));
        to.setPlaceOfMeeting("s4 4");
        to.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(4+Configuration.HOURS_DIFFERENCE))));
        r.setTo(to);


        routeService.add(r);
    }

    private void generateRoute02(Long ownerId, List<City> cities) throws ParseException {

        Route r = new Route();
        r.setRouteId(2L);
        r.setNumberOfSeats(3);
        r.setNumberOfOccupiedSeats(0);
        r.setOwnerId(ownerId);
        r.setPrice(16.0);
        r.setDescription("desc 2");

        Localization from = new Localization();
        from.setCity(cities.get(7));
        from.setPlaceOfMeeting("s2 2");
        from.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(21+Configuration.HOURS_DIFFERENCE))));
        r.setFrom(from);

        r.setStops(Collections.emptyList());

        Localization to = new Localization();
        to.setCity(cities.get(8));
        to.setPlaceOfMeeting("s4 4");
        to.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(25+Configuration.HOURS_DIFFERENCE))));
        r.setTo(to);


        routeService.add(r);
    }
}
