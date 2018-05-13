package com.agh.givealift.repository;

import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    @Query("select s from Subscription s WHERE s.from.cityId = :from AND s.to.cityId in (:to) AND (s.date is null OR s.date = :date)")
    List<Subscription> findSubscriptions(
            @Param(value = "from") Long from,
            @Param(value = "to") List<Long> to,
            @Param(value = "date") Date date
    );

}
