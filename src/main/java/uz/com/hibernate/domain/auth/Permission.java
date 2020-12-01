package uz.com.hibernate.domain.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base.Item;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_permissions")
public class Permission extends Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(unique = true)
    private String code;

    @Column(name = "parent_id")
    private Long parentId;

    @Embedded
    private AuditInfo auditInfo;

}
