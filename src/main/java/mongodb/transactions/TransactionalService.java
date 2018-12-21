package mongodb.transactions;

import mongodb.transactions.model.Data;
import mongodb.transactions.persistence.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

/**
 * This class demonstrates transactional behaviour.
 */
@Component
public class TransactionalService {

    @Autowired
    private DataRepository dataRepository;

    /**
     * This method inserts test data in a transactional scope. If an error occurs, the inserts will be reverted.
     *
     * @param errorSource may or may not cause an error
     */
    @Transactional
    public void writeData(Supplier<String> errorSource) {

        this.dataRepository.insert(new Data());
        errorSource.get();
    }
}
