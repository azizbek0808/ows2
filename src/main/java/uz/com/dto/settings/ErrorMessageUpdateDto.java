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
@Builder
@ApiModel(value = "Error message update request")
public class ErrorMessageUpdateDto extends GenericCrudDto {
    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true)
    private String errorCode;

    @ApiModelProperty(required = true)
    private List<ErrorMessageTranslationCreateDto> translations;
}
