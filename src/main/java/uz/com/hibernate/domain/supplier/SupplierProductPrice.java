package uz.com.hibernate.domain.supplier;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.object.ObjectProperty;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.settings.Type;
import uz.com.utils.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "supplier_product_prices")
public class SupplierProductPrice extends _Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "product_model")
    private String productModel;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "price")
    private Double price;

    @Column(name = "bought_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime boughtDate;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_type_id", nullable = false, referencedColumnName = "id")
    private Type unitType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_type_id", referencedColumnName = "id")
    private Type currencyType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_product_id", nullable = false, referencedColumnName = "id")
    private SupplierProduct supplierProduct;

}
