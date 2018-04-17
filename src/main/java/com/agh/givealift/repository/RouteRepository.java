package com.agh.givealift.repository;

import com.agh.givealift.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {
    List<Route> findByDepartureTimeBetweenAndFromCityCityIdAndToCityCityId(Date dateStart, Date dateEnd, Long from, Long to);

    Route findByRouteId(Long id);

}
