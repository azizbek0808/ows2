package uz.com.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Change Password request ")
public class ChangePasswordDto {

    @ApiModelProperty(required = true)
    private Long userId;

    private String currentPassword;

    @ApiModelProperty(required = true)
    private String newPassword;
}
