package core.asserts;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import org.testng.Assert;
import io.restassured.response.Response;

public class DrawDeckValidation implements IValidations {

	Response response;
	
	public DrawDeckValidation(Response res) {
		this.response = res;
	}
	
	public void successMessage() {
		response.then().body("success", is(true));
	}
	
	public void deckId(String deck_id) {
		response.then().body("deck_id", is(deck_id));
	}
	
	@Override
	public void success() {
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Override
	public void notFound() {
		Assert.assertEquals(response.statusCode(), 404);
	}

	@Override
	public void statusCode(int responseCode) {
		Assert.assertEquals(response.statusCode(), responseCode);
	}

	@Override
	public void schema() {
		response.then().assertThat().body(matchesJsonSchemaInClasspath("schema_matchers/draw_deck.json"));
	}

	@Override
	public void validateSLA() {
		long sla = 5000L; // TODO get this value from properties files
		Assert.assertTrue(response.getTime() < sla);
	}

}
