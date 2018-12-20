package mongodb.transactions;

import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import mongodb.transactions.model.Data;
import mongodb.transactions.persistence.DataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class TransactionTest {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DataRepository dataRepository;

    @Test
    public void doNotChangeDataIfTransactionIsNotCommitted() {

        this.dataRepository.deleteAll();

        ClientSessionOptions sessionOptions = ClientSessionOptions.builder().causallyConsistent(true).build();
        ClientSession session = this.mongoClient.startSession(sessionOptions);

        this.mongoTemplate.withSession(() -> session)
                .execute(action -> {

                    session.startTransaction();

                    Data data = new Data();
                    action.insert(data);

                    return data;
                });

        session.close();

        assertThat(this.dataRepository.count(), is(0L));
    }

    @Test
    public void changeDataIfTransactionIsCommitted() {

        this.dataRepository.deleteAll();

        ClientSessionOptions sessionOptions = ClientSessionOptions.builder().causallyConsistent(true).build();
        ClientSession session = this.mongoClient.startSession(sessionOptions);

        this.mongoTemplate.withSession(() -> session)
                .execute(action -> {

                    session.startTransaction();

                    Data data = new Data();
                    action.insert(data);

                    session.commitTransaction();

                    return data;
                });

        session.close();

        assertThat(this.dataRepository.count(), is(1L));
    }
}
