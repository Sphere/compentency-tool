package com.sphere.compentencytool.controller;

import java.util.List;

import com.sphere.compentencytool.model.GetPassbook;
import com.sphere.compentencytool.model.Passbook;
import com.sphere.compentencytool.repository.passbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sphere.compentencytool.exception.ResourceNotFoundException;
import com.sphere.compentencytool.model.Designations;
import com.sphere.compentencytool.model.Roles;
import com.sphere.compentencytool.repository.DesignationsRepository;
import com.sphere.compentencytool.repository.RolesRepository;


@RestController
@RequestMapping("/compentencytool/v1")
public class Controller {
	
	@Autowired
	DesignationsRepository designationsRepository;
	@Autowired
	RolesRepository rolesRepository;
	@Autowired
	passbookRepository PassbookRepository;
	
	
	// DESIGNATION API's
	
   // 1. Insert designations
   @PostMapping(value = "/designations", produces = "application/json")
   public Designations Add_Designations(@RequestBody Designations designations) {
	return this.designationsRepository.save(designations);
   }	
	
   // 2. Get All designations
   @GetMapping("/designations")
	public List<Designations> getAllDesignations(){
		return this.designationsRepository.findAll();
	}
// 3. Get designations by id
   @GetMapping("/designations/{id}")
	public ResponseEntity<Designations> getDesignationsById(@PathVariable(value = "id") Long designationsId)
			throws ResourceNotFoundException {
	   Designations designations = designationsRepository.findById(designationsId)
				.orElseThrow(() -> new ResourceNotFoundException("designations not found for this id :: " + designationsId));
		return ResponseEntity.ok().body(designations);
	}
   
   
// 4. Update designations by id
   @PutMapping("/designations/{id}")
	public ResponseEntity<Designations> UpdateDesignations(@PathVariable(value = "id") Long designation_id,
			@RequestBody Designations designationDetails) throws ResourceNotFoundException {
	   Designations _design = designationsRepository.findById(designation_id)
				.orElseThrow(() -> new ResourceNotFoundException("designations not found for this id :: " + designation_id));

	   _design.setDesignation_id(designationDetails.getDesignation_id());
		_design.setDesignation_name(designationDetails.getDesignation_name());
		_design.setDesignation_description(designationDetails.getDesignation_description());
		_design.setStatus(designationDetails.getStatus());
		_design.setCreatedon(designationDetails.getCreatedon());
		_design.setCreatedby(designationDetails.getCreatedby());
		final Designations updated_designationDetails = designationsRepository.save(_design);
		return ResponseEntity.ok(updated_designationDetails);
	}
   
// 5. delete designations by id
   @DeleteMapping("/designations/{id}")
	public ResponseEntity<HttpStatus> DeleteDesignations(@PathVariable("id") long id) {
		try {
			designationsRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
   
   
      //ROLE API's
   
   
// 1. Insert roles
   @PostMapping(value = "/roles", produces = "application/json")
   public Roles Add_roles(@RequestBody Roles roles) {
	return this.rolesRepository.save(roles);
   }
   
// 2. Get All roles
   @GetMapping("/roles")
	public List<Roles> getAllRoles(){
		return this.rolesRepository.findAll();
	}
   
// 3. Get roles by id
   @GetMapping("/roles/{id}")
	public ResponseEntity<Roles> getrolesById(@PathVariable(value = "id") Long roleId)
			throws ResourceNotFoundException {
	   Roles roles = rolesRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Roles not found for this id :: " + roleId));
		return ResponseEntity.ok().body(roles);
	}
   
   
// 4. Update roles by id
   @PutMapping("/roles/{id}")
	public ResponseEntity<Roles> UpdateRoles(@PathVariable(value = "id") Long role_id,
			@RequestBody Roles roleDetails) throws ResourceNotFoundException {
	   Roles _roles = rolesRepository.findById(role_id)
				.orElseThrow(() -> new ResourceNotFoundException("Roles not found for this id :: " + role_id));

	   _roles.setRole_id(roleDetails.getRole_id());
	   _roles.setRole_name(roleDetails.getRole_name());
	   _roles.setRole_description(roleDetails.getRole_description());
	   _roles.setStatus(roleDetails.getStatus());
	   _roles.setCreatedon(roleDetails.getCreatedon());
	   _roles.setCreatedby(roleDetails.getCreatedby());
		final Roles updated_rolesDetails = rolesRepository.save(_roles);
		return ResponseEntity.ok(updated_rolesDetails);
	}
   
// 5. delete role by id
   @DeleteMapping("/roles/{id}")
	public ResponseEntity<HttpStatus> DeleteRole(@PathVariable("id") long id) {
			try {
				rolesRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
   
   
   
    // ROLE-DESIGNATION-MAPPING API's

	// 1. Insert passbook
	@PatchMapping(value = "/passbook", produces = "application/json")
	public Passbook Add_Passbook(@RequestBody Passbook passbook) {
		System.out.println(passbook);
	   return this.PassbookRepository.save(passbook);
	}
	@PostMapping("/passbook")
	public List<Passbook> GetPassbook(@RequestBody GetPassbook getpassbook){
		String TypeName=getpassbook.getRequest().getTypeName();
		List<String> userId=getpassbook.getRequest().getUserId();
		System.out.println("Userid = "+userId);
		List<Passbook> result = null;
		if (TypeName != null & userId != null) {
			System.out.println(userId);
			System.out.println(TypeName);
			result=  this.PassbookRepository.searchByTypeNameUserId(TypeName,userId);
		}
		else if (TypeName != null & userId == null) {
			result= this.PassbookRepository.searchByTypeName(TypeName);
		}


		return result;
	}
   
   
	@GetMapping("/designations/test")
	public String Test(){
		
		return "Api working ";	
	}
		

}	

