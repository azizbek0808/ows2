package uz.com.criteria.settings;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDirectionCriteria extends GenericCriteria {

    private String name;
    private String codeName;

    @Builder(builderMethodName = "childBuilder")
    public BusinessDirectionCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String name, String codeName) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.name = name;
        this.codeName = codeName;
    }
}
