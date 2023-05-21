//package tacos.model;

//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.domain.Persistable;
//import org.springframework.data.relational.core.mapping.Table;
//
//@Data
//@Table
//@AllArgsConstructor
//@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
//public class Ingredient implements Persistable<String> {
//    @Id
//    private String id;
//
//    private String name;
//    private Type type;
//
//    @Override
//    public boolean isNew(){
//        return true;
//    }
//
//    public enum Type{
//        WRAP, PROTEIN, VEGGIES
//    }
//}


package tacos.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Entity
public class Ingredient{

    @Id
    private final String id;
    private final String name;
    private final Type type;

    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
