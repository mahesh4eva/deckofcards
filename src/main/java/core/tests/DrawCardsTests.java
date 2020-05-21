package core.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.asserts.DrawDeckValidation;
import core.library.CardsClient;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

public class DrawCardsTests {
	
	CardsClient cc;
	String cardDeck;
		
	@BeforeClass
	public void setUP() {
		cc = new CardsClient("qa");
	}
	
	@Test
	public void drawSingleCardFromNewDeck() {
		//Create a new deck to get deck_id
		Map<String, Object> deck = cc.get("/new/").as(new TypeRef<Map<String, Object>>(){});
		cardDeck = (String) deck.get("deck_id");
		
		Response res = cc.get("/"+cardDeck+"/draw");

		DrawDeckValidation Check = new DrawDeckValidation(res);
		Check.successMessage();
		Check.success();
		Check.schema();
		Check.deckId(cardDeck);
		Check.validateSLA();
	}
	
	@Test(dependsOnMethods = { "drawSingleCardFromNewDeck" })
	public void drawSingleCardFromExistingDeck() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("count", "5");
		
		Response res = cc.get("/"+this.cardDeck+"/draw", parameters);

		DrawDeckValidation Check = new DrawDeckValidation(res);
		Check.successMessage();
		Check.success();
		Check.schema();
		Check.deckId(cardDeck);
		Check.validateSLA();
	}
}
