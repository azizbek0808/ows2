package uz.com.hibernate.base;

import lombok.*;
import org.hibernate.search.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditInfo implements Serializable {

    @SortableField
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @DateBridge(resolution = Resolution.MILLISECOND, encoding = EncodingType.STRING)
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @SortableField
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @DateBridge(resolution = Resolution.MILLISECOND, encoding = EncodingType.STRING)
    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;

    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
    @Column(name = "created_by")
    private Long createdBy;

    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
    @Column(name = "updated_by")
    private Long updatedBy;

}
