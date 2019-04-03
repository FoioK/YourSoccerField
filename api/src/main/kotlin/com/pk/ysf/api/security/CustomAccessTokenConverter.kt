package com.pk.ysf.api.security

import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.token.AccessTokenConverter
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.LinkedHashSet

@Component
class CustomAccessTokenConverter : AccessTokenConverter, JwtAccessTokenConverterConfigurer {

    private var includeGrantType: Boolean = false
    private var userTokenConverter: UserAuthenticationConverter = CustomUserAuthenticationConverter()

    override fun configure(configurer: JwtAccessTokenConverter) {
        configurer.accessTokenConverter = this
    }

    override fun extractAccessToken(value: String, map: MutableMap<String, *>): OAuth2AccessToken {
        val token = DefaultOAuth2AccessToken(value)

        val info: MutableMap<String, Any?> = HashMap(map)
        info.remove(AccessTokenConverter.EXP)
        info.remove(AccessTokenConverter.AUD)
        info.remove(AccessTokenConverter.CLIENT_ID)
        info.remove(AccessTokenConverter.SCOPE)

        if (map.containsKey(AccessTokenConverter.EXP)) {
            token.expiration = Date((map[AccessTokenConverter.EXP] as Long) * 1000L)
        }
        if (map.containsKey(AccessTokenConverter.JTI)) {
            info[AccessTokenConverter.JTI] = map[AccessTokenConverter.JTI]
        }

        token.scope = extractScope(map)
        token.additionalInformation = info

        return token
    }

    override fun extractAuthentication(map: MutableMap<String, *>): OAuth2Authentication {
        val scope: Set<String> = extractScope(map)
        val clientId: String = map[AccessTokenConverter.CLIENT_ID] as String
        val parameters: MutableMap<String, String> = mutableMapOf(AccessTokenConverter.CLIENT_ID to clientId)

        if (includeGrantType && map.containsKey(AccessTokenConverter.GRANT_TYPE)) {
            parameters[AccessTokenConverter.GRANT_TYPE] = map.get(AccessTokenConverter.GRANT_TYPE) as String
        }

        val resourceIds = LinkedHashSet(
                if (map.containsKey(AccessTokenConverter.AUD)) getAudience(map) else emptySet()
        )
        var authorities: Collection<GrantedAuthority>? = null

        if (userTokenConverter.extractAuthentication(map) == null && map.containsKey(AccessTokenConverter.AUTHORITIES)) {
            @Suppress("UNCHECKED_CAST") val roles =
                    (map[AccessTokenConverter.AUTHORITIES] as Collection<String>).toTypedArray()
            authorities = AuthorityUtils.createAuthorityList(*roles)
        }
        val request = OAuth2Request(
                parameters, clientId, authorities,
                true, scope, resourceIds, null, null, null)

        return OAuth2Authentication(request, userTokenConverter.extractAuthentication(map))
    }

    private fun extractScope(map: Map<String, Any?>): Set<String> {
        if (map.containsKey(AccessTokenConverter.SCOPE)) {
            val scopeObject: Any = map[AccessTokenConverter.SCOPE] ?: return emptySet()

            if (scopeObject is String) {
                return LinkedHashSet(listOf(
                        *scopeObject.split(" ".toRegex())
                                .dropLastWhile { it.isEmpty() }
                                .toTypedArray())
                )
            } else if (Collection::class.java.isAssignableFrom(scopeObject.javaClass)) {
                @Suppress("UNCHECKED_CAST")
                return LinkedHashSet(scopeObject as Collection<String>)
            }
        }

        return emptySet()
    }

    private fun getAudience(map: Map<String, *>): Collection<String> {
        val auds = map[AccessTokenConverter.AUD]

        return if (auds is Collection<*>) {
            @Suppress("UNCHECKED_CAST")
            auds as Collection<String>
        } else setOf(auds as String)

    }

    override fun convertAccessToken(token: OAuth2AccessToken, authentication: OAuth2Authentication): Map<String, *> {
        var response: MutableMap<String, Any?> = mutableMapOf()
        val clientToken = authentication.oAuth2Request

        if (!authentication.isClientOnly) {
            @Suppress("UNCHECKED_CAST")
            response = userTokenConverter.convertUserAuthentication(authentication.userAuthentication)
                    as MutableMap<String, Any?>
        }
        else if (clientToken.authorities != null && !clientToken.authorities.isEmpty()) {
            response[UserAuthenticationConverter.AUTHORITIES] = AuthorityUtils.authorityListToSet(clientToken.authorities)
        }
        if (token.scope != null) {
            response[AccessTokenConverter.SCOPE] = token.scope
        }
        if (token.additionalInformation.containsKey(AccessTokenConverter.JTI)) {
            response[AccessTokenConverter.JTI] = token.additionalInformation[AccessTokenConverter.JTI]
        }
        if (token.expiration != null) {
            response[AccessTokenConverter.EXP] = token.expiration.time / 1000
        }
        if (includeGrantType && authentication.oAuth2Request.grantType != null) {
            response[AccessTokenConverter.GRANT_TYPE] = authentication.oAuth2Request.grantType
        }

        response.putAll(token.additionalInformation)
        response[AccessTokenConverter.CLIENT_ID] = clientToken.clientId

        if (clientToken.resourceIds != null && !clientToken.resourceIds.isEmpty()) {
            response[AccessTokenConverter.AUD] = clientToken.resourceIds
        }

        return response
    }

}