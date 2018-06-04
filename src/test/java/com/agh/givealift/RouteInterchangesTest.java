package com.agh.givealift;

import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.request.LoginUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.model.response.RouteResponse;
import com.agh.givealift.security.UserDetails;
import com.agh.givealift.service.CityService;
import com.agh.givealift.service.RouteService;
import com.agh.givealift.service.UserService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import com.stefanik.cod.controller.CODGlobal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteInterchangesTest {
    private static final COD cod = CODFactory.c().get();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private static Long cFromId;
    private static List<Long> cStopsId;
    private static Long cToId;

    private static Long cFromId02;
    private static List<Long> cStopsId02;
    private static Long cToId02;

    @Autowired
    private RouteService routeService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @After
    public void after() throws ParseException {

        SecurityContextHolder.clearContext();
    }

    @Before
    public void before() throws ParseException {


        List<GrantedAuthority> ananymous = new ArrayList<GrantedAuthority>() {{
            add(new SimpleGrantedAuthority("ANONYMOUS"));
        }};
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("test", new UserDetails(), ananymous));
        cleanDB();
        SignUpUserRequest userNew = new SignUpUserRequest();
        userNew.setEmail("patryk2@gm.pl");
        userNew.setPassword("admin");
        userService.signUp(userNew);

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "patryk2@gm.pl", "admin"
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final GalUser user = userService.getUserByUsername("patryk2@gm.pl").get();


        generateRoute(user.getGalUserId());


    }

    private void generateRoute(long id) throws ParseException {
        cStopsId = new ArrayList<>();
        cStopsId02 = new ArrayList<>();
        List<City> cities = cityService.generate(100);

        Route r = new Route();
        r.setNumberOfSeats(5);
        r.setNumberOfOccupiedSeats(2);
        r.setOwnerId(id);
        r.setPrice(10.0);

        Localization from = new Localization();
        from.setCity(cities.get(0));
        cFromId = cities.get(0).getCityId();
        from.setPlaceOfMeeting("s1 1");
        from.setDate(dateFormat.parse("08.05.2018 21:15:00"));
        r.setFrom(from);


        Localization s1 = new Localization();
        s1.setCity(cities.get(1));
        cStopsId.add(cities.get(1).getCityId());
        s1.setPlaceOfMeeting("s2 2");
        s1.setDate(dateFormat.parse("08.05.2018 22:45:00"));

        Localization s2 = new Localization();
        s2.setCity(cities.get(2));
        cStopsId.add(cities.get(2).getCityId());
        s2.setPlaceOfMeeting("s3 3");
        s2.setDate(dateFormat.parse("08.05.2018 22:55:00"));

        r.setStops(Arrays.asList(s1, s2));

        Localization to = new Localization();
        to.setCity(cities.get(3));
        cToId = cities.get(3).getCityId();
        to.setPlaceOfMeeting("s4 4");
        to.setDate(dateFormat.parse("08.05.2018 23:15:00"));
        r.setTo(to);

        routeService.add(r);


        Route r02 = new Route();
        r02.setNumberOfSeats(5);
        r02.setNumberOfOccupiedSeats(2);
        r02.setOwnerId(id);
        r02.setPrice(10.0);

        Localization from02 = new Localization();
        from02.setCity(cities.get(6));
        cFromId02 = cities.get(6).getCityId();
        from02.setPlaceOfMeeting("s1 2");
        from02.setDate(dateFormat.parse("08.05.2018 21:15:00"));
        r02.setFrom(from02);


        Localization s1r02 = new Localization();
        s1r02.setCity(cities.get(2));
        cStopsId02.add(cities.get(2).getCityId());
        s1r02.setPlaceOfMeeting("s1r02 2");
        s1r02.setDate(dateFormat.parse("08.05.2018 23:10:00"));


        Localization s2r02 = new Localization();
        s2r02.setCity(cities.get(9));
        cStopsId02.add(cities.get(9).getCityId());
        s2r02.setPlaceOfMeeting("s1r02 2");
        s2r02.setDate(dateFormat.parse("08.05.2018 23:20:00"));




        r02.setStops(Arrays.asList(s1r02,s2r02));

        Localization to02 = new Localization();
        to02.setCity(cities.get(8));
        cToId02 = cities.get(8).getCityId();
        to02.setPlaceOfMeeting("s4 4");
        to02.setDate(dateFormat.parse("08.05.2018 23:30:00"));
        r02.setTo(to02);



        routeService.add(r02);



    }

    private void cleanDB() {
        // cityService.removeAll();
        routeService.removeAll();
        userService.removeAll();
    }

    @Test
    public void routeSearchFromToTest01() throws ParseException {
//        CODGlobal.setImmersionLevel(5);

        List<List<RouteResponse>> result = routeService.searchInterchanges(
                cFromId,
                cToId02,
                dateFormat.parse("08.05.2018 21:15:00")
        );
        cod.c().off().i("SEARCH", result);
        assertThat(result).hasSize(1);
    }



}
