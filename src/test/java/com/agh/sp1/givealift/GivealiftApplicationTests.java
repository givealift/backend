package com.agh.sp1.givealift;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GivealiftApplicationTests {

	@Test
	public void contextLoads() {
		assertThat(true).isEqualTo(true);//test test...
	}

}
