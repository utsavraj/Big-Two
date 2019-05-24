/**
 * This public abstract interface is for two instance methods - isValid() and getType() 
 * Runs and checks everytime.
 * 
 * @author utsav
 *
 */
 
public interface Abstract_Interface {
	
	/**
	 * Hand is valid or not.
	 * 
	 * @return True only if Hand is valid.
	 * 
	 */
		
	public abstract boolean isValid();
	
	
	
	/**
	* What type of Hand eg. Pair, Single.
	* 
	* @return Type of Hand.
	* 
	*/
	
	public abstract String getType();

}