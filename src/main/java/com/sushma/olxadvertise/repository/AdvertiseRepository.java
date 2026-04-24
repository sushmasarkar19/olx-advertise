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

    // ── Derived queries – no JPQL literals, no fix needed ─────────────────

    List<AdvertiseEntity> findByUsernameAndActive(String username, String active);

    Optional<AdvertiseEntity> findByIdAndUsernameAndActive(int id, String username, String active);

    // ── JPQL queries – FIX APPLIED TO ALL 6 ───────────────────────────────
    //
    // ROOT CAUSE:
    //   Hibernate 6.x (Spring Boot 4.x) introduced a strict JPQL/HQL parser
    //   that validates ALL query literals at startup against the entity model.
    //   Inline string literals like 'true' in comparisons (a.active = 'true')
    //   fail this static validation even when the Java field type is String.
    //
    // FIX:
    //   Replace every  a.active = 'true'  with  a.active = :active
    //   and add  @Param("active") String active  to every method signature.
    //   In your service always pass "true" for this parameter.
    //
    //   This is also better practice — bind parameters are:
    //     - immune to Hibernate's static literal type checking
    //     - safer (no JPQL injection risk)
    //     - reusable (same query plan for any active value)

    // 1. Full-text search across title and description
    @Query("SELECT a FROM AdvertiseEntity a WHERE a.active = :active AND " +
           "(LOWER(a.title) LIKE LOWER(CONCAT('%',:text,'%')) OR " +
           " LOWER(a.description) LIKE LOWER(CONCAT('%',:text,'%')))")
    List<AdvertiseEntity> searchByText(
            @Param("active") String active,
            @Param("text") String text);

    // 2. Filter by criteria (no date condition)
    @Query("SELECT a FROM AdvertiseEntity a WHERE a.active = :active " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%')))")
    List<AdvertiseEntity> filterByCriteria(
            @Param("active") String active,
            @Param("searchText") String searchText,
            @Param("categoryId") Integer categoryId,
            @Param("postedBy") String postedBy);

    // 3. Filter by criteria + exact date match
    @Query("SELECT a FROM AdvertiseEntity a WHERE a.active = :active " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%'))) " +
           "AND a.createdDate = :onDate")
    List<AdvertiseEntity> filterByCriteriaOnDate(
            @Param("active") String active,
            @Param("searchText") String searchText,
            @Param("categoryId") Integer categoryId,
            @Param("postedBy") String postedBy,
            @Param("onDate") LocalDate onDate);

    // 4. Filter by criteria + createdDate greater than fromDate
    @Query("SELECT a FROM AdvertiseEntity a WHERE a.active = :active " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%'))) " +
           "AND a.createdDate > :fromDate")
    List<AdvertiseEntity> filterByCriteriaGreaterThan(
            @Param("active") String active,
            @Param("searchText") String searchText,
            @Param("categoryId") Integer categoryId,
            @Param("postedBy") String postedBy,
            @Param("fromDate") LocalDate fromDate);

    // 5. Filter by criteria + createdDate less than fromDate
    @Query("SELECT a FROM AdvertiseEntity a WHERE a.active = :active " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%'))) " +
           "AND a.createdDate < :fromDate")
    List<AdvertiseEntity> filterByCriteriaLessThan(
            @Param("active") String active,
            @Param("searchText") String searchText,
            @Param("categoryId") Integer categoryId,
            @Param("postedBy") String postedBy,
            @Param("fromDate") LocalDate fromDate);

    // 6. Filter by criteria + createdDate between fromDate and toDate
    @Query("SELECT a FROM AdvertiseEntity a WHERE a.active = :active " +
           "AND (:searchText IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%',:searchText,'%')) " +
           "     OR LOWER(a.description) LIKE LOWER(CONCAT('%',:searchText,'%'))) " +
           "AND (:categoryId IS NULL OR a.categoryId = :categoryId) " +
           "AND (:postedBy IS NULL OR LOWER(a.postedBy) LIKE LOWER(CONCAT('%',:postedBy,'%'))) " +
           "AND a.createdDate BETWEEN :fromDate AND :toDate")
    List<AdvertiseEntity> filterByCriteriaBetween(
            @Param("active") String active,
            @Param("searchText") String searchText,
            @Param("categoryId") Integer categoryId,
            @Param("postedBy") String postedBy,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);
}