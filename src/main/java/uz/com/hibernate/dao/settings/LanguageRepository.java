package uz.com.hibernate.dao.settings;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.com.hibernate.domain.settings.Language;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Long> {

    Language findByCode(String code);

}
