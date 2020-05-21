package core.tests;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.asserts.ListCardsValidations;
import core.library.CardsClient;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

public class ListCardsTests {
	CardsClient cc;
	String cardDeck;
		
	public void createCardPiles() {
		Map<String, String> testData = new HashMap<>();
		testData.put("Don", "AS,2S");
		testData.put("John", "5C,KS,0H");
		
		for(Map.Entry<String, String> data: testData.entrySet()) {
			cc.get("/"+cardDeck+"/pile/"+data.getKey()+"/add/?cards="+data.getValue());
		}
	}
	
	@BeforeClass
	public void setUP() {
		cc = new CardsClient("qa");
		Map<String, Object> deck = cc.get("/new/").as(new TypeRef<Map<String, Object>>(){});
		cardDeck = (String) deck.get("deck_id");
		createCardPiles();
	}
	
	@Test
	public void listCardsInPiles() {
		Response res = cc.get("/"+cardDeck+"/pile/Don/list");
		
		ListCardsValidations Check = new ListCardsValidations(res);
		Check.successMessage();
		Check.deckId(cardDeck);
		Check.success();
		Check.schema();
		Check.validateSLA();
	}
}
