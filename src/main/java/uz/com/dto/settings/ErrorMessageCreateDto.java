package uz.com.dto.settings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericCrudDto;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Error message create request")
@Builder
public class ErrorMessageCreateDto extends GenericCrudDto {
    @ApiModelProperty(required = true)
    private String errorCode;

    @ApiModelProperty(required = true)
    private List<ErrorMessageTranslationCreateDto> translations;
}
