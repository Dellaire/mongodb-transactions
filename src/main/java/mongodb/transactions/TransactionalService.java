package mongodb.transactions;

import mongodb.transactions.model.Data;
import mongodb.transactions.persistence.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Component
public class TransactionalService {

    @Autowired
    private DataRepository dataRepository;

    @Transactional
    public void writeData(Supplier<String> errorSource) {

        this.dataRepository.insert(new Data());
        errorSource.get();
    }
}
