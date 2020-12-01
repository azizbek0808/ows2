package uz.com.criteria.settings;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageCriteria extends GenericCriteria {
    private String name;
    private String code;


    @Builder(builderMethodName = "childBuilder")
    public LanguageCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String name, String code) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.name = name;
        this.code = code;
    }
}
