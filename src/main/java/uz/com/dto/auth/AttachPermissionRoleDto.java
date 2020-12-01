package uz.com.dto.auth;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Attaching role To User request ")
public class AttachPermissionRoleDto {

    private Long roleId;
    private Long permissionId;
    private boolean attach;


}
