package uz.com.hibernate.dao;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionParam {

    private Object param;
    private int paramType;

}
