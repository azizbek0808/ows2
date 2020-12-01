package uz.com.dto.organization;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.hibernate.domain.organization.Organization;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationAddressDto extends GenericDto {

    private String name;
    private String longitude;
    private String latitude;
    @Builder(builderMethodName = "childBuilder")
    public OrganizationAddressDto(Long id, String name, String longitude, String latitude) {
        super(id);
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
