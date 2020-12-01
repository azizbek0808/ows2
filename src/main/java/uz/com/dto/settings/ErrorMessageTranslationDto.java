package uz.com.dto.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageTranslationDto extends GenericDto {
    private Long messageId;
    private String name;
    private LanguageDto language;
}
