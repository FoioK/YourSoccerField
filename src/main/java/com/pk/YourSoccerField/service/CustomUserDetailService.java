package com.pk.YourSoccerField.service;

import com.pk.YourSoccerField.model.CustomUserDetail;
import com.pk.YourSoccerField.model.UserEntity;
import com.pk.YourSoccerField.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User email not found!"));

        Collection<GrantedAuthority> permissions = new ArrayList<>();
        userRepository.getPermissions(email)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .forEach(permission -> permissions.add(new SimpleGrantedAuthority("ROLE_" + permission)));

        userEntity.setGrantedAuthorityList(permissions);

        return new CustomUserDetail(userEntity);
    }
}
