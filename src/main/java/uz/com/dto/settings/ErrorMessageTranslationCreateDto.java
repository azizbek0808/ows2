package uz.com.dto.settings;

import lombok.*;
import uz.com.dto.GenericCrudDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessageTranslationCreateDto extends GenericCrudDto {
    private String name;
    private String langCode;

}
