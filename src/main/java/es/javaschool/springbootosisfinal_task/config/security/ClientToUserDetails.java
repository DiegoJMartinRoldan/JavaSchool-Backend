package es.javaschool.springbootosisfinal_task.config.security;

import es.javaschool.springbootosisfinal_task.domain.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ClientToUserDetails implements UserDetails {

    // Spring Security Entity to UserDetails converter

    private String email;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;

    public ClientToUserDetails(Client client) {
        email = client.getEmail();
        password = client.getPassword();
        grantedAuthorityList = Arrays.stream(client.getRole()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
