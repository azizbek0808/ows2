package uz.com.hibernate.domain.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base.Item;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_roles")
public class Role extends Item implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(unique = true)
    private String code;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "auth_roles_permissions",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Collection<Permission> permissions;

    @Override
    public String getAuthority() {
        return "ROLE_" + getCode();
    }
}
