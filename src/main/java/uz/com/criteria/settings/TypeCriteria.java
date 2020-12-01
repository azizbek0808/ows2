package uz.com.criteria.settings;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeCriteria extends GenericCriteria {

    private String name;

    private String typeCode;

    private String value;

    @Builder(builderMethodName = "childBuilder")
    public TypeCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String name, String typeCode, String value) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.name = name;
        this.typeCode = typeCode;
        this.value = value;
    }
}
