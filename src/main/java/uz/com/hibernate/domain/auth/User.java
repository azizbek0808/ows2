package uz.com.hibernate.domain.auth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.util.StringUtils;
import uz.com.enums.GenderType;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.application.ApplicationResponsibleUser;
import uz.com.hibernate.domain.files.ResourceFile;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.settings.Language;
import uz.com.hibernate.domain.settings.Type;
import uz.com.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_users")
public class User extends _Entity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "social_phone_number")
    private String socialPhoneNumber;

    @Column(name = "birth_date", columnDefinition = "TIMESTAMP")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime birthDate;

    @Column(name = "is_locked", columnDefinition = "boolean default false")
    private boolean locked;

    @Column(name = "is_system_admin", columnDefinition = "boolean default false")
    private boolean systemAdmin;

    @Column(name = "ordering")
    private Long ordering;

    @Column(name = "is_online", columnDefinition = "boolean default false")
    private Boolean isOnline;

    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supply_department_id", referencedColumnName = "id")
    private Type supplyDepartmentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "position_type_id", referencedColumnName = "id")
    private Type positionType;

    @OneToOne
    @JoinColumn(name = "lang_id", referencedColumnName = "id")
    private Language language;

    @OneToOne
    @JoinColumn(name = "resource_file", referencedColumnName = "id")
    private ResourceFile resourceFile;

    @Embedded
    private AuditInfo auditInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserContact> contacts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_organization", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "organization_id")})
    private List<Organization> organizations;


    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "auth_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Collection<Role> roles;

    public String getShortName() {
        return String.format("%s %s",
                StringUtils.isEmpty(lastName) ? "" : lastName,
                StringUtils.isEmpty(firstName) ? "" : firstName.substring(0, 1));
    }
}
