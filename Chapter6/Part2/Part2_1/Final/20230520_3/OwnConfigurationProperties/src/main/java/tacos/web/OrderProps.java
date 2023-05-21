package tacos.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component
@ConfigurationProperties(prefix="taco.orders")
@Data
public class OrderProps {
    @Min(value=2, message="must be between 2 and 4")
    @Max(value=4, message="must be between 2 and 4")
    private int pageSize = 3;
}
