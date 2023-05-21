package tacos.web.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.model.Ingredient;
import tacos.repository.IngredientRepository;

@Component
public class IngredientByIdConvert implements Converter<String, Ingredient> {
    //private Map<String, Ingredient> ingredientMap = new HashMap<>();

//    public IngredientByIdConvert(){
//        ingredientMap.put("FLTO",
//                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
//        ingredientMap.put("COTO",
//                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
//        ingredientMap.put("GRBF",
//                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
//        ingredientMap.put("PORK",
//                new Ingredient("PORK", "Pork Protein", Ingredient.Type.PROTEIN));
//        ingredientMap.put("BROC",
//                new Ingredient("BROC", "Broccoli", Ingredient.Type.VEGGIES));
//        ingredientMap.put("PUMP",
//                new Ingredient("PUMP", "Pumpkin", Ingredient.Type.VEGGIES));
//    }

//    public Ingredient convert(String id){
//        return ingredientMap.get(id);
//    }

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConvert(IngredientRepository ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id){
        return ingredientRepo.findById(id).orElse(null);
    }
}
