package uz.com.hibernate.domain.contract;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
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
@Table(name = "contract_items")
public class ContractItem extends _Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
    private Product product;

    @Column(name = "product_model")
    private String productModel;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "hs_code")
    private String hsCode;

    @Column(name = "count")
    private Double count;

    @Column(name = "price")
    private Double price;

    @Column(name = "amount")
    private String amount;

    @Column(name = "vat_rate")
    private Long vatRate;

    @Column(name = "total_amount")
    private String totalAmount;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_type_id", nullable = false, referencedColumnName = "id")
    private Type unitType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false, referencedColumnName = "id")
    private Contract contract;

}
