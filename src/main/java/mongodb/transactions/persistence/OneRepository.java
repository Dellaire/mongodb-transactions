package mongodb.transactions.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import mongodb.transactions.model.One;

@Component
public interface OneRepository extends MongoRepository<One, String> {

}
