package uz.com.hibernate.dao.settings;

import uz.com.criteria.settings.TypeCriteria;
import uz.com.dto.settings.TypeUpdateDto;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.settings.Type;

public interface TypeDao extends Dao<Type, TypeCriteria> {
    Type findByValue(String name);

    Type findByValueNotId(TypeUpdateDto dto);
}
