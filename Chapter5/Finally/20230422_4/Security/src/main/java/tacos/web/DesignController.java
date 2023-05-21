package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.model.Ingredient;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tacos.model.Ingredient.Type;
import tacos.model.Taco;
import tacos.model.TacoOrder;
import tacos.model.User;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;
import tacos.repository.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/design")
@Slf4j
@SessionAttributes("tacoOrder")    //Object put in model be maintain in session
public class DesignController {

    private final IngredientRepository ingredientRepo;

    private TacoRepository tacoRepo;

    private UserRepository userRepo;

    @Autowired
    public DesignController(
            IngredientRepository ingredientRepo,
            TacoRepository tacoRepo,
            UserRepository userRepo){
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String showDesignForm(){
        return "designForm";
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("PORK", "Pork Protein", Type.PROTEIN),
//                new Ingredient("BROC", "Broccoli", Type.VEGGIES),
//                new Ingredient("PUMP", "Pumpkin", Type.VEGGIES)
//        );
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));


        Type[] types = Ingredient.Type.values();

        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal){
        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        return user;
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco,
            Errors errors,
            @ModelAttribute TacoOrder tacoOrder){
        log.info("      --- Saving taco");

        if(errors.hasErrors()){
            return "designForm";
        }

//      Author changed a lot of codes on the sneak and didn't mention them.
        Taco saved = tacoRepo.save(taco);
        tacoOrder.addTaco(saved);

//        tacoOrder.addTaco(taco);

//        log.info("Processing taco:{}", taco);
//
        return "redirect:/orders/current";
    }
}


