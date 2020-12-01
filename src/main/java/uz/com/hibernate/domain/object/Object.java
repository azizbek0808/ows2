package uz.com.hibernate.domain.object;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationMfo;
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
@Table(name = "objects")
public class Object extends _Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "builder_organization_id", nullable = false, referencedColumnName = "id")
    private Organization builderOrganization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payer_organization_id", nullable = false, referencedColumnName = "id")
    private Organization payerOrganization;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "object_id")
    @Builder.Default
    private List<ObjectProperty> objectProperties = new ArrayList<>();
}
