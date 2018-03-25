package com.agh.givealift.service;


import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.CityInfo;
import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.Registration;
import com.agh.givealift.repository.CityRepository;
import com.agh.givealift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<GalUser> list() {
        return userRepository.findAll();
    }

    public void register(Registration registration) {
    }
}
