package be.rd.msmvc.user.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import be.rd.msmvc.user.dto.User;

@Repository
public class UserRepository {

	private final Map<String, User> userMap = new ConcurrentHashMap<>();

	public User save(String email, User user) {
		user.setEmail(email);
		return userMap.put(email, user);
	}

	public User save(User user) {
		return save(user.getEmail(), user);
	}

	public List<User> findAll() {
		return new ArrayList<>(userMap.values());
	}

	public void delete(String email) {
		userMap.remove(email);
	}

	public boolean exists(String email) {
		return userMap.containsKey(email);
	}
}
