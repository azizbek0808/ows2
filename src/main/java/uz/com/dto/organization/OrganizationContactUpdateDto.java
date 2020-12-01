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
@ApiModel(value = "Organization Contact update request")
public class OrganizationContactUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(example = "salom@gmail.com")
    @Size(max = 7, message = " max size %s")
    private String email;

    @ApiModelProperty(example = "+998977777777")
    @Size(max = 13, message = " max size %s")
    private String phoneNumber;


}
