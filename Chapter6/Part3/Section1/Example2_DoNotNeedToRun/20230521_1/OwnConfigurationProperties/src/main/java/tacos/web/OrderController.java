//package tacos.web;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.support.SessionStatus;
//import tacos.model.TacoOrder;
//import tacos.repository.OrderRepository;
//
//import javax.validation.Valid;
//
//@Slf4j
//@Controller
//@RequestMapping("/orders")
//@SessionAttributes("tacoOrder")
//public class OrderController {
//
//    private OrderRepository orderRepo;
//
//    public OrderController(OrderRepository orderRepo){
//        this.orderRepo = orderRepo;
//    }
//
//    @GetMapping("/current")
//    public String orderForm(){
//        return "orderForm";
//    }
//
////    @PostMapping
////    public String processOrder(
////            @Valid TacoOrder tacoOrder,
////            Errors errors,
////            SessionStatus sessionStatus){
////
////        if(errors.hasErrors()){
////            return "orderForm";
////        }
////
////        log.info("Order submitted: {}", tacoOrder);
////        sessionStatus.setComplete();
////
////        return "redirect:/";
////    }
//    @PostMapping
//    public String processOrder(
//            @Valid TacoOrder tacoOrder,
//            Errors errors,
//            SessionStatus sessionStatus){
//
//        if(errors.hasErrors()){
//            return "orderForm";
//        }
//
//        orderRepo.save(tacoOrder);
//        sessionStatus.setComplete();
//
//        return "redirect:/";
//    }
//}

package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
//@ConfigurationProperties(prefix="taco.orders")
public class OrderController {

    private OrderRepository orderRepo;

    /**
     * yml 文件中的pageSize 配置优先级,
     * 比这个类中的pageSize属性,
     * 优先级更大
     */
//    private int pageSize = 20;
//
//    public void setPageSize(int pageSize){
//        this.pageSize = pageSize;
//    }

//    public OrderController(OrderRepository orderRepo){
//        this.orderRepo = orderRepo;
//    }


    /**
     * Configuration property holders
     */
    private OrderProps props;
    public OrderController(OrderRepository orderRepo,
                           OrderProps props){
        this.orderRepo = orderRepo;

        this.props = props;
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


    /**
     * Showing bean consume configurations
     */
    @GetMapping
    public String orderForUser(@AuthenticationPrincipal User user,
                               Model model){
//        Pageable pageable = PageRequest.of(0, 20);
//        Pageable pageable = PageRequest.of(0, pageSize);
        Pageable pageable = PageRequest.of(0, props.getPageSize());

        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }

}




