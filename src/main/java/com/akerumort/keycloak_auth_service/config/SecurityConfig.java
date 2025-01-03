package com.akerumort.keycloak_auth_service.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

@Configuration
public class SecurityConfig {
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring HTTP security...");

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http.oauth2Login(Customizer.withDefaults());

        logger.debug("Security filter chain configured");

        return http
                .authorizeHttpRequests(c -> c.requestMatchers("/error").permitAll()
                        .requestMatchers("/auth/admin").hasRole("ADMIN") // prefix "ROLE_" is default by SS
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutSuccessUrl("http://localhost:8081/realms/kaservice/protocol/openid-connect/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            logger.debug("Converting JWT authorities...");
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            var roles = jwt.getClaimAsStringList("kaservice_roles");

            return Stream.concat(authorities.stream(),
                            roles.stream()
                                    .filter(role -> role.startsWith("ROLE_"))
                                    .map(SimpleGrantedAuthority::new)
                                    .map(GrantedAuthority.class::cast))
                    .toList();
        });

        return converter;
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        var oidcUserService = new OidcUserService();
        return userRequest -> {
            logger.debug("Loading OIDC user...");
            var oidcUser = oidcUserService.loadUser(userRequest);
            var roles = oidcUser.getClaimAsStringList("kaservice_roles");
            var authorities = Stream.concat(oidcUser.getAuthorities().stream(),
                            roles.stream()
                                    .filter(role -> role.startsWith("ROLE_"))
                                    .map(SimpleGrantedAuthority::new)
                                    .map(GrantedAuthority.class::cast))
                    .toList();
            logger.info("OIDC user loaded with roles: {}", roles);
            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }
}
