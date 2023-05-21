package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.model.TacoOrder;
import tacos.model.User;
import tacos.repository.OrderRepository;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo){
        this.orderRepo = orderRepo;
    }

//    @GetMapping("/current")
//    public String showOrderForm(){
//        return "orderForm";
//    }

//      Author changed a lot of codes on the sneak
//      and didn't mention them.
    @GetMapping("/current")
    public String showOrderForm(
            @AuthenticationPrincipal User user,
            @ModelAttribute TacoOrder order){
        if(order.getDeliveryName() == null){
            order.setDeliveryName(user.getFullname());
        }

        if(order.getDeliveryStreet() == null){
            order.setDeliveryStreet(user.getStreet());
        }

        if(order.getDeliveryCity() == null){
            order.setDeliveryCity(user.getCity());
        }

        if(order.getDeliveryState() == null){
            order.setDeliveryState(user.getState());
        }

        if(order.getDeliveryZip() == null){
            order.setDeliveryZip(user.getZip());
        }

        return "orderForm";
    }


    @PostMapping
    public String processOrder(
            @Valid TacoOrder order,
            Errors errors,
            SessionStatus sessionStatus,
            @AuthenticationPrincipal User user){

        if(errors.hasErrors()){
            return "orderForm";
        }

//      Author changed a lot of codes on the sneak
//      and didn't mention them.
        order.setUser(user);



//        log.info("Order submitted: {}", order);
//        sessionStatus.setComplete();
        orderRepo.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
