package uz.com.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericCrudDto;
import uz.com.dto.TranslationDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Role create request")
public class RoleCreateDto extends GenericCrudDto {

    @ApiModelProperty(value = "CODE", required = true)
    private String code;

    private TranslationDto translations;
}
