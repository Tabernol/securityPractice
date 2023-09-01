package com.example.securitypractice.config;

import com.example.securitypractice.database.entity.Role;
import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
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
                        .antMatchers("/", "/login", "/registration").permitAll()
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
            // Delegate to the default implementation for loading a user
            OidcUser oidcUser = delegate.loadUser(userRequest);
            String email = oidcUser.getClaims().get("email").toString();
            if (!userService.ifExist(email)) {
                userService.save(oidcUser);
            }

            Optional<User> maybeUser = userService.getByLogin(email);


            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            Role role = maybeUser.orElseThrow().getRole();
            mappedAuthorities.add(role);

            // TODO
            // 1) Fetch the authority information from the protected resource using accessToken
            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

            Map<String, Object> claims = oidcUser.getClaims();

            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

            return oidcUser;
        };
    }
//////////////////////////////////////////////////////////


}

//    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//        return userRequest -> {
//            String email = userRequest.getIdToken().getClaim("email");
//
//            if(!userService.ifExist(email)){
//                User newUser = new User();
//                newUser.setLogin(email);
//                newUser.setName(userRequest.getIdToken().getClaim("given_name"));
//                newUser.setRole(Role.USER);
//                userService.save(newUser);
//            }
//
//            UserDetails userDetails = userService.loadUserByUsername(email);
////            new OidcUserService().loadUser()
//            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
//
//            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());
//
//            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
//                    new Class[]{UserDetails.class, OidcUser.class},
//                    (proxy, method, args) -> userDetailsMethods.contains(method)
//                            ? method.invoke(userDetails, args)
//                            : method.invoke(oidcUser, args));
//        };
//    }


