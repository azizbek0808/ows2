package uz.com.hibernate.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.organization.Organization;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_category")
public class ProductCategory extends _Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "name")
    private String name;

    @Embedded
    private AuditInfo auditInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_category_id")
    @Builder.Default
    private List<Product> products= new ArrayList<>();

}
