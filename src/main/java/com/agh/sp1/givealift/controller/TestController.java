package com.agh.sp1.givealift.controller;

import com.agh.sp1.givealift.model.entity.TestEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;


@Controller
//@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public ResponseEntity<List<TestEntity>> getTest() {
        System.out.println("test");
        TestEntity t1 = new TestEntity();
        TestEntity t2 = new TestEntity();
        TestEntity t3 = new TestEntity();
        return new ResponseEntity<>(Arrays.asList(t1,t2,t3), HttpStatus.OK);
    }

}
