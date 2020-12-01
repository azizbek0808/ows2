package uz.com.criteria.main;

import lombok.*;
import uz.com.criteria.GenericCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCriteria extends GenericCriteria {

    private String number;

    @Builder(builderMethodName = "childBuilder")
    public ApplicationCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String number) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.number = number;
    }
}
