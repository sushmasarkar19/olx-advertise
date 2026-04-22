package com.sushma.olxadvertise.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sushma.olxadvertise.entity.AdvertiseEntity;

@Repository
public interface AdvertiseRepository extends JpaRepository<AdvertiseEntity, Integer> {

    // All active ads posted by a given username
    List<AdvertiseEntity> findByUsernameAndActive(String username, String active);

    // One active ad by id and username
    Optional<AdvertiseEntity> findByIdAndUsernameAndActive(int id, String username, String active);

    // Full-text search across title and description (case-insensitive)
    @Query("SELECT a FROM Advertise a WHERE a.active = '1' AND " +
           "(LOWER(a.title) LIKE LOWER(CONCAT('%',:text,'%')) OR " +
           " LOWER(a.description) LIKE LOWER(CONCAT('%',:text,'%')))")
    List<AdvertiseEntity> searchByText(@Param("text") String text);

    // ---------- filter-criteria queries ----------

    @Query("SELECT a FROM Advertise a WHERE a.active = '1' " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%')))")
    List<AdvertiseEntity> filterByCriteria(@Param("searchText") String searchText,
                                     @Param("categoryId") Integer categoryId,
                                     @Param("postedBy") String postedBy);

    @Query("SELECT a FROM Advertise a WHERE a.active = '1' " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%'))) " +
           "AND a.createdDate = :onDate")
    List<AdvertiseEntity> filterByCriteriaOnDate(@Param("searchText") String searchText,
                                           @Param("categoryId") Integer categoryId,
                                           @Param("postedBy") String postedBy,
                                           @Param("onDate") LocalDate onDate);

    @Query("SELECT a FROM Advertise a WHERE a.active = '1' " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%'))) " +
           "AND a.createdDate > :fromDate")
    List<AdvertiseEntity> filterByCriteriaGreaterThan(@Param("searchText") String searchText,
                                                @Param("categoryId") Integer categoryId,
                                                @Param("postedBy") String postedBy,
                                                @Param("fromDate") LocalDate fromDate);

    @Query("SELECT a FROM Advertise a WHERE a.active = '1' " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%'))) " +
           "AND a.createdDate < :fromDate")
    List<AdvertiseEntity> filterByCriteriaLessThan(@Param("searchText") String searchText,
                                             @Param("categoryId") Integer categoryId,
                                             @Param("postedBy") String postedBy,
                                             @Param("fromDate") LocalDate fromDate);

    @Query("SELECT a FROM Advertise a WHERE a.active = '1' " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%'))) " +
           "AND a.createdDate BETWEEN :fromDate AND :toDate")
    List<AdvertiseEntity> filterByCriteriaBetween(@Param("searchText") String searchText,
                                            @Param("categoryId") Integer categoryId,
                                            @Param("postedBy") String postedBy,
                                            @Param("fromDate") LocalDate fromDate,
                                            @Param("toDate") LocalDate toDate);
}
