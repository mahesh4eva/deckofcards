package core.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import core.asserts.CardPilesValidation;
import core.library.CardsClient;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

public class AddAndShufflePilesTests {
	CardsClient cc;
	String cardDeck;
	String pileName;
	
	@BeforeClass
	public void setUP() {
		cc = new CardsClient("qa");
		Map<String, Object> deck = cc.get("/new/").as(new TypeRef<Map<String, Object>>(){});
		cardDeck = (String) deck.get("deck_id");
	}
	
	@Test
	public void addToPile() {
		Faker faker = new Faker();

		Map<String, String> parameters = new HashMap<>();
		parameters.put("cards", "AS,2S");
		pileName = faker.name().firstName();
		Response res = cc.get("/"+cardDeck+"/pile/"+pileName+"/add/", parameters);
		
		CardPilesValidation Check = new CardPilesValidation(res);
		Check.successMessage();
		Check.deckId(cardDeck);
		Check.success();
		Check.schema();
		Check.validateSLA();
	}
	
	@Test
	public void shuffle_piles() {
		Response res = cc.get("/"+cardDeck+"/pile/"+pileName+"/shuffle/");
		
		CardPilesValidation Check = new CardPilesValidation(res);
		Check.successMessage();
		Check.deckId(cardDeck);
		Check.success();
		Check.schema();
		Check.validateSLA();
	}
}
