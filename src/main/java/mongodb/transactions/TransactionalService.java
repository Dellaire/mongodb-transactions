package mongodb.transactions;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;

import mongodb.transactions.model.Data;
import mongodb.transactions.persistence.DataRepository;

/**
 * This class demonstrates transactional behaviour.
 */
@Component
public class TransactionalService {

	@Autowired
	private DataRepository dataRepository;

	@Autowired
	private MongoClient mongoClient;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * This method inserts test data in an implicitly transactional scope. If an
	 * error occurs, the inserts will be reverted.
	 *
	 * @param errorSource
	 *            may or may not cause an error
	 */
	@Transactional
	public void writeDataImplicitlyTransactional(Supplier<String> errorSource) {

		this.dataRepository.insert(new Data());
		errorSource.get();
	}

	/**
	 * Like {@link #writeDataImplicitlyTransactional(Supplier)}, but uses explicit
	 * transactions
	 *
	 * @param errorSource
	 *            may or may not cause an error
	 */
	public void writeDataExplicitlyTransactional(Supplier<String> errorSource) {

		ClientSessionOptions sessionOptions = ClientSessionOptions.builder().causallyConsistent(true).build();
		try (ClientSession session = this.mongoClient.startSession(sessionOptions)) {
			this.mongoTemplate.withSession(() -> session).execute(action -> {

				session.startTransaction();

				Data data = new Data();
				action.insert(data);
				errorSource.get();

				session.commitTransaction();

				return data;
			});
		} catch (Exception exception) {
		}
	}
}
