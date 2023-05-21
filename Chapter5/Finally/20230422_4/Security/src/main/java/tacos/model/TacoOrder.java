package tacos.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="Taco_Order")
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    private Date placedAt = new Date();
    private Date placedAt;

    @ManyToOne
    private User user;

    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @NotBlank(message="Delivery Street is required")
    private String deliveryStreet;

    @NotBlank(message="Delivery City is required")
    private String deliveryCity;

    @NotBlank(message="Delivery State is required")
    private String deliveryState;

    @NotBlank(message="Delivery Zip code is required")
    private String deliveryZip;

    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

    /**
     * 这里一定不能用原本的   “@OneToMany”,
     * 如果用“@OneToMany”则会无法正确获取Order的 Id 和 placedAt 的值，
     * 最终导致 orderForm.html Post后报错
     * ————————[Request processing failed;
     * nested exception is org.springframework.dao.InvalidDataAccessApiUsageException:
     * detached entity passed to persist: tacos.model.Taco; nested exception is
     * org.hibernate.PersistentObjectException: detached entity passed to persist:
     * tacos.model.Taco] with root cause
     */
//      Author changed a lot of codes on the sneak
//      and didn't mention them.
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Taco> tacos = new ArrayList<>();
    @ManyToMany(targetEntity=Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }

//      Author changed a lot of codes on the sneak
//      and didn't mention them.
    @PrePersist
    void placedAt(){
        this.placedAt = new Date();
    }

}
