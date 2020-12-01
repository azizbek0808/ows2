package uz.com.dto.settings;

import lombok.*;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto extends GenericDto {

    private String name;
    private String nameRu;
    private String nameEn;
    private String nameUz;
    private String typeCode;
    private String value;


    @Builder(builderMethodName = "childBuilder")
    public TypeDto(Long id, String name, String nameRu, String nameEn, String nameUz, String typeCode, String value) {
        super(id);
        this.name = name;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.nameUz = nameUz;
        this.typeCode = typeCode;
        this.value = value;
    }
}
