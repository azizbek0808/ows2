package uz.com.dto.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericCrudDto;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Organization Mfo update request")
public class OrganizationMfoUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true)
    private String mfo;
}
