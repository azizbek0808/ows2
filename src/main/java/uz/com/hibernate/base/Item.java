package uz.com.hibernate.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item extends _Entity {
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "name_ru", nullable = false)
    private String nameRu;
    @Column(name = "name_en", nullable = false)
    private String nameEn;
    @Column(name = "name_uz", nullable = false)
    private String nameUz;
}
