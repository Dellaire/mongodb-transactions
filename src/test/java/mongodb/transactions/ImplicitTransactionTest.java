package mongodb.transactions;

import mongodb.transactions.persistence.DataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringJUnitWebConfig(Application.class)
@SpringBootTest
public class ImplicitTransactionTest {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private TransactionalService transactionalService;

    @BeforeAll
    public static void startMongodb() {
        MongodbTestConfiguration.startMongodb();
    }

    @BeforeEach
    public void clearData() {
        this.dataRepository.deleteAll();
    }

    @Test
    public void doNotInsertDataIfErrorOccurs() {

        Assertions.assertThrows(RuntimeException.class,
                () -> this.transactionalService.writeDataImplicitlyTransactional(
                        () -> {
                            throw new RuntimeException();
                        }));

        assertThat(this.dataRepository.count(), is(0L));
    }

    @Test
    public void insertDataIfNoErrorOccurs() {

        this.transactionalService.writeDataImplicitlyTransactional(() -> "");

        assertThat(this.dataRepository.count(), is(1L));
    }
}
