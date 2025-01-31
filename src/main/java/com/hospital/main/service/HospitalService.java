package com.hospital.main.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.main.modelentity.HospitalEntity;
import com.hospital.main.repository.HospitalRepository;

@Service
public class HospitalService {
	
    @Autowired
    HospitalRepository hospitalRepository;

	//saving hospital data
	public HospitalEntity getHospitalDetails(HospitalEntity hospitalEntity) {
		
		HospitalEntity  saveHsp=hospitalRepository.save(hospitalEntity);
			 return saveHsp;
		}
	//saving multiple hospital data
	public List<HospitalEntity> saveHospitalDetails(List<HospitalEntity> hospitalEntity) {
			 List<HospitalEntity> savehospital=hospitalRepository.saveAll(hospitalEntity);
				 return savehospital;
	}
	
	// Get all hospitals data
	   public List<HospitalEntity> getAllhospitals(){
		   return hospitalRepository.findAll();
		   
	    }
	   
	// Get employee by ID
	    public Optional<HospitalEntity> getBYid(Long id) {
	        return hospitalRepository.findById(id);
	    }
	    
	    //get hospitals by location
		public List<HospitalEntity> getBYLocation(String location) {
			
			return hospitalRepository.findByLocation(location);
		}
		
		//get by minRating and maxRating
		public List<HospitalEntity> getBetween(double minRating,double maxRating) {
			return hospitalRepository.findBetween(minRating,maxRating);
		}
		
		//get hospitals with a rating greater than a specified value
		public List<HospitalEntity> byRating(double rating){
			return hospitalRepository.findByGreaterThanRating(rating);
		}
		
		//get hospitals with a rating greater than a specified value
		public List<HospitalEntity> getBylessThanRating(double rating){
			return hospitalRepository.findByLessThanRating(rating);
		}
		
		//get hospitals with location and name
		public List<HospitalEntity> getbylocationAndname(String location,String name){
			return hospitalRepository.findByLocationAndName(location,name);
		}
		
		//get hospital by location or name
		public List<HospitalEntity> getbyLocationOrName(String location, String name) {
			return hospitalRepository.findByLocationOrName(location,name);
		}
		
		//get hospitals in specific location and within a certain range of rating(between minRating and maxRating)
		public List<HospitalEntity> getByLocationAndRating(String location, double minRating, double maxRating) {
			return hospitalRepository.findByLocationBetweeenMinRatingAndMaxRating(location,minRating,maxRating);
		}
		
		//get hospitals by rating in sort-by-rating
		public List<HospitalEntity> getSortByRating() {
			return hospitalRepository.SortByRating();	
		}
		
		//delete by id
		public Boolean deletebyid(Long id) {
	    	 hospitalRepository.deleteById(id);
			  	return true;
			}
		
		//delete by name
		public int deletebyhospitalname(String name) {
				return hospitalRepository.DeleteByName(name);
		}
		
		//delete by rating between minRating and maxRating
		public int deleteByRatingBetween(double minRating, double maxRating) {
			return hospitalRepository.DeleteBetweenminRatingAndmaxRating(minRating,maxRating);
		}
		
		//delete by greater than specified rating.
		public int deleteGreaterThanSpecifiedRating(double rating) {
			return hospitalRepository.DeleteByGreaterThanSecifiedRating(rating);
		}
		
		//delete by greater than specified rating and location.
		public int deleteByGreaterThanRatingAndLocation(double rating, String location) {
			return hospitalRepository.DeleteByGreaterThanRatingAndLocation(rating,location);
		}
		
		//Update entire hospital based on id.
		public Optional<HospitalEntity> updateHospitalById(Long id,HospitalEntity hospital) {
			Optional<HospitalEntity>hospital1=hospitalRepository.findById(id);
			HospitalEntity hospital2=hospital1.get();
			if(hospital1.isPresent()) {
				hospital2.setName(hospital.getName());
				hospital2.setRating(hospital.getRating());
				hospital2.setLocation(hospital.getLocation());
				hospital2.setEmail(hospital.getEmail());
			hospitalRepository.save(hospital2);
			return hospital1;
			}
			return hospital1;
		}
		
		//to partial update specific field hospital name only (using patch).
		public HospitalEntity partialUpdateHspl(Long id, Map<String, Object> updateDetailName) {
			//based on the id get existing hospital data
			Optional<HospitalEntity> hospitalupdate=hospitalRepository.findById(id);
		if(hospitalupdate.isPresent()) {
			HospitalEntity existingHospital=hospitalupdate.get();
			if(updateDetailName.containsKey("name")) {
				existingHospital.setName((String)updateDetailName.get("name"));
			}
			return hospitalRepository.save(existingHospital);
		}
		else
		{
		return null;
		}
	}
		
		//to partially(update specific fields only) update email by email.(using patch)
		public HospitalEntity updateHospitalEmailByEmail(String email, Map<String, Object> updateEmail) {
		    Optional<HospitalEntity> hospital = hospitalRepository.findByEmail(email);

		    if (hospital.isPresent()) {
		        HospitalEntity hospitalEntity = hospital.get();

		        // Check if the 'email' field is in the request and update it
		        if (updateEmail.containsKey("email")) {
		            hospitalEntity.setEmail((String) updateEmail.get("email"));
		        }

		        // Save the updated hospital entity
		        return hospitalRepository.save(hospitalEntity);
		    } else {
		        // If no hospital is found with the given email
		        return null;
		    }
		}

		
		
		
		
		
		
		
}