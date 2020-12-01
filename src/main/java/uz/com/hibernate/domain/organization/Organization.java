package uz.com.hibernate.domain.organization;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.auth.User;
import uz.com.hibernate.domain.settings.BusinessDirection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "organizations")
public class Organization extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "name")
    private String name;

    @Column(name = "nick")
    private String nick;

    @Column(name = "inn")
    private String inn;

    @Column(name = "for_object_name", columnDefinition = "boolean default false")
    private Boolean forObjectName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "financier_user_id", nullable = false, referencedColumnName = "id")
    private User financierUser;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_direction_id", nullable = false, referencedColumnName = "id")
    private BusinessDirection businessDirection;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "organization_id")
    @Builder.Default
    private List<OrganizationAddress> addresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "organization_id")
    @Builder.Default
    private List<OrganizationContact> contacts = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "organization_id")
    @Builder.Default
    private List<OrganizationMfo> mfos = new ArrayList<>();
}
