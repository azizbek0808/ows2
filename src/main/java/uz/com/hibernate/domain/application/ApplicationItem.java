package uz.com.hibernate.domain.application;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.auth.User;
import uz.com.hibernate.domain.object.ObjectProperty;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.product.Product;
import uz.com.hibernate.domain.settings.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "application_items")
public class ApplicationItem extends _Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
    private Product product;

    @Column(name = "product_model_name")
    private String productModelName;

    @Column(name = "product_type_name")
    private String productTypeName;

    @Column(name = "count")
    private Double count;

    @Column(name = "price")
    private Double price;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_type_id", nullable = false, referencedColumnName = "id")
    private Type unitType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id", nullable = false, referencedColumnName = "id")
    private Application application;
}
