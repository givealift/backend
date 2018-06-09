package com.agh.givealift.service.init;

import com.agh.givealift.configuration.Configuration;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.service.CityService;
import com.agh.givealift.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
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
        Long uIdBG = signUpBG();
        Long uIdMW = signUpMW();
        Long uIdKS = signUpKS();
        Long uIdDS = signUpDS();
        Long uIdPZ = signUpPZ();
        Long uIdJO = signUpJO();
        try {
            generateRoute01(uId1, cities);
            generateRoute02(uId2, cities);
            generateRoute03(uId2, cities);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private Long signUp01() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("user1@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("Jan");
        signUp.setLastName("Kowalski");
        signUp.setGender("male");
        signUp.setBirthYear(new GregorianCalendar(1997, 9, 15, 1, 10, 16).getTime());
        signUp.setRate(0D);
        signUp.setPhone("365198320");
        return userServiceInit.signUp(signUp);
    }

    private Long signUp02() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("user2@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("Anna");
        signUp.setLastName("Mickiewicz");
        signUp.setGender("female");
        signUp.setBirthYear(new GregorianCalendar(1996, 1, 28, 13, 24, 56).getTime());
        signUp.setRate(0D);
        signUp.setPhone("687998564");
        return userServiceInit.signUp(signUp);
    }

    private Long signUpMW() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("mw@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("Marcin");
        signUp.setLastName("Wloczko");
        signUp.setGender("male");
        signUp.setBirthYear(new GregorianCalendar(1996, 1, 28, 13, 24, 56).getTime());
        signUp.setRate(5D);
        signUp.setPhone("123456789");
        return userServiceInit.signUp(signUp);
    }

    private Long signUpBG() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("bg@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("Bartek");
        signUp.setLastName("Grodek");
        signUp.setGender("male");
        signUp.setBirthYear(new GregorianCalendar(1996, 1, 28, 13, 24, 56).getTime());
        signUp.setRate(5D);
        signUp.setPhone("234567890");
        return userServiceInit.signUp(signUp);
    }

    private Long signUpKS() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("ks@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("Krzysztof");
        signUp.setLastName("Szczyrbak");
        signUp.setGender("male");
        signUp.setBirthYear(new GregorianCalendar(1996, 1, 28, 13, 24, 56).getTime());
        signUp.setRate(4D);
        signUp.setPhone("345678901");
        return userServiceInit.signUp(signUp);
    }

    private Long signUpDS() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("ds@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("Daniel");
        signUp.setLastName("Stefanik");
        signUp.setGender("male");
        signUp.setBirthYear(new GregorianCalendar(1996, 1, 28, 13, 24, 56).getTime());
        signUp.setRate(4D);
        signUp.setPhone("456789012");
        return userServiceInit.signUp(signUp);
    }

    private Long signUpPZ() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("pz@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("Patryk");
        signUp.setLastName("Zygmunt");
        signUp.setGender("male");
        signUp.setBirthYear(new GregorianCalendar(1996, 1, 28, 13, 24, 56).getTime());
        signUp.setRate(4D);
        signUp.setPhone("567890123");
        return userServiceInit.signUp(signUp);
    }

    private Long signUpJO() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("jo@gmail.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("Jacek");
        signUp.setLastName("Oles");
        signUp.setGender("male");
        signUp.setBirthYear(new GregorianCalendar(1996, 1, 28, 13, 24, 56).getTime());
        signUp.setRate(5D);
        signUp.setPhone("678901234");
        return userServiceInit.signUp(signUp);
    }

    private void generateRoute01(Long ownerId, List<City> cities) throws ParseException {

        Route r = new Route();
        r.setRouteId(1L);
        r.setNumberOfSeats(4);
        r.setNumberOfOccupiedSeats(0);
        r.setOwnerId(ownerId);
        r.setPrice(10.0);
        r.setDescription("Jade z Warszway do Wrocka, przez Krk i Łódź");

        Localization from = new Localization();
        from.setCity(cities.get(0));
        from.setPlaceOfMeeting("Pałac Kultury i Nauki");
        from.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(Configuration.HOURS_DIFFERENCE))));
        r.setFrom(from);

        Localization s1 = new Localization();
        s1.setCity(cities.get(1));
        s1.setPlaceOfMeeting("Sukiennice");
        s1.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(1 + Configuration.HOURS_DIFFERENCE))));

        Localization s2 = new Localization();
        s2.setCity(cities.get(2));
        s2.setPlaceOfMeeting("łódź fabryczna");
        s2.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(3 + Configuration.HOURS_DIFFERENCE))));

        r.setStops(Arrays.asList(s1, s2));

        Localization to = new Localization();
        to.setCity(cities.get(3));
        to.setPlaceOfMeeting("Planetarium");
        to.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(4 + Configuration.HOURS_DIFFERENCE))));
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
        r.setDescription("Bydgoszcz - Lublin");

        Localization from = new Localization();
        from.setCity(cities.get(7));
        from.setPlaceOfMeeting("Poniatowskiego 12");
        from.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(21 + Configuration.HOURS_DIFFERENCE))));
        r.setFrom(from);

        r.setStops(Collections.emptyList());

        Localization to = new Localization();
        to.setCity(cities.get(8));
        to.setPlaceOfMeeting("uniwersytet");
        to.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(25 + Configuration.HOURS_DIFFERENCE))));
        r.setTo(to);


        routeService.add(r);
    }


    private void generateRoute03(Long ownerId, List<City> cities) throws ParseException {

        Route r = new Route();
        r.setRouteId(2L);
        r.setNumberOfSeats(3);
        r.setNumberOfOccupiedSeats(0);
        r.setOwnerId(ownerId);
        r.setPrice(16.0);
        r.setDescription("Nie mam klimy, ale i tak będzie fajnie");

        Localization from = new Localization();
        from.setCity(cities.get(11));
        from.setPlaceOfMeeting("Plaża");
        from.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(2 + Configuration.HOURS_DIFFERENCE))));
        r.setFrom(from);


        Localization s1 = new Localization();
        s1.setCity(cities.get(2));
        s1.setPlaceOfMeeting("łódź fabryczna");
        s1.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(4 + Configuration.HOURS_DIFFERENCE))));

        r.setStops(Arrays.asList(s1));

        Localization to = new Localization();
        to.setCity(cities.get(8));
        to.setPlaceOfMeeting("KUL");
        to.setDate(Date.from(new Date().toInstant().plus(Duration.ofHours(7 + Configuration.HOURS_DIFFERENCE))));
        r.setTo(to);


        routeService.add(r);
    }
}
