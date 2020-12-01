package uz.com.criteria.organization;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMfoCriteria extends GenericCriteria {

    private String mfo;

    @Builder(builderMethodName = "childBuilder")
    public OrganizationMfoCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection) {
        super(selfId, page, perPage, sortBy, sortDirection);
    }
}
