package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.model.TacoOrder;

public interface OrderRepository
        extends CrudRepository<TacoOrder, Long> {
}
