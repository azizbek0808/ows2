package uz.com.dto.settings;

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
@ApiModel(value = "Business Direction create request")
public class BusinessDirectionCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String name;

    @Size(max = 100, message = " max size %s")
    private String codeName;

}
