package uz.com.hibernate.domain.contract;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.application.Application;
import uz.com.hibernate.domain.settings.Type;
import uz.com.hibernate.domain.supplier.Supplier;
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
@Table(name = "contracts")
public class Contract extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "contract_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime contractDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "total_amount_with_vat")
    private Double totalAmountWithVat;

    @Column(name = "description")
    private String description;

    @Column(name = "payment_condition")
    private String paymentCondition;

    @Column(name = "delivery_condition")
    private String deliveryCondition;

    @Column(name = "packaging_types")
    private String packagingTypes;

    @Column(name = "certificate_availability")
    private String certificateAvailability;

    @Column(name = "service_maintenance")
    private String serviceMaintenance;

    @Column(name = "warranty_period")
    private String warrantyPeriod;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_phone_number")
    private String authorPhoneNumber;

    @Column(name = "brand")
    private String brand;

    /**
     * ADMIN CHECKED
     */
    @Column(name = "is_admin_checked")
    private Boolean isAdminChecked;

    @Column(name = "admin_checked_time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime adminCheckedTime;

    @Column(name = "admin_user_id")
    private Long adminUserId;

    @Column(name = "admin_cause_text")
    private String adminCauseText;

    /**
     * DIRECTOR CHECKED
     */
    @Column(name = "is_director_checked")
    private Boolean isDirectorChecked;

    @Column(name = "director_checked_time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime directorCheckedTime;

    @Column(name = "director_user_id")
    private Long directorUserId;

    @Column(name = "director_cause_text")
    private String directorCauseText;

    /**
     * RESPONSIBLE USER CHECKED
     */
    @Column(name = "is_responsible_user_checked")
    private Boolean isResponsibleUserChecked;

    @Column(name = "responsible_user_checked_time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime responsibleUserCheckedTime;

    @Column(name = "responsible_user_id")
    private Long responsibleUserId;

    @Column(name = "responsible_user_cause_text")
    private String responsibleUserCauseText;

    @Embedded
    private AuditInfo auditInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
    private Type status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_type_id", referencedColumnName = "id")
    private Type currencyType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false, referencedColumnName = "id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id", nullable = false, referencedColumnName = "id")
    private Application application;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contract_id")
    @Builder.Default
    private List<ContractFile> contractFiles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contract_id")
    @Builder.Default
    private List<ContractItem> contractItems = new ArrayList<>();
}
