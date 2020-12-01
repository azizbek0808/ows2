package uz.com.dto.organization;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.hibernate.domain.organization.Organization;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationContactDto extends GenericDto {

    private String email;
    private String phoneNumber;


    @Builder(builderMethodName = "childBuilder")
    public OrganizationContactDto(Long id, String email, String phoneNumber) {
        super(id);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
