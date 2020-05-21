package core.tests;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.asserts.DrawCardPilesValidation;
import core.library.CardsClient;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;


public class DrawFromPilesTests {
	CardsClient cc;
	String cardDeck;
		
	public String[] createCardPiles(String deck, int count, String pileName) {
		Response res_draw = cc.get("/"+deck+"/draw/?count="+count);
		JSONObject jObject  = new JSONObject(res_draw.body().asString());
		String cards="";
		for(int i=0;i<count;i++) {
			cards = cards+jObject.getJSONArray("cards").getJSONObject(i).getString("code")+",";
		}
		cc.get("/"+deck+"/pile/"+pileName+"/add/?cards="+cards);
		return cards.split(",");
	}
	
	@BeforeClass
	public void setUP() {
		cc = new CardsClient("qa");
		Map<String, Object> deck = cc.get("/new/").as(new TypeRef<Map<String, Object>>(){});
		cardDeck = (String) deck.get("deck_id");
		
		createCardPiles(cardDeck, 4, "player2");
	}
	
	@Test
	public void drawCards() {
		String[] cards = createCardPiles(cardDeck, 2, "player1");
		Map<String, String> parameters = new HashMap<>();
		parameters.put("cards", cards[0]);
		
		Response res = cc.get("/"+this.cardDeck+"/pile/player1/draw/", parameters);
		System.out.println(res.body().asString());
		DrawCardPilesValidation Check = new DrawCardPilesValidation(res);
		Check.successMessage();
		Check.success();
		Check.schema();
		Check.deckId(cardDeck);
		Check.validateSLA();
	}
	
	@Test
	public void drawCount() {
		createCardPiles(cardDeck, 6, "player2");
		Map<String, String> parameters = new HashMap<>();
		parameters.put("count", "5");
		
		Response res = cc.get("/"+this.cardDeck+"/pile/player2/draw/", parameters);

		DrawCardPilesValidation Check = new DrawCardPilesValidation(res);
		Check.successMessage();
		Check.success();
		Check.schema();
		Check.deckId(cardDeck);
		Check.validateSLA();
	}
	
	@Test
	public void drawFromBotton() {
		createCardPiles(cardDeck, 3, "player3");
		Response res = cc.get("/"+this.cardDeck+"/draw/bottom/");

		DrawCardPilesValidation Check = new DrawCardPilesValidation(res);
		Check.successMessage();
		Check.success();
		Check.schema();
		Check.deckId(cardDeck);
		Check.validateSLA();
	}
}
