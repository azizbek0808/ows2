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
@ApiModel(value = "Type update request")
public class TypeUpdateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String name;

    @ApiModelProperty(required = false)
    @Size(max = 200, message = " max size %s")
    private String nameRu;

    @ApiModelProperty(required = false)
    @Size(max = 200, message = " max size %s")
    private String nameEn;

    @ApiModelProperty(required = false)
    @Size(max = 200, message = " max size %s")
    private String nameUz;

    @Size(max = 100, message = " max size %s")
    private String typeCode;

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String value;
}
