package com.agh.givealift;

import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.service.CityService;
import com.agh.givealift.service.RouteService;
import com.agh.givealift.service.UserService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import com.stefanik.cod.controller.CODGlobal;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteTest {
    private static final COD cod = CODFactory.c().get();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static boolean init = false;


    private static Long cFromId;
    private static List<Long> cStopsId;
    private static Long cToId;

    @Autowired
    RouteService routeService;

    @Autowired
    CityService cityService;

    @Autowired
    UserService userService;


    @Before
    public void before() throws ParseException {

        if (!init) {
            cStopsId = new ArrayList<>();
            cityService.removeAll();
            routeService.removeAll();
            userService.removeAll();
            List<City> cities = cityService.generate(100);

            cod.i(cities);

            Route r = new Route();
            r.setNumberOfSeats(5);
            r.setNumberOfOccupiedSeats(2);
            r.setOwnerId(1L);
            r.setPrice(10.0);

            Localization from = new Localization();
            from.setCity(cities.get(0));
            cFromId = cities.get(0).getCityId();
            from.setStreet("s1");
            from.setBuildingNumber(1);
            from.setDate(dateFormat.parse("08.05.2018 21:15:00"));
            r.setFrom(from);


            Localization s1 = new Localization();
            s1.setCity(cities.get(1));
            cStopsId.add(cities.get(1).getCityId());
            s1.setStreet("s2");
            s1.setBuildingNumber(2);
            s1.setDate(dateFormat.parse("08.05.2018 22:45:00"));

            Localization s2 = new Localization();
            s2.setCity(cities.get(2));
            cStopsId.add(cities.get(2).getCityId());
            s2.setStreet("s3");
            s2.setBuildingNumber(3);
            s2.setDate(dateFormat.parse("08.05.2018 22:55:00"));

            r.setStops(Arrays.asList(s1, s2));

            Localization to = new Localization();
            to.setCity(cities.get(3));
            cToId = cities.get(3).getCityId();
            to.setStreet("s4");
            to.setBuildingNumber(4);
            to.setDate(dateFormat.parse("08.05.2018 23:15:00"));
            r.setTo(to);


            routeService.add(r);
            init = true;
        }
    }

    //    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    @Test
    public void routeSearchFromToTest01() throws ParseException {
        CODGlobal.setImmersionLevel(5);
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
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
        List<Route> result = routeService.search(
                cStopsId.get(1),
                cStopsId.get(0),
                dateFormat.parse("08.05.2018 05:00:00")
        );
        cod.c().i("SEARCH", result);
        assertThat(result).hasSize(0);
    }

}
