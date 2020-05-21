package core.library;

import java.util.Map;
import core.config.CardApiProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
* This class implements basic api client
*
* @author  BhimaRaju Mavuri
* @version 1.0
* @since   05/20/2020 
*/
public class CardsClient {
	
	private CardApiProperties cap;
	private String url;
	
	
   /**
   * This constructor take a string parameter (environment) and fetches properties from file
   * @param environment Name of the environment
   * @return void
   */
	public CardsClient(String environement) {
		cap = new CardApiProperties(environement+"_env.properties");
		url = cap.prop.getProperty("baseURL")+cap.prop.getProperty("commonURI");
	}
   /**
   * This method sends a GET request to server and returns the Response object
   * @param URI uri of the service
   * @return Response restAssured response object
   */
	public Response get(String URI) {
		return RestAssured.get(url+URI);
	}
	
   /**
   * This method sends a GET request with parameters to server and returns the Response object
   * @param URI uri of the service
   * @param parameters Key, Value pairs of data (Map)
   * @return Response restAssured response object
   */
	public Response get(String URI, Map<String, String> parameters) {
		return RestAssured.given().params(parameters).get(url+URI);
	}

   /**
   * This method sends a POST request with parameters to server and returns the Response object
   * @param URI uri of the service
   * @param body JSON representation of body
   * @return Response restAssured response object
   */
	public Response post(String URI, Map<String, Object> body) {
		return RestAssured.given().contentType(ContentType.JSON).body(body).
				post(url+URI);
	}
	
}
