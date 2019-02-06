package com.pk.ysf.util;

import com.pk.ysf.apimodels.model.CustomUserDetail;
import com.pk.ysf.apimodels.model.UserEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {

    private static final String ID = "id";
    private static final String CODE = "code";
    private static final String EMAIL = "email";
    private static final String IS_ACTIVE = "isActive";
    private static final String CREATE_TIME = "createTime";
    private Collection<? extends GrantedAuthority> defaultAuthorities;

    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(StringUtils.arrayToCommaDelimitedString(defaultAuthorities));
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(
                USERNAME,
                userAuthentication.getName()
        );

        if (userAuthentication.getAuthorities() != null && !userAuthentication.getAuthorities().isEmpty())
            response.put(
                    AUTHORITIES,
                    AuthorityUtils.authorityListToSet(userAuthentication.getAuthorities())
            );

        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME))
            return new UsernamePasswordAuthenticationToken(
                    this.getUserDetail(map), "N/A",
                    getAuthorities(map));

        return null;
    }

    private CustomUserDetail getUserDetail(Map<String, ?> map) {
        return new CustomUserDetail(this.getUserEntity(map));
    }

    private UserEntity getUserEntity(Map<String, ?> map) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.valueOf(map.get(ID).toString()));
        userEntity.setCode(Long.valueOf(map.get(CODE).toString()));
        userEntity.setEmail((String) map.get(EMAIL));
        userEntity.setActive(Boolean.valueOf(map.get(IS_ACTIVE).toString()));
        userEntity.setCreateTime(LocalDateTime.parse((String) map.get(CREATE_TIME)));
        userEntity.setPassword("");
        userEntity.setGrantedAuthorityList(new ArrayList<>());

        return userEntity;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES))
            return defaultAuthorities;
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String)
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        if (authorities instanceof Collection)
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}