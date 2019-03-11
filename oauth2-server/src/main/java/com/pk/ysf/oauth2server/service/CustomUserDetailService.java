package com.pk.ysf.oauth2server.service;

import com.pk.ysf.apimodels.exception.MissingEntityException;
import com.pk.ysf.apimodels.exception.UnactivatedUserException;
import com.pk.ysf.apimodels.entity.CustomUserDetail;
import com.pk.ysf.apimodels.entity.UserEntity;
import com.pk.ysf.oauth2server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<UserEntity> users = userRepository.findByEmail(email)
                .orElseThrow(() -> new MissingEntityException("User email not found"));

        UserEntity userEntity = findActiveUser(users);

        Collection<GrantedAuthority> permissions = new ArrayList<>();
        userRepository.getPermissions(email)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .forEach(permission -> permissions.add(new SimpleGrantedAuthority(permission + "")));

        userEntity.setGrantedAuthorityList(permissions);

        return new CustomUserDetail(userEntity);
    }

    private UserEntity findActiveUser(List<UserEntity> users) {
        UserEntity userEntity = users.stream()
                .filter(UserEntity::isActive)
                .findAny().orElse(null);

        if (userEntity != null) {
            return userEntity;
        } else if (users.size() == 1) {
            throw new UnactivatedUserException(
                    "Login operation is not allowed for non-activated users"
            );
        } else {
            throw new MissingEntityException("User email not found");
        }
    }
}
