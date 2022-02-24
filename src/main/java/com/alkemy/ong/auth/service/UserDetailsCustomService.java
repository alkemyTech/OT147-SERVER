package com.alkemy.ong.auth.service;

import com.alkemy.ong.auth.domain.UserDomain;
import com.alkemy.ong.auth.mapper.UserMapper;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Transactional
@Service
public class UserDetailsCustomService implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    /*
        Method to register a user with password encryption. Return a UserEntity.
     */
    public UserEntity save(UserEntity user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRoleId(user.getRoleId());
        userEntity = this.userRepository.save(userEntity);

        return userEntity;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleId().getName()));

        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), authorities);
    }

}

