package uz.com.dto.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericCrudDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Application create request")
public class ApplicationCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private String number;
}
