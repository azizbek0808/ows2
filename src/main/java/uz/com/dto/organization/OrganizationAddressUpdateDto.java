package uz.com.dto.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericCrudDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Organization Address update request")
public class OrganizationAddressUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = false)
    private String longitude;

    @ApiModelProperty(required = false)
    private String latitude;



}
