package core.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import core.asserts.PartialDeckValidation;
import core.library.CardsClient;
import io.restassured.response.Response;

public class PartialDeckTests {
	CardsClient cc;
		
	@BeforeClass
	public void setUP() {
		cc = new CardsClient("qa");
	}
	
	@Test
	public void createPartialDeck() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("cards", "AS,2S,KS,AD,2D,KD");
		
		Response res = cc.get("/new/shuffle/", parameters);
		PartialDeckValidation Check = new PartialDeckValidation(res);
		Check.success();
		Check.count(6); //we have created deck with 6 cards above
		Check.schema();
		Check.validateSLA();
	}
}
