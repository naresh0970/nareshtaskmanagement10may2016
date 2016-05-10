package com.tms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tms.Entity.*;
import com.tms.Repository.UserRepository;


/**
 * Custom User Details Service. Service responsible to authenticate the user and
 * populate the current logged in user details.
 * 
 * @author Pavan
 *
 */
@Service
public class MyUserDetials implements UserDetailsService {
	

	Logger logger = LoggerFactory.getLogger(getClass());

	//@Autowired
	//AuthService authService;

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public User loadUserByUsername(String userName) throws UsernameNotFoundException {
		/*logger.info("Fetching user details");
		logger.debug("UserName: {}", userName);
*/

		User user = userRepository.findByUserName(userName);
		if (user == null) {
			logger.info("User not found in the database. Returning exception");
			throw new UsernameNotFoundException("User does not exist.");
		}

		return user;
	}

}
