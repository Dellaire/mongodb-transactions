package mongodb.transactions;

import mongodb.transactions.persistence.DataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class ImplicitTransactionTest {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private TransactionalService transactionalService;

    @Test
    public void doNotInsertDataIfErrorOccurs() {

        this.dataRepository.deleteAll();

        try {
            this.transactionalService.writeData(() -> {
                throw new RuntimeException();
            });
        } catch (Exception exception) {
        }

        assertThat(this.dataRepository.count(), is(0L));
    }

    @Test
    public void insertDataIfNErrorOccurs() {

        this.dataRepository.deleteAll();

        this.transactionalService.writeData(() -> "");

        assertThat(this.dataRepository.count(), is(1L));
    }
}
