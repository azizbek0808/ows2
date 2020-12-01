package uz.com.hibernate.domain.settings;


import lombok.*;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import uz.com.enums.State;
import uz.com.hibernate.base._Entity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "settings_error_message_translations")
public class ErrorMessageTranslation extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "lang_code")
    private String langCode;

    @OneToOne
    @JoinColumn(name = "lang_id", referencedColumnName = "id")
    private Language language;
}
