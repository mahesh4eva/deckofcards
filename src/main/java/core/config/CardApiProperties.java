package core.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
* This class implements the logic to read configuration parameters for tests
*
* @author  BhimaRaju Mavuri
* @version 1.0
* @since   05/20/2020 
*/
public class CardApiProperties {

	InputStream inputStream;
	public Properties prop;
	
   /**
   * This constructor take a string parameter (filename) and updates prop variable
   * @param fileName Name of the file at resources folder
   * @return void
   */
	public CardApiProperties(String fileName){
		prop = new Properties();
		String envConfig = fileName;
		inputStream = getClass().getClassLoader().getResourceAsStream(envConfig);
		try {
			if(inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + envConfig + "' not found in the classpath");
			}
			
		} catch (Exception e) {
			System.out.println("Exception is "+e.getMessage());
		}
	}
}
