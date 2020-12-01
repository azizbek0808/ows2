package uz.com.dto.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.com.dto.GenericCrudDto;
import uz.com.hibernate.domain.auth.User;
import uz.com.hibernate.domain.settings.BusinessDirection;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Organization create request")
public class OrganizationCreateDto extends GenericCrudDto {

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private String nick;

    @ApiModelProperty(required = true)
    private String inn;

    @ApiModelProperty(required = false)
    private Boolean forObjectName;

    @ApiModelProperty(required = false)
    private Long financierUserId;

    @ApiModelProperty(required = true)
    private Long businessDirectionId;

    private List<OrganizationAddressCreateDto> addresses;
    private List<OrganizationContactCreateDto> contacts;
    private List<OrganizationMfoCreateDto> mfos;


}
