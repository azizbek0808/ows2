package uz.com.criteria.organization;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCriteria extends GenericCriteria {

    private String name;

    private String nick;

    private String inn;

    private Long businessDirectionId;


    @Builder(builderMethodName = "childBuilder")
    public OrganizationCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String name, String nick, String inn, Long businessDirectionId) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.name = name;
        this.nick = nick;
        this.inn = inn;
        this.businessDirectionId = businessDirectionId;
    }
}
