package uz.com.dto.main;

import lombok.*;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto extends GenericDto {

    private String number;

    @Builder(builderMethodName = "childBuilder")
    public ApplicationDto(Long id, String number) {
        super(id);
        this.number = number;
    }
}
