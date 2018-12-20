package mongodb.transactions.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import mongodb.transactions.model.Two;

@Component
public interface TwoRepository extends MongoRepository<Two, String> {

}
