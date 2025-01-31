package com.hospital.main.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.main.modelentity.HospitalEntity;
import com.hospital.main.service.HospitalService;
import com.hospital.main.apiresponse.ApiResponse;

@RequestMapping("/hospitals")
@RestController
public class HospitalController {
	
		@Autowired
		HospitalService hospitalService;
		
		//to save one new hospital data
		@PostMapping("/savehospital")
	public ResponseEntity<HospitalEntity> saveHospitalDetails(@RequestBody HospitalEntity hospitalEntity){
			HospitalEntity saveEmpObj=hospitalService.getHospitalDetails(hospitalEntity);
		return ResponseEntity.status(HttpStatus.CREATED)
				.header("info","data saved succssfully")
				.body(saveEmpObj);
	 }
		
		//to save multiple Hospitals data
	  	@PostMapping("/savehospitals")
	  public ResponseEntity<List<HospitalEntity>> saveHospitalDetails(@RequestBody List<HospitalEntity> HospitalEntity){
	  	List<HospitalEntity> saveHspObj=hospitalService.saveHospitalDetails(HospitalEntity);
	  	return ResponseEntity.status(HttpStatus.CREATED)
	  			.header("info","data saved succssfully")
	  			.body(saveHspObj);
	   }
	  	
	 // Get all hospitals data
	      @GetMapping("/getall")
	      public ResponseEntity<?> getAllhospitals() {
	         List<HospitalEntity> hospitals = hospitalService.getAllhospitals(); 
	          if (!hospitals.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.OK)
	                        .header("info", "data retrieved successfully")
	                        .body(hospitals); // Return the list of hospitals
	               }
	          else {
	                ApiResponse apiResponse = new ApiResponse();
	                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	                apiResponse.setErrorMessage("No hopitals data found");
	                apiResponse.setTimestamp(LocalDateTime.now());

	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .header("info", "data not found")
	                      .body(apiResponse);
	               }
	          
	      }
	      
	   // Get hospitals by ID
	      @GetMapping("/getbyid/{id}")
	      public ResponseEntity<?> getHospitalsById(@PathVariable("id") Long id) {
	          Optional<HospitalEntity> hospitals = hospitalService.getBYid(id);
	          if(hospitals.isPresent()) {
	          return ResponseEntity.status(HttpStatus.OK)
	                  .header("info","data retrieved")
	                  .body(hospitals.get());
	          }
	          else
	          {
	          ApiResponse apiResponse=new ApiResponse();
	          apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	          apiResponse.setErrorMessage("employee not found");
	          apiResponse.setTimestamp(LocalDateTime.now());
	          	return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                      .header("info","data not found")
	                      .body(apiResponse);
	          }
	      }
	      
	   // Get hospitals by location
	      @GetMapping("/getbylocation/{location}")
	      public ResponseEntity<?> getHospitalsBylocation(@PathVariable("location") String location) {
	          List<HospitalEntity> hospitals = hospitalService.getBYLocation(location);
	       // Check if the list is not empty
	            if (!hospitals.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.OK)
	                        .header("info", "data retrieved successfully")
	                        .body(hospitals); // Return the list of hospitals
	               }
	          else {
	                ApiResponse apiResponse = new ApiResponse();
	                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	                apiResponse.setErrorMessage("No hopital found with location: " + location);
	                apiResponse.setTimestamp(LocalDateTime.now());

	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .header("info", "data not found")
	                      .body(apiResponse);
	               }
	        }
	      
	      //get hospitals by rating between minRating and maxRating
	      @GetMapping("/getbetween/{minRating}/{maxRating}")
	      public ResponseEntity<?> updateBetweenminSalaryAndmaxSalary(@PathVariable("minRating") double minRating,@PathVariable("maxRating") double maxRating){
	    	  List<HospitalEntity> hospitals=hospitalService.getBetween(minRating,maxRating);
	    	// Check if the list is not empty
	            if (!hospitals.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.OK)
	                        .header("info", "data retrieved successfully")
	                        .body(hospitals); // Return the list of hospitals
	               }
	            else {
	                ApiResponse apiResponse = new ApiResponse();
	                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	                apiResponse.setErrorMessage("No hopitals found between minRating : "+minRating+" and maxRating : "+maxRating);
	                apiResponse.setTimestamp(LocalDateTime.now());

	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .header("info", "data not found")
	                      .body(apiResponse);
	               }
	      }
	      
	      //get hospitals with a rating greater than a specified value
	      @GetMapping("/greaterthan/rating/{rating}")
	      public ResponseEntity<?> getbyRating(@PathVariable double rating){
	    	  List<HospitalEntity> hospital=hospitalService.byRating(rating);
	    	  if(!hospital.isEmpty()) {
	    		  return ResponseEntity.status(HttpStatus.OK)
	    				  .header("info", "retrieved hospitals data which are greater than a specified rating")
	    				  .body(hospital);
	    	  }
	    	  else
	    	  {
	    		  ApiResponse apiResponse = new ApiResponse();
	                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	                apiResponse.setErrorMessage("No hopitals found greater than : "+rating+" rating");
	                apiResponse.setTimestamp(LocalDateTime.now());

	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .header("info", "data not found")
	                      .body(apiResponse);
	    	  }
	      }
	      
	    //get hospitals with a rating less than a specified value
	      @GetMapping("/lessthan/rating/{rating}")
	      public ResponseEntity<?> getLessThan(@PathVariable double rating) {
	      	List<HospitalEntity> hospital=hospitalService.getBylessThanRating(rating);
	      	if(!hospital.isEmpty()) {
	      		return ResponseEntity.status(HttpStatus.OK)
	    				  .header("info", "retrieved hospitals data which are less than a specified rating")
	    				  .body(hospital);
	      	}
	      	 else
	    	  {
	    		  ApiResponse apiResponse = new ApiResponse();
	                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	                apiResponse.setErrorMessage("No hopitals found less than : "+rating+" rating");
	                apiResponse.setTimestamp(LocalDateTime.now());

	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .header("info", "data not found")
	                      .body(apiResponse);
	    	  }
	      }
	      
	    //get hospitals with location and name
	      @GetMapping("/getbyLocationAndName")
	      public ResponseEntity<?> getbyLocationAndName(@RequestParam String location,@RequestParam String name) {
	    	  List<HospitalEntity> hospitals=hospitalService.getbylocationAndname(location,name);
	      	if(!hospitals.isEmpty()) {
	      		return ResponseEntity.status(HttpStatus.OK)
	    				  .header("info", "retrieved hospitals data based on location and name")
	    				  .body(hospitals);
	      	}
	      	else
	    	  {
	    		  ApiResponse apiResponse = new ApiResponse();
	                apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	                apiResponse.setErrorMessage("No hospitals found with location : " + location + " and name : " + name);
	                apiResponse.setTimestamp(LocalDateTime.now());

	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .header("info", "data not found")
	                      .body(apiResponse);
	    	  }
	      }
	      
	      //get hospital by location or name
	      @GetMapping("/getbyLocationOrName")
	      public ResponseEntity<?> getMethodName(@RequestParam String location, @RequestParam String name) {
	    	  List<HospitalEntity> hospitals=hospitalService.getbyLocationOrName(location,name);
	    	  if(!hospitals.isEmpty()) {
	    		  return ResponseEntity.status(HttpStatus.OK)
	    				  .header("info", "hospitals data retrieved based on location : "+location+" or name : "+name)
	    				  .body(hospitals);
	    	  }
	    	  else
	    	  {
	    		  ApiResponse apiResponse=new ApiResponse();
	    		  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	    		  apiResponse.setErrorMessage("hospital not found with location or name");
	    		  apiResponse.setTimestamp(LocalDateTime.now());
	    			return ResponseEntity.status(HttpStatus.NOT_FOUND)
		  	      			.header("info","cannot find hospital with location : "+location+" or name : "+name)
		  	      			.body(apiResponse);
	          }
         }
	      
	      //get hospitals in specific location and within a certain range of rating
	      @GetMapping("/getByLocationBetweenRatingRange/{location}/{minRating}/{maxRating}")
	      public ResponseEntity<?> getbyLocationinratingRange(@PathVariable String location,@PathVariable double minRating,@PathVariable double maxRating) {
	    	  List<HospitalEntity> hospitals=hospitalService.getByLocationAndRating(location,minRating,maxRating);
	    	  if(!hospitals.isEmpty()) {
	    		  return ResponseEntity.status(HttpStatus.OK)
	    				  .header("info", "hospitals data retrieved based on location : "+location+" between minRating : "+minRating+" and maxRating : "+maxRating)
	    				  .body(hospitals);
	    	  }
	    	  else
	    	  {
	    		  ApiResponse apiResponse=new ApiResponse();
	    		  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	    		  apiResponse.setErrorMessage("hospital not found with location between minRating and maxRating");
	    		  apiResponse.setTimestamp(LocalDateTime.now());
	    			return ResponseEntity.status(HttpStatus.NOT_FOUND)
		  	      			.header("info","cannot find hospitals with location : "+location+" between minRating : "+minRating+" and maxRating : "+maxRating)
		  	      			.body(apiResponse);
	          }
	      }
	      
	      //get hospitals by rating in sort-by-rating
	      @GetMapping("/sort-by-rating")
	      public ResponseEntity<?> getSortByrating() {
	    	  List<HospitalEntity> hospitals=hospitalService.getSortByRating();
	    	  if(!hospitals.isEmpty()) {
	    		  return ResponseEntity.status(HttpStatus.OK)
	    	     		  .header("info", "hospitals data retrieved sort by rating")
	    				  .body(hospitals);
	    	  }
	    	  else
	    	  {
	    		  ApiResponse apiResponse=new ApiResponse();
	    		  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	    		  apiResponse.setErrorMessage("hospitals not found");
	    		  apiResponse.setTimestamp(LocalDateTime.now());
	    			return ResponseEntity.status(HttpStatus.NOT_FOUND)
		  	      			.header("info","cannot find hospitals")
		  	      			.body(apiResponse);
	          }
	      }
	      
	      //delete hospital by id 
	      @DeleteMapping("/delete/id/{id}")
	      public ResponseEntity<ApiResponse> deletebyid(@PathVariable("id") Long id){
	      	if(hospitalService.deletebyid(id)) {
	      	return	ResponseEntity.status(HttpStatus.NO_CONTENT)
	      			.header("info","hospital data deleted based on id")
	      			.build();
	      	}
	      	//in realtime here we don't need to write else just give data else block body directly.
	      	ApiResponse apiResponse=new ApiResponse();
	      	apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	      	apiResponse.setErrorMessage("hospital not found with id : "+id);
	      	apiResponse.setTimestamp(LocalDateTime.now());
	      	return ResponseEntity.status(HttpStatus.NOT_FOUND)
	      			.header("info","cannot find hospital with id : "+id)
	      			.body(apiResponse);
	      }
	      
	      //delete by hospital name
	      @DeleteMapping("/deletebyHospitalName/{name}")
	      public ResponseEntity<?> deletebyhospitalname(@PathVariable String name){
	    	  int  deleteCount=hospitalService.deletebyhospitalname(name);
	    	  if(deleteCount>0) {
	  	      	return	ResponseEntity.status(HttpStatus.NO_CONTENT)
	  	      			.header("info",deleteCount+" hospitals data deleted based on name")
	  	      			.build();
	  	      	}
	  	      	ApiResponse apiResponse=new ApiResponse();
	  	      	apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	  	      	apiResponse.setErrorMessage("hospital not found with name : "+name);
	  	      	apiResponse.setTimestamp(LocalDateTime.now());
	  	      	return ResponseEntity.status(HttpStatus.NOT_FOUND)
	  	      			.header("info","cannot find hospitals with name : "+name)
	  	      			.body(apiResponse);
	  	      }
	      
	      //delete by rating between minRating and maxRating
	      @DeleteMapping("/deletebyRatingBetween/{minRating}/{maxRating}")
	      public ResponseEntity<ApiResponse> deletebyRatingBetween(@PathVariable double minRating,@PathVariable double maxRating){
	    	  int deletedCount = hospitalService.deleteByRatingBetween(minRating, maxRating);

	    	    if (deletedCount > 0) {
	    	        return ResponseEntity.status(HttpStatus.NO_CONTENT)
	    	                .header("info", "Hospitals "+deletedCount+" deleted between minRating: " + minRating + " and maxRating: " + maxRating)
	    	                .build();
	    	    }

	    	    ApiResponse apiResponse = new ApiResponse();
	    	    apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	    	    apiResponse.setErrorMessage("No hospitals found between minRating: " + minRating + " and maxRating: " + maxRating);
	    	    apiResponse.setTimestamp(LocalDateTime.now());

	    	    return ResponseEntity.status(HttpStatus.NOT_FOUND)
	    	            .header("info", "No hospitals found between minRating: " + minRating + " and maxRating: " + maxRating)
	    	            .body(apiResponse);
	    	}
	      
	      //delete by greater than specified rating.
	      @DeleteMapping("/deletebyGreaterThan/{rating}")
	      public ResponseEntity<ApiResponse> deletebyGreaterThanSpecifiedRating(@PathVariable double rating){
	    	  int deletedCount=hospitalService.deleteGreaterThanSpecifiedRating(rating);
	    	  if(deletedCount>0) {
	    		  return ResponseEntity.status(HttpStatus.NO_CONTENT)
	    	                .header("info", "Hospitals "+deletedCount+" deleted greater than rating : "+rating)
	    	                .build();
	    	    }

	    	    ApiResponse apiResponse = new ApiResponse();
	    	    apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	    	    apiResponse.setErrorMessage("No hospitals found greater than rating : "+rating);
	    	    apiResponse.setTimestamp(LocalDateTime.now());

	    	    return ResponseEntity.status(HttpStatus.NOT_FOUND)
	    	            .header("info", "No hospitals found greater than rating : "+rating)
	    	            .body(apiResponse);
	      }
	      
	      //delete by greater than specified rating and location.
	      @DeleteMapping("/deletebyGreaterThanRatingAndLocation/{rating}/{location}")
	      public ResponseEntity<ApiResponse> deletebyGreaterThanRatingAndLocation(@PathVariable double rating,@PathVariable String location){
	    	  int deletedCount=hospitalService.deleteByGreaterThanRatingAndLocation(rating,location);
	    	  if(deletedCount>0) {
	    		  return ResponseEntity.status(HttpStatus.NO_CONTENT)
	    				  .header("info","hospitals : "+deletedCount+ " data deleted based greater than specified rating and location")
	    				  .build();
	    	  }
	    	  ApiResponse apiResponse = new ApiResponse();
	    	    apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	    	    apiResponse.setErrorMessage("No hospitals found greater than rating : "+rating+" and location : "+location);
	    	    apiResponse.setTimestamp(LocalDateTime.now());

	    	    return ResponseEntity.status(HttpStatus.NOT_FOUND)
	    	            .header("info", "No hospitals found greater than rating : "+rating+" and location : "+location)
	    	            .body(apiResponse);
	      }
	      
	      //Update entire hospital based on id.
	      @PutMapping("/updateEntireHospital/id/{id}")
	      public ResponseEntity<?> updateById(@PathVariable Long id,@RequestBody HospitalEntity hospital){
	    	  Optional<HospitalEntity> hospital1=hospitalService.updateHospitalById(id,hospital);
	    	  if(hospital1.isPresent()) {
	    		  return ResponseEntity.status(HttpStatus.OK)
	    				  .header("info", "hospital updated successfully")
	    				  .body(hospital1);
	    	  }
	    	  ApiResponse apiResponse=new ApiResponse();
	        	apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	        	apiResponse.setErrorMessage("hospital not found with id"+id);
	        	apiResponse.setTimestamp(LocalDateTime.now());
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
	        			.header("info","hospital details not updated")
	        			.body(apiResponse);
	      }
	      
	    //to partial update specific field hospital name only (using patch).
	      @PatchMapping("updatespecific/id/{id}")
	      public ResponseEntity<?> partialUpdateHospital(@PathVariable Long id,@RequestBody Map<String,Object>updateDetailName) {
	    	  HospitalEntity hospital=hospitalService.partialUpdateHspl(id,updateDetailName);
	    	  if(hospital!=null) {
	    		  return ResponseEntity.status(HttpStatus.OK)
	    				  .header("info", "data is updatad")
	    				  .body(hospital);
	    	  }
	    	  else
	    	  {
	    		  ApiResponse apiResponse=new ApiResponse();
	  	      	apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	  	      	apiResponse.setErrorMessage("hospital not found with id : "+id);
	  	      	apiResponse.setTimestamp(LocalDateTime.now());
	  	      	return ResponseEntity.status(HttpStatus.NOT_FOUND)
	  	      			.header("info","cannot find hospital with id : "+id)
	  	      			.body(apiResponse);
	    	  }
	      }
	      
	    //to partially(update specific field only) update email by email.(using patch)
	      @PatchMapping("/updatespecific/email/{email}")
	      public ResponseEntity<?> updateEmailbyEmail(@PathVariable String email, @RequestBody Map<String, Object> updateEmail) {
	          // Call the service to update the email by the given email
	          HospitalEntity hospitals = hospitalService.updateHospitalEmailByEmail(email, updateEmail);

	          if (hospitals != null) {
	              // If hospital is found and updated
	              return ResponseEntity.status(HttpStatus.OK)
	                      .header("info", "data is updated")
	                      .body(hospitals);
	          } else {
	              // If hospital is not found
	              ApiResponse apiResponse = new ApiResponse();
	              apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
	              apiResponse.setErrorMessage("Hospital not found with email: " + email);
	              apiResponse.setTimestamp(LocalDateTime.now());
	              
	              return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                      .header("info", "Cannot find hospital with email: " + email)
	                      .body(apiResponse);
	          }
	      }
}
