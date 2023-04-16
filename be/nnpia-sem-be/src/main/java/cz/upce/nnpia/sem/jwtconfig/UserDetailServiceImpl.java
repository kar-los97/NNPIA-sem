package cz.upce.nnpia.sem.jwtconfig;

import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmailAndDeletedAtIsNull(username);
        if(user==null) throw new UsernameNotFoundException("User not found.");
        return UserDetailsImpl.build(user);
    }
}
