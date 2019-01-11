package mongodb.transactions;

import mongodb.transactions.persistence.DataRepository;
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

	@Test
	public void doNotInsertDataIfErrorOccurs() {

		this.dataRepository.deleteAll();

		try {
			this.transactionalService.writeDataImplicitlyTransactional(() -> {
				throw new RuntimeException();
			});
		} catch (Exception exception) {
		}

		assertThat(this.dataRepository.count(), is(0L));
	}

	@Test
	public void insertDataIfNoErrorOccurs() {

		this.dataRepository.deleteAll();

		this.transactionalService.writeDataImplicitlyTransactional(() -> "");

		assertThat(this.dataRepository.count(), is(1L));
	}
}
