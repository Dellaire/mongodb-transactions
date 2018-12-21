package mongodb.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mongodb.transactions.model.One;
import mongodb.transactions.model.Two;
import mongodb.transactions.persistence.OneRepository;
import mongodb.transactions.persistence.TwoRepository;

@Component
public class TransactionRunner implements CommandLineRunner {

	@Autowired
	private OneRepository oneRepository;

	@Autowired
	private TwoRepository twoRepository;

	@Override
	public void run(String... args) throws Exception {

		this.transactionalMethod();
	}

	@Transactional
	private void transactionalMethod() {
		this.oneRepository.save(new One());

		if (true) {
			throw new RuntimeException();
		}

		this.twoRepository.save(new Two());
	}
}
