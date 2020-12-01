package uz.com.dto.auth;

import lombok.*;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto extends GenericDto {

    private String code;

    private Long parentId;

    private String name;
    private String nameRu;
    private String nameEn;
    private String nameUz;

    private String createdDate;

    @Builder(builderMethodName = "childBuilder")
    public PermissionDto(Long id, String code, Long parentId, String name, String nameRu, String nameEn, String nameUz, String createdDate) {
        super(id);
        this.code = code;
        this.parentId = parentId;
        this.name = name;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameUz = nameUz;
        this.createdDate = createdDate;
    }
}
