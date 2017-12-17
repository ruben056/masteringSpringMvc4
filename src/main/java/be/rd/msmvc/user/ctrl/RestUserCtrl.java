package be.rd.msmvc.user.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.rd.msmvc.general.infrastructure.EntityNotFoundException;
import be.rd.msmvc.user.dto.User;
import be.rd.msmvc.user.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("User Api")
@RestController
@RequestMapping(path = "/rest/user/")
public class RestUserCtrl {

	@Autowired
	private UserRepository userRepository;

	@ApiOperation("Retrieve all users")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@ApiOperation("Create a user")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		HttpStatus status = (userRepository.exists(user.getEmail())) 
									? HttpStatus.OK : HttpStatus.CREATED;
		User saved = userRepository.save(user);
		return new ResponseEntity<>(saved, status);
	}

	@ApiOperation("Update a user")
	@RequestMapping(value = "/user/{email}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) throws EntityNotFoundException{
		User saved = userRepository.update(email, user);
		return new ResponseEntity<>(saved, HttpStatus.OK);
	}

	@ApiOperation("Delete a user")
	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable String email) throws EntityNotFoundException {
		userRepository.delete(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
