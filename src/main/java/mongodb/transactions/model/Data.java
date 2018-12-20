package mongodb.transactions.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Data {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
