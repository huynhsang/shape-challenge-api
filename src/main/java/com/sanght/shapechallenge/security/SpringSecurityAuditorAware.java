package com.sanght.shapechallenge.security;

import com.sanght.shapechallenge.common.util.SecurityUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String userName = SecurityUtil.getCurrentUsername();
        return userName != null ? Optional.of(userName) : Optional.of("system");
    }
}
