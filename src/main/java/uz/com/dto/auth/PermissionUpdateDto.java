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
@ApiModel(value = "Permission update request")
public class PermissionUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(value = "CODE", required = true)
    private String code;

    private Long parentId;

    private TranslationDto translations;
}
