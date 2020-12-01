package uz.com.dto.organization;

import lombok.*;
import uz.com.dto.GenericDto;
import uz.com.dto.auth.UserDto;
import uz.com.hibernate.domain.auth.User;
import uz.com.hibernate.domain.organization.Organization;
import uz.com.hibernate.domain.organization.OrganizationAddress;
import uz.com.hibernate.domain.organization.OrganizationContact;
import uz.com.hibernate.domain.organization.OrganizationMfo;
import uz.com.hibernate.domain.settings.BusinessDirection;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto extends GenericDto {

    private String name;
    private String nick;
    private String inn;
    private Boolean forObjectName;
    private UserDto financierUser;
    private List<OrganizationAddressDto> addresses;
    private List<OrganizationContactDto> contacts;
    private List<OrganizationMfoDto> mfos;



    @Builder(builderMethodName = "childBuilder")
    public OrganizationDto(Long id, String name, String nick, String inn, Boolean forObjectName, UserDto financierUser, List<OrganizationAddressDto> addresses, List<OrganizationContactDto> contacts, List<OrganizationMfoDto> mfos) {
        super(id);
        this.name = name;
        this.nick = nick;
        this.inn = inn;
        this.forObjectName = forObjectName;
        this.financierUser = financierUser;
        this.addresses = addresses;
        this.contacts = contacts;
        this.mfos = mfos;
    }
}
