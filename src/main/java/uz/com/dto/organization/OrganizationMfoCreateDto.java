package uz.com.dto.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericCrudDto;
import uz.com.hibernate.domain.organization.Organization;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Organization Mfo create request")
public class OrganizationMfoCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private String mfo;

    @ApiModelProperty(hidden = true)
    private Organization organization;


}
