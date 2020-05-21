package core.tests;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.asserts.NewDeckValidation;
import core.library.CardsClient;
import io.restassured.response.Response;

public class NewDeckTests {
	
	CardsClient cc;
	
	
	@BeforeClass
	public void setUP() {
		cc = new CardsClient("qa");
	}
	
	@Test
	public void createNewDeck_no_parameters() {
		Response res = cc.get("/new/");

		NewDeckValidation Check = new NewDeckValidation(res);
		Check.withOutJockers();
		Check.success();
		Check.schema();
		Check.validateSLA();
	}
	
	@Test
	public void createNewDeck_with_jockers() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("jokers_enabled", "true");
		Response res = cc.get("/new/", parameters);
		
		NewDeckValidation Check = new NewDeckValidation(res);
		Check.withJockers();
		Check.success();
		Check.schema();
		Check.validateSLA();
	}
	
	@Test
	public void createNewDeck_with_out_jockers() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("jokers_enabled", "false");
		Response res = cc.get("/new/", parameters);
		
		NewDeckValidation Check = new NewDeckValidation(res);
		Check.withOutJockers();
		Check.success();
		Check.schema();
		Check.validateSLA();
	}

	@Test
	//POST method requires a CSRF cookie (authentication)
	//I could not able to find documentation on how to generate one.
	public void createNewDeck_with_POST() {
		Map<String, Object> data = new HashMap<>();
		data.put("jokers_enabled", true);
		Response res = cc.post("/new/", data);
		
		NewDeckValidation Check = new NewDeckValidation(res);
		Check.success();
		Check.schema();
		Check.validateSLA();
	}
}
