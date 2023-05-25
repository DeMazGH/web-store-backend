package ru.skypro.homework.security;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequestScope
@Data
public class SecurityUser implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("Was invoked method - getAuthorities");
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().name());
        return List.of(grantedAuthority);
    }

    @Override
    public String getPassword() {
        log.info("Was invoked method - getPassword");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        log.info("Was invoked method - getUsername");
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        log.info("Was invoked method - isAccountNonExpired");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        log.info("Was invoked method - isAccountNonLocked");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        log.info("Was invoked method - isCredentialsNonExpired");
        return true;
    }

    @Override
    public boolean isEnabled() {
        log.info("Was invoked method - isEnabled");
        return true;
    }
}
