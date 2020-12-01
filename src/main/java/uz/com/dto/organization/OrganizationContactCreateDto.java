package uz.com.dto.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericCrudDto;
import uz.com.hibernate.domain.organization.Organization;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Organization Contact create request")
public class OrganizationContactCreateDto extends GenericCrudDto {

    @ApiModelProperty(example = "salom@gmail.com")
    @Size(max = 7, message = " max size %s")
    private String email;

    @ApiModelProperty(example = "+998977777777")
    @Size(max = 13, message = " max size %s")
    private String phoneNumber;

    @ApiModelProperty(hidden = true)
    private Organization organization;

}
