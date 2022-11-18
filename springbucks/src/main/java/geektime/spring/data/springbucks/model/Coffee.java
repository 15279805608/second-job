package geektime.spring.data.springbucks.model;

import lombok.*;
import org.joda.money.Money;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coffee extends BaseEntity implements Serializable {
    private String name;
    private Money price;
}
