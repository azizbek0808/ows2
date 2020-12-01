package uz.com.criteria.organization;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationAddressCriteria extends GenericCriteria {

    private String name;
    private String longitude;
    private String latitude;

    @Builder(builderMethodName = "childBuilder")
    public OrganizationAddressCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String name, String longitude, String latitude) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
