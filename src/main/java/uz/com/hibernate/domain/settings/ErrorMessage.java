package uz.com.hibernate.domain.settings;

import lombok.*;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import uz.com.enums.State;
import uz.com.hibernate.base.AuditInfo;
import uz.com.hibernate.base._Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "settings_error_messages")
public class ErrorMessage extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "error_code")
    private String errorCode;

    @Embedded
    private AuditInfo auditInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "message_id")
    @Builder.Default
    private List<ErrorMessageTranslation> translations = new ArrayList<>();
}
