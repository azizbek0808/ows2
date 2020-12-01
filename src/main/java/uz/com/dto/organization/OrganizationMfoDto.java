package uz.com.dto.organization;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.hibernate.domain.organization.Organization;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMfoDto extends GenericDto {

    private String mfo;


    @Builder(builderMethodName = "childBuilder")
    public OrganizationMfoDto(Long id, String mfo) {
        super(id);
        this.mfo = mfo;
    }
}
