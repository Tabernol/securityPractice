package com.example.securitypractice.config;

import com.example.securitypractice.database.entity.Role;
import com.example.securitypractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/", "/login", "/registration", "/login/forgot").permitAll()
                        .antMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users"))
                .oauth2Login(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users")
                        .userInfoEndpoint()
                        .oidcUserService(this.oidcUserService())
                );
    }


    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            String email = oidcUser.getClaims().get("email").toString();
            if (!userService.ifExist(email)) {
                userService.save(oidcUser);
            }

            UserDetails userDetails = userService.loadUserByUsername(email);

            DefaultOidcUser oidcUser2 = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
//            Map<String, Object> claims = oidcUser.getClaims();
//
//            for (Map.Entry<String, Object> entry : claims.entrySet()) {
//                System.out.println(entry.getKey() + ":" + entry.getValue());
//            }
            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser2, args));
        };
    }
}


