package be.rd.msmvc.user.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import be.rd.msmvc.general.infrastructure.EntityNotFoundException;
import be.rd.msmvc.user.dto.User;

@Repository
public class UserRepository {

	private final Map<String, User> userMap = new ConcurrentHashMap<>();

	public User update(String email, User user) throws EntityNotFoundException{
		if(!exists(email)) {
			throw new EntityNotFoundException("User " + email + " cannot be found");
		}
		user.setEmail(email);
		return userMap.put(email, user);
	}

	public User save(User user) {
		return userMap.put(user.getEmail(), user);
	}

	public User findOne(String email) throws EntityNotFoundException {
		if(!exists(email)) {
			throw new EntityNotFoundException("User " + email + " cannot be found");
		}
		User user = userMap.get(email);
		return user;
	}

	
	public List<User> findAll() {
		return new ArrayList<>(userMap.values());
	}

	public void delete(String email) throws EntityNotFoundException{
		if(!exists(email)) {
			throw new EntityNotFoundException("User " + email + " cannot be found");
		}
		userMap.remove(email);
	}

	public boolean exists(String email) {
		return userMap.containsKey(email);
	}
}
