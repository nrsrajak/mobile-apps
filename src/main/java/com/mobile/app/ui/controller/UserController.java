/**
 * 
 */
package com.mobile.app.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.app.excetions.UserServiceException;
import com.mobile.app.service.UserService;
import com.mobile.app.shared.dto.UserDTO;
import com.mobile.app.ui.controller.model.request.UserDetailsRequestModel;
import com.mobile.app.ui.controller.model.response.ErrorMessages;
import com.mobile.app.ui.controller.model.response.UserResponse;

/**
 * @author Naresh Kumar
 * The will register this class as rest controller and it will be able to receive http request when URL path matched.
 */
@RestController
@RequestMapping("users") //http://localhost:9907/users
public class UserController {
	
	@Autowired
	UserService userService;
	

	@GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserResponse getUsers(@PathVariable String id) {
		UserResponse returnedValue = new UserResponse();
		UserDTO userDTO = userService.getUserDetailsByUserId(id);
		BeanUtils.copyProperties(userDTO, returnedValue);

		return returnedValue;
	}
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserResponse returnValues = new UserResponse();
		
//		Error handling
		if(userDetails.getFirstName().isEmpty()) {
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userDetails, userDTO);
		
		UserDTO createUser = userService.createUser(userDTO);
		BeanUtils.copyProperties(createUser, returnValues);
		return returnValues;
	}
	@PutMapping
	public String updateUser(String msg) {
		msg = "Update User";
		return msg;
	}
	@DeleteMapping
	public String deleteUser(String msg) {
		msg = "Delete User";
		return msg;
	}
}
