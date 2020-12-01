package uz.com.hibernate.domain.settings;

import lombok.*;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import uz.com.enums.State;
import uz.com.hibernate.base._Entity;

import javax.persistence.*;

/**
 * Created by 'Javokhir Mamadiyarov Uygunovich' on 10/5/18.
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "setting_languages")
public class Language extends _Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
    @Column(name = "state", columnDefinition = "NUMERIC default 0")
    private State state = State.NEW;

    @Column(name = "name")
    protected String name;

    @Column(name = "code")
    protected String code;

}
