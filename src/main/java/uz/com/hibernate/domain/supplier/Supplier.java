package uz.com.hibernate.domain.supplier;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "supplier")
public class Supplier extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_inn")
    private String supplierInn;

    @Column(name = "supplier_position")
    private String supplierPosition;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Embedded
    private AuditInfo auditInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplier_id")
    @Builder.Default
    private List<SupplierProduct> supplierProducts = new ArrayList<>();
}
