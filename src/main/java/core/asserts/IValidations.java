package core.asserts;

interface IValidations {
	
	public void success();
	public void notFound();
	public void statusCode(int responseCode);
	public void schema();
	public void validateSLA();
	
}
