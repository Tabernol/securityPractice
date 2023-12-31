package com.example.securitypractice.config;

import com.example.securitypractice.SecurityPracticeApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = SecurityPracticeApplication.class)
@Configuration
public class AuditConfiguration {

//    @Bean
//    public AuditorAware<String> auditorAware() {
//        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//                .map(authentication -> (UserDetails)authentication.getPrincipal())
//                .map(UserDetails::getUsername);
//    }
}
