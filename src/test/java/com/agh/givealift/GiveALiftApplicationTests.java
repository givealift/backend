package com.agh.givealift;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GiveALiftApplicationTests {

	@Test
	public void travisTest01() {
		assertThat(true).isEqualTo(true);//test test...
	}

    @Test
    public void travisTest02() {
        assertThat(true).isEqualTo(false);//test test...
    }

}
