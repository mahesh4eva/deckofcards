package core.tests;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.asserts.ShuffleDeckValidation;
import core.library.CardsClient;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

public class ShuffleCardsTests {
	CardsClient cc;
	String cardDeck;
	
	@BeforeClass
	public void setUP() {
		cc = new CardsClient("qa");
	}
	
	@Test
	public void newDeck_with_shuffle() {
		Response res = cc.get("/new/shuffle");
		Map<String, Object> deck = res.as(new TypeRef<Map<String, Object>>(){});
		cardDeck = (String) deck.get("deck_id");
		
		ShuffleDeckValidation Check = new ShuffleDeckValidation(res);
		Check.success();
		Check.schema();
		Check.validateSLA();
	}
	
	@Test(dependsOnMethods = { "newDeck_with_shuffle" })
	public void existingDeck_with_shuffle() {
		Response res = cc.get("/"+cardDeck+"/shuffle");

		ShuffleDeckValidation Check = new ShuffleDeckValidation(res);
		Check.success();
		Check.schema();
		Check.validateSLA();
	}
}
