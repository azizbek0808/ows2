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
@ApiModel(value = "Organization Address create request")
public class OrganizationAddressCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = false)
    private String longitude;

    @ApiModelProperty(required = false)
    private String latitude;

    @ApiModelProperty(hidden = true)
    private Organization organization;


}
