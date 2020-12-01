package uz.com.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericCrudDto;
import uz.com.hibernate.domain.files.ResourceFile;
import uz.com.hibernate.domain.settings.Language;
import uz.com.hibernate.domain.settings.Type;

import javax.validation.constraints.Size;
import java.util.function.LongToDoubleFunction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User create request")
public class UserCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String firstName;

    @ApiModelProperty(required = true)
    @Size(max = 100, message = " max size %s")
    private String lastName;

    @Size(max = 100, message = " max size %s")
    private String middleName;

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String password;

    @ApiModelProperty(example = "+998977777777")
    @Size(max = 13, message = " max size %s")
    private String socialPhoneNumber;

    @ApiModelProperty(example = "20.20.2020", required = false)
    private String birthDate;

    @ApiModelProperty(required = true)
    private Long ordering;

    @ApiModelProperty(required = true)
    private Long supplyDepartmentTypeId;

    @ApiModelProperty(required = true)
    private Long positionTypeId;

    @ApiModelProperty(required = false)
    private Long languageId;

    @ApiModelProperty(required = false)
    private Long resourceFileId;


}
