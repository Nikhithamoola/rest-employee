package com.sathya.rest.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import org.springframework.web.bind.annotation.RestController;

import com.sathya.rest.models.Employee;
import com.sathya.rest.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
@Autowired
EmployeeService employeeService;
@PostMapping("/saveemployee")
public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
    
    Employee saveEmp = employeeService.saveEmployee(employee);
    return  ResponseEntity.status(HttpStatus.CREATED)
    		              .header("employee status","employee saved successfully")
    		              .body(saveEmp);
}
@PostMapping("/saveall")
public ResponseEntity<List<Employee>> saveallEmployees( @RequestBody List<Employee> employees) {
    
    List<Employee> emps = employeeService.saveallEmployee(employees);
    return  ResponseEntity.status(HttpStatus.CREATED)
    		              .header("employee status","employee saved successfully")
    		              .body(emps);
}
@GetMapping("/getall")
public ResponseEntity<List<Employee>> getallEmployees() {
    
    List<Employee> emps = employeeService.getallEmployee();
    return  ResponseEntity.status(HttpStatus.OK)
    		              .header("employee status","employee saved successfully")
    		              .body(emps);
}
@GetMapping("/getbyid/{id}")
public ResponseEntity<?> getById(@PathVariable Long id)
{	Optional<Employee> optionalEmp = employeeService.getById(id);
	if(optionalEmp.isPresent())
	{	
		Employee employee = optionalEmp.get();
		 // Create an EntityModel for the user
        EntityModel<Employee> entityModel = EntityModel.of(employee);

        // Add self link
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getById(id)).withSelfRel());

        // Add link to update the user
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).updateById(id, employee)).withRel("update"));

        // Add link to delete the user
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteById(id)).withRel("delete"));

        // Add link to get all users
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getallEmployees()).withRel("all-users"));
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(entityModel);
	}
	else
	{	return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 .body("Emp is not found with Id.."+id);
	}
}




@GetMapping("/getbyemail/{email}")
public ResponseEntity<?> getByEmail(@PathVariable String email) {
    
    Optional<Employee> optionalEmp = employeeService.getByEmail(email);
    if(optionalEmp.isPresent()) {
    return  ResponseEntity.status(HttpStatus.OK)
    		              .body(optionalEmp.get());
    }
    else
    {
    	return  ResponseEntity.status(HttpStatus.NOT_FOUND)
	              .body("email is not found.."+email);
    }
}
@DeleteMapping("/deleteById/{id}")
public ResponseEntity<?>deleteById(@PathVariable Long id)
{boolean status= employeeService.deleteById(id); 
 if(status)
 {
	 
	 return ResponseEntity.noContent().build();		 
 }
 else
 {
	 return ResponseEntity.status(HttpStatus.NOT_FOUND)
			 .header("status","data is not found")
			 .body("data not found by id..."+id);
 }
}
@DeleteMapping("/deleteByEmail/{email}")
public ResponseEntity<?>deleteByEmail(@PathVariable String email)
{boolean status= employeeService.deleteByEmail(email); 
 if(status)
 {
	 return ResponseEntity.noContent().build();	
	 
 }
 else
 {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	        .header("status","data is not found")
			 .body("data not found by email..."+email);
 }
}
@PutMapping("updateById/{id}")
public ResponseEntity<?>updateById (@PathVariable Long id, @RequestBody Employee newemployee)
{Optional<Employee> updatedemployee=employeeService.updateById(id, newemployee);
if(updatedemployee.isPresent())
{
	 return  ResponseEntity.status(HttpStatus.OK)
             .body(updatedemployee);
}
else
{
	return ResponseEntity.status(HttpStatus.NOT_FOUND)
			 .body("data not found by id..."+id);
}
}
@PatchMapping("update/{id}")
public ResponseEntity<?>updatePartialById (@PathVariable Long id, @RequestBody Map<String,Object>updates)
{Optional<Employee> updatedemployee=employeeService.updatePartialById(id, updates);
if(updatedemployee.isPresent())
{
	return  ResponseEntity.status(HttpStatus.OK)
    .body(updatedemployee.get());
}
else
{
return  ResponseEntity.status(HttpStatus.NOT_FOUND)
.body("data is not found.."+id);
}
}
}
