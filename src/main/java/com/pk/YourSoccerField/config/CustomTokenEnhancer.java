package com.pk.YourSoccerField.config;

import com.pk.YourSoccerField.model.CustomUserDetail;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {

        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

        if (user.getId() != null) {
            info.put("id", user.getId());
        }
        if (user.getCode() != null) {
            info.put("code", user.getCode());
        }
        if (user.getEmail() != null) {
            info.put("email", user.getEmail());
        }
        info.put("isActive", user.isActive());
        if (user.getCreateTime() != null) {
            info.put("createTime", user.getCreateTime().toString());
        }
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);

        return super.enhance(customAccessToken, authentication);
    }
}