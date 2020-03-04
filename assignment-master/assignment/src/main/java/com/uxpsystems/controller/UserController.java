package com.uxpsystems.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uxpsystems.exception.ResourceNotFoundException;
import com.uxpsystems.model.User;
import com.uxpsystems.repository.UserRepository;

/**
 * @author Sandip Rathod
 *
 */
@RestController
@RequestMapping("/assignement")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String getString() {
		return "Hello sandy";
	}

	/*
	 * @GetMapping("/user") public List<User> getLoginUser() {
	 * 
	 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 * 
	 * Object credentials = auth.getCredentials(); Collection<? extends
	 * GrantedAuthority> authorities = auth.getAuthorities(); Object details =
	 * auth.getDetails(); String name = auth.getName(); Object principal =
	 * auth.getPrincipal(); boolean authenticated = auth.isAuthenticated();
	 * 
	 * log.info(credentials +" "+
	 * authorities+" "+details+" "+name+" "+principal+" "+authenticated);
	 * 
	 * return userRepository.findByUsername(name).get(); }
	 */

	@GetMapping("/allusers")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getAllEmployees() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		User employee = userRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/saveusers")
	@PreAuthorize("hasRole('ADMIN')")
	public User createEmployee(@Valid @RequestBody User employee) {

		String encoded = new BCryptPasswordEncoder().encode("admin@123");
		employee.setPassword(encoded);
		return userRepository.save(employee);
	}

	@PutMapping("/updateuser/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody User employeeDetails) throws ResourceNotFoundException {
		User employee = userRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setUsername(employeeDetails.getUsername());
		employee.setPassword(employeeDetails.getPassword());
		employee.setEmail(employeeDetails.getEmail());
		employee.setStatus(employeeDetails.getStatus());

		final User updatedEmployee = userRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/deleteuser/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		User employee = userRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		userRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	/*
	 * @GetMapping("/api/test/user")
	 * 
	 * @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") public String
	 * userAccess() { return ">>> User Contents!"; }
	 * 
	 * @GetMapping("/api/test/pm")
	 * 
	 * @PreAuthorize("hasRole('PM')") public String projectManagementAccess() {
	 * return ">>> Board Management Project"; }
	 * 
	 * @GetMapping("/api/test/admin")
	 * 
	 * @PreAuthorize("hasRole('ADMIN')") public String adminAccess() { return
	 * ">>> Admin Contents"; }
	 */
}
