package uz.com.dto.settings;

import lombok.*;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDirectionDto extends GenericDto {

    private String name;
    private String codeName;

    @Builder(builderMethodName = "childBuilder")
    public BusinessDirectionDto(Long id, String name, String codeName) {
        super(id);
        this.name = name;
        this.codeName = codeName;
    }
}
