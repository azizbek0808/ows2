package uz.com.dto.auth;

import lombok.*;
import uz.com.dto.GenericDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends GenericDto {

    private String code;

    private String name;
    private String nameRu;
    private String nameEn;
    private String nameUz;

    private String createdDate;

    private List<PermissionDto> permissions;

    @Builder(builderMethodName = "childBuilder")
    public RoleDto(Long id, String code, String name, String nameRu, String nameEn, String nameUz, String createdDate, List<PermissionDto> permissions) {
        super(id);
        this.code = code;
        this.name = name;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameUz = nameUz;
        this.createdDate = createdDate;
        this.permissions = permissions;
    }
}
