package uz.project.template.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.project.template.base.BaseEntity;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
public class AuthUserEntity extends BaseEntity implements UserDetails {
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String fullName;
    @Column
    private String password;
    @Column(nullable = false)
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public AuthUserEntity(String email, String fullName, String role, String password) {
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.password = password;
    }
}
