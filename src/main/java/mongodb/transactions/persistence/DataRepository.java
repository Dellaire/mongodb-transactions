package mongodb.transactions.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import mongodb.transactions.model.Data;

@Component
public interface DataRepository extends MongoRepository<Data, String> {

}
