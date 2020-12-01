package uz.com.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.enums.State;
import uz.com.hibernate.domain.files.ResourceFile;
import uz.com.hibernate.domain.settings.Language;
import uz.com.hibernate.domain.settings.Type;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends GenericDto {

    private String firstName;

    private String lastName;

    private String middleName;

    private String shortName;

    private String username;

    private String socialPhoneNumber;

    private String birthDate;

    private Long ordering;

    private Boolean isOnline;

    private Long chatId;

    private Type supplyDepartmentType;

    private Type positionType;

    private Language language;

    private ResourceFile resourceFile;

    private State state;

    private List<RoleDto> roles;

    @ApiModelProperty(hidden = true)
    private boolean locked;

    @ApiModelProperty(hidden = true)
    private boolean systemAdmin;

    @Builder(builderMethodName = "childBuilder")
    public UserDto(Long id, String firstName, String lastName, String middleName, String shortName, String username, String socialPhoneNumber, String birthDate, Long ordering, Boolean isOnline, Long chatId, Type supplyDepartmentType, Type positionType, Language language, ResourceFile resourceFile, State state, List<RoleDto> roles, boolean locked, boolean systemAdmin) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.shortName = shortName;
        this.username = username;
        this.socialPhoneNumber = socialPhoneNumber;
        this.birthDate = birthDate;
        this.ordering = ordering;
        this.isOnline = isOnline;
        this.chatId = chatId;
        this.supplyDepartmentType = supplyDepartmentType;
        this.positionType = positionType;
        this.language = language;
        this.resourceFile = resourceFile;
        this.state = state;
        this.roles = roles;
        this.locked = locked;
        this.systemAdmin = systemAdmin;
    }
}
