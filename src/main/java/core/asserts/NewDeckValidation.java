package core.asserts;

import org.testng.Assert;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class NewDeckValidation implements IValidations {
	
	Response newDeckResponse;

	public NewDeckValidation(Response res) {
		this.newDeckResponse = res;
	}
	
	public void withJockers() {
		newDeckResponse.then().body("remaining", is(54));
	}
	
	public void withOutJockers() {
		newDeckResponse.then().body("remaining", is(52));
	}
	
	
	@Override
	public void success() {
		Assert.assertEquals(newDeckResponse.statusCode(), 200);
	}

	@Override
	public void notFound() {
		Assert.assertEquals(newDeckResponse.statusCode(), 404);
	}

	@Override
	public void statusCode(int responseCode) {
		Assert.assertEquals(newDeckResponse.statusCode(), responseCode);
	}

	@Override
	public void schema() {
		newDeckResponse.then().assertThat().body(matchesJsonSchemaInClasspath("schema_matchers/new_deck.json"));
	}

	@Override
	public void validateSLA() {
		long sla = 5000L; // TODO get this value from properties files
		Assert.assertTrue(newDeckResponse.getTime() < sla);
	}

}
