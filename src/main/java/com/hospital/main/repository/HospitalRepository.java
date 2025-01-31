package com.hospital.main.repository;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hospital.main.modelentity.HospitalEntity;

import jakarta.transaction.Transactional;
@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {
	//by default srping will generate queries by understanding methode names or if it can't then we can write JPQL queries or native sql queries.
	
   //get hospital data by location
	List<HospitalEntity> findByLocation(String location);
	
	//get hospital data between minRating and MaxRating
@Query("SELECT refferenceVariableName FROM HospitalEntity refferenceVariableName WHERE refferenceVariableName.rating BETWEEN :minRating AND :maxRating")
	List<HospitalEntity> findBetween(@Param("minRating") double minRating,@Param("maxRating")double maxRating);

//to get hospitals data which is greater than a specified rating
@Query("SELECT h FROM HospitalEntity h WHERE h.rating > :rating")
List<HospitalEntity> findByGreaterThanRating(double rating);

//get hospitals with a rating greater than a specified value
@Query("SELECT h FROM HospitalEntity h WHERE h.rating < :rating")
List<HospitalEntity> findByLessThanRating(double rating);

//get hospitals with location and name
@Query("SELECT h FROM HospitalEntity h WHERE h.location = :location AND h.name= :name")
List<HospitalEntity> findByLocationAndName(String location, String name);

//get hospital by location or name
//@Query(value = "SELECT * FROM HospitalEntity h WHERE h.location = :location OR h.name = :name", nativeQuery = true)//this i snative sql query
List<HospitalEntity> findByLocationOrName(String location, String name);

//get hospitals in specific location and within a certain range of rating(between minRating and maxRating
@Query("SELECT h FROM HospitalEntity h WHERE h.location = :location AND h.rating BETWEEN :minRating AND :maxRating")
List<HospitalEntity> findByLocationBetweeenMinRatingAndMaxRating(String location, double minRating, double maxRating);

//get hospitals by rating in sort-by-rating(native sql query).
@Query(value = "SELECT * FROM hospitals h ORDER BY h.rating ASC", nativeQuery = true)
List<HospitalEntity> SortByRating();

@Modifying
@Transactional
//delete by name
@Query("DELETE FROM HospitalEntity h WHERE h.name = :name")
 int DeleteByName(String name);


//delete by rating between minRating and maxRating
@Modifying
@Transactional
@Query("DELETE FROM HospitalEntity h WHERE h.rating BETWEEN :minRating AND :maxRating")
int DeleteBetweenminRatingAndmaxRating(double minRating, double maxRating);//return type int will return number of rows deleted.

//delete by greater than specified rating.
@Modifying
@Transactional
@Query("DELETE FROM HospitalEntity e WHERE e.rating> :rating")
int DeleteByGreaterThanSecifiedRating(double rating);

//delete by greater than specified rating and location.
@Modifying
@Transactional
@Query(value="DELETE FROM hospitals h WHERE h.rating > :rating AND h.location=location",nativeQuery=true)
int DeleteByGreaterThanRatingAndLocation(double rating, String location);

//Find hospital by email (for checking existence before updating)
Optional<HospitalEntity> findByEmail(String email);
//Update hospital email by current email
@Modifying
@Transactional
@Query("UPDATE HospitalEntity h SET h.email = :email WHERE h.email = :updateEmail")
void updateEmailByEmail(String email,Map<String, Object> updateEmail);


}