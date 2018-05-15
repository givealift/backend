package com.agh.givealift.repository;

import com.agh.givealift.model.entity.Route;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {

    @Query(
            "select distinct r from Route r "
                    + "left join r.from f "
                    + "left join r.stops s1 "
                    + "left join r.stops s2 "
                    + "left join r.to t "
                    + "WHERE " +
                    "(f.date between :dateStart AND :dateEnd AND f.city.cityId = :cityFrom AND t.city.cityId = :cityTo) " +
                    "OR (s1.date between :dateStart AND :dateEnd AND s1.city.cityId = :cityFrom AND t.city.cityId = :cityTo)" +
                    "OR (f.date between :dateStart AND :dateEnd AND f.city.cityId = :cityFrom AND s1.city.cityId = :cityTo)" +
                    "OR (s1.date between :dateStart AND :dateEnd AND s1.city.cityId = :cityFrom AND s2.city.cityId = :cityTo " +
                    "AND s1.date < s2.date)"
    )
    List<Route> findRoutes(
            @Param(value = "dateStart") Date dateStart,
            @Param(value = "dateEnd") Date dateEnd,
            @Param(value = "cityFrom") Long cityFrom,
            @Param(value = "cityTo") Long cityTo
    );

//    @Query(
//            "select distinct r from Route r "
//                    + "left join r.from f "
//                    + "left join r.stops s1 "
//                    + "left join r.stops s2 "
//                    + "left join r.to t "
//                    + "WHERE " +
//                    "(f.city.cityId = :cityFrom AND t.city.cityId = :cityTo) "
////                    + "OR (s1.city.cityId = :cityFrom AND t.city.cityId = :cityTo)"
////                    + "OR (f.city.cityId = :cityFrom AND s1.city.cityId = :cityTo)"
////                    + "OR (s1.city.cityId = :cityFrom AND s2.city.cityId = :cityTo AND s1.date < s2.date)"
//    )
//    List<Route> findRoutes(
//            @Param(value = "cityFrom") Long cityFrom,
//            @Param(value = "cityTo") Long cityTo
//    );


//    @Query(
//            "select distinct r from Route r "
//                    + "join r.from f "
//                    + "join r.stops s1 "
//                    + "join r.stops s2 "
//                    + "join r.to t "
//                    + "WHERE " +
//                    "(f.city.cityId = :cityFrom AND t.city.cityId = :cityTo) " +
//                    "OR ( s1.city.cityId = :cityFrom AND t.city.cityId = :cityTo)" +
//                    "OR ( f.date < :dateEnd AND f.city.cityId = :cityFrom AND s1.city.cityId = :cityTo)" +
//                    "OR (s1.city.cityId = :cityFrom AND s2.city.cityId = :cityTo " +
//                    "AND s1.date < s2.date AND :dateStart < :dateEnd)"
//    )
//    List<Route> findRoutes(
//            @Param(value = "dateStart") Date dateStart,
//            @Param(value = "dateEnd") Date dateEnd,
//            @Param(value = "cityFrom") Long cityFrom,
//            @Param(value = "cityTo") Long cityTo
//    );

    Route findByRouteId(Long id);

    List<Route> findByOwnerId(long id, Pageable pageable);

    Integer countByOwnerId(long id);
}
