package uz.com.hibernate.domain.settings;

import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "settings_types")
public class Type extends _Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "name")
    private String name;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "type_code")
    private String typeCode;

    @Column(unique = true)
    private String value;

    @Embedded
    private AuditInfo auditInfo;
}
