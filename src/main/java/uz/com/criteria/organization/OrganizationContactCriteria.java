package uz.com.criteria.organization;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationContactCriteria extends GenericCriteria {

    private String email;
    private String phoneNumber;

    @Builder(builderMethodName = "childBuilder")
    public OrganizationContactCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String email, String phoneNumber) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
