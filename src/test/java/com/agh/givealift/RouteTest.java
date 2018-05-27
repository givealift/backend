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
public class RouteTest {
    private static final COD cod = CODFactory.c().get();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static boolean init = false;
    private LoginUser loginUser;

    private static Long cFromId;
    private static List<Long> cStopsId;
    private static Long cToId;

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
        userNew.setEmail("patryk@gm.pl");
        userNew.setPassword("admin");
        userService.signUp(userNew);

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "patryk@gm.pl", "admin"
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final GalUser user = userService.getUserByUsername("patryk@gm.pl").get();


        generateRoute(user.getGalUserId());


    }

    private void generateRoute(long id) throws ParseException {
        cStopsId = new ArrayList<>();
        List<City> cities = cityService.generate(100);

        cod.i(cities);

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
    }

    private void cleanDB() {
        // cityService.removeAll();
        routeService.removeAll();
        userService.removeAll();
    }

    private void signUp() {
        SignUpUserRequest signUp = new SignUpUserRequest();
        signUp.setEmail("a@a.pl");
        signUp.setPassword("admin");
        signUp.setFirstName("admin");
        signUp.setLastName("admin");
        signUp.setGender("admin");
        signUp.setPhone("admin");
        userService.signUp(signUp);
    }

    //    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    @Test
    public void routeSearchFromToTest01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cFromId,
                cToId,
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().off().i("SEARCH", result);
        assertThat(result).hasSize(1);
    }


    @Test
    public void routeSearchFromStopTest01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cFromId,
                cStopsId.get(0),
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(1);
    }

    @Test
    public void routeSearchFromStopTest02() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cFromId,
                cStopsId.get(1),
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(1);
    }

    @Test
    public void routeSearchStopToTest01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cStopsId.get(0),
                cToId,
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(1);
    }

    @Test
    public void routeSearchStopToTest02() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cStopsId.get(1),
                cToId,
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(1);
    }

    @Test
    public void routeSearchStopStopTest01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cStopsId.get(0),
                cStopsId.get(1),
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(1);
    }

    @Test
    public void routeSearchToFromReverse01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cToId,
                cFromId,
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(0);
    }

    @Test
    public void routeSearchToStopReverse01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cToId,
                cStopsId.get(0),
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(0);
    }

    @Test
    public void routeSearchToStopReverse02() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cToId,
                cStopsId.get(1),
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(0);
    }

    @Test
    public void routeSearchStopFromReverse01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cStopsId.get(1),
                cFromId,
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(0);
    }

    @Test
    public void routeSearchStopFromReverse02() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cStopsId.get(0),
                cFromId,
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(0);
    }

    @Test
    public void routeSearchStopStopReverse01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<RouteResponse> result = routeService.search(
                cStopsId.get(1),
                cStopsId.get(0),
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(0);
    }

}
