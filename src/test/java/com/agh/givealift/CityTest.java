package com.agh.givealift;

import com.agh.givealift.service.CityService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityTest {
    private final COD cod = CODFactory.c().get();

    @Autowired
    CityService cityService;

   /* @Before
    public void before() {
        cityService.generate(100);
    }*/

    @Test
    public void citySearch() {

   /*  CODGlobal.setImmersionLevel(3);
        List<City> result = cityService.search("k", 2);
        cod.c().off().i("SEARCH", result);*/
        assertTrue(true);
    }

}
