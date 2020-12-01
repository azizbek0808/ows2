package uz.com.hibernate.domain.application;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.contract.Contract;
import uz.com.hibernate.domain.object.Object;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.settings.Type;
import uz.com.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "applications")
public class Application extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "application_number")
    private String applicationNumber;

    @Column(name = "application_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime applicationDate;

    @Column(name = "object_name")
    private String objectName;

    @Column(name = "description")
    private String description;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
    private Type status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "builder_organization_id", nullable = false, referencedColumnName = "id")
    private Organization builderOrganization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payer_organization_id", nullable = false, referencedColumnName = "id")
    private Organization payerOrganization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "object_id", nullable = false, referencedColumnName = "id")
    private Object object;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id")
    @Builder.Default
    private List<ApplicationItem> applicationItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id")
    @Builder.Default
    private List<Contract> contracts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id")
    @Builder.Default
    private List<ApplicationResponsibleUser> responsibleUsers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id")
    @Builder.Default
    private List<ApplicationFile> applicationFiles = new ArrayList<>();
}
