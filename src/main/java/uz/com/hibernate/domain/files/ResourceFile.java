package uz.com.hibernate.domain.files;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;
import uz.com.hibernate.domain.object.ObjectProperty;
import uz.com.hibernate.domain.organization.Organization;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "resource_files")
public class ResourceFile extends _Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "file_name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long size;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Embedded
    private AuditInfo auditInfo;

}
