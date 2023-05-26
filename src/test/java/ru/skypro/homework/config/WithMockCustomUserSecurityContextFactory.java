package ru.skypro.homework.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import ru.skypro.homework.security.SecurityUser;

import static ru.skypro.homework.ConstantsTest.USER_TEST;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        SecurityUser securityUser = new SecurityUser();
        securityUser.setUser(USER_TEST);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(securityUser, "password", securityUser.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
