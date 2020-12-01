package uz.com.dto.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericDto;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDto extends GenericDto {
    private String errorCode;
    private List<ErrorMessageTranslationDto> translations;
}
