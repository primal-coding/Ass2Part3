/**
* Classical Change making problem with an unlimited amount of coins of each type. <br> 
* Version 2: Selection function with more elaborated policy: First biggest-coin.<br> 
* Depending on the type of coins, it can lead to an optimal solution.<br>
* The class encapsulates all the functions of the Greedy schema<br>
*/

public class ChangeMaking {

	//---------------------------------------
	//	Constructor
	//---------------------------------------
	/**
	 * Constructor of the class. Do not edit it.
	 */
	public ChangeMaking(){}

	
	// -------------------------------------------------------------------
	// 1. selectionFunctionFirstCandidate--> It selects the first candidate 
	// -------------------------------------------------------------------
	/**
	 * Given a current solution that is not a final solution, this function
	 * selects the new candidate to be added to it.<br>
	 * The policy followed is very simple: Just pick the first unused item.
	 * 
	 * @param candidates:
	 *            The MyList stating whether a candidate has been selected so
	 *            far or not.
	 * @return: The index of first candidate to be selected.
	 */
	public int selectionFunctionFirstCandidate(MyList<Integer> candidates) {
		if (candidates.length() == 0) return 0;		// to prevent the error of calling removeElement(null)
		// it has to be noted that elements can be retrieved from index 0 as well
		// as no detail has been given an arbitrary choice has been made 
		int res = candidates.getElement(0);
		candidates.removeElement(0);
		return res;
		//TO-DO
	}

		
	//-------------------------------------------------------------------
	// 1. selectionFunction --> It selects the next candidate to be considered.  
	//-------------------------------------------------------------------	
	/**
	 * Given a current solution that is not a final solution, this function selects the new candidate to be added to it.<br> 
	 * The policy followed is more elaborated: Pick the best coin according to the objective function of minimizing the number
	 * of coins that make the change of the amount. 
	 * @param candidates: List of candidates
	 * @return: The index of candidate to be selected.
	 */	
	public int selectionFunctionBestCandidate( MyList<Integer> candidates ){
		int res = 0;
		int remove = 0;
		for (int i = 0; i < candidates.length(); i++){
			if (res < candidates.getElement(i)) {
				remove = i;
				res = candidates.getElement(i);
			}
		}
		if (res > 0) candidates.removeElement((remove));
		return res;
			//TO-DO
	}
	
	//-------------------------------------------------------------------
	// 2. feasibilityTest --> It selects if a candidate can be added to the solution.  
	//-------------------------------------------------------------------	
	/**
	 * Given a current solution and a selected candidate, this function 
	 * states whether the candidate must be added to the solution or discarded.<br> 
	 * @param candidateValue: The value of the candidate coin selected. 
	 * @param amount: The amount of change we want to generate.
	 * @param changeGenerated: The quantity of change we have generated so far. 
		 * @return: Whether the candidate fits or not into the solution.
	 */	

	public boolean feasibilityTest(int candidateValue, int amount, int changeGenerated){
		if ((changeGenerated + candidateValue) > amount) return false;
		return true;
			//TO-DO
	}
	
	// -------------------------------------------------------------------
	// 5. solutionTest --> It selects if the current solution is the final
	// solution
	// -------------------------------------------------------------------
	/**
	 * Given a current solution, this function states whether it is a final
	 * solution or it can still be improved.<br>
	 * 
	 * 		// 
	 * To determine it, it checks whether there is (at least) one item not
	 * picked before that fits into the knapsack.
	 * 		// without getting the size of the knapstack it is going to be difficult to evaluate
	 * 
	 * @param nbCandidates:
	 *            number of candidates that have not been yet selected by the
	 *            selection function
	 * @return: Whether the current solution is the final solution.
	 */
	public boolean solutionTest(MyList<Integer> candidates) {
		// for (int i = 0; i < candidates.length(); i++){
		// 	if (candidates.getElement(i) - _AMOUNT_ >= 0 ) return true;
		// }
		// return false;

		if (candidates.length() == 0) return false;
		return true;
			//TO-DO
	}


	//-------------------------------------------------------------------
	//
	// 4. objectiveFunction --> This function computes the value of the final solution.  
	//
	//  >>> the amount of coins I assume <<< or >>> the sum value of all coins used? <<<  
	//
	//-------------------------------------------------------------------	
	/**
	 * Given the final solution to the problem, this function 
	 * computes its objective function value according to:<br>
	 * How many coins are used in the solution.<br> 					
	 * 
	 * // sol.length then? it seems too simple (and of no use in the solve function)
	 * 
	 * @param sol: The MyList containing the solution to the problem. 
	 * @return: The objective function value of such solution.
	 */	

	public int  objectiveFunction(MyList<Integer> sol){
		return sol.length();

		// this implementation seems more usefull for the solve function:
		// (by returning the changeGenerated)
		// int result = 0;
		// for (int i = 1; i < sol.length(); i++){
		// 	result += sol.getElement(i);
		// }
		// return result;
			//TO-DO
	}
	
	//-------------------------------------------------------------------
	// 5. solve --> This function solves the problem using a greedy algorithm.  
	//-------------------------------------------------------------------	
	/**
	 * Given an instance of the GP1 problem, this function solves it using 
	 * a greedy algorithm.<br> 
	 * @param typeSelectFunc:
	 *            Type of selection function to choose.
	 * @param coinValues: A MyList containing the value of each type of coin supported. 
	 * @param amount: The amount of change we want to generate.
	 * @return: A MyList containing the amount of coins of each type being selected.
	 */	
	public MyList<Integer> solve(int typeSelectFunc, MyList<Integer> coinValues, int amount){
		// scenario 1: no coin to solve the problem -> exit()
		if (coinValues.length() == 0) {
			System.out.println("ERROR. There is no coin to solve the problem.");
			return coinValues;
		}

		int amountOfCoinsGenerated = 0;
		int candidateNumber = 0;
		int changeGenerated = 0;
		int candidateValue = 0;
		int countCoinInUse = 0;
		MyList<Integer> sol = new MyDynamicList<Integer>();
		MyList<Integer> solValues = new MyDynamicList<Integer>();
		MyList<Integer> originalCoinValues = new MyDynamicList<Integer>();
		for (int i = 0; i < coinValues.length(); i++) originalCoinValues.addElement(i, coinValues.getElement(i));
		boolean loop = true;
		
		if (typeSelectFunc == 1){
			candidateValue = selectionFunctionFirstCandidate(coinValues);
			while (loop){
				if (feasibilityTest(candidateValue, amount, changeGenerated)){
					countCoinInUse++;
					changeGenerated += candidateValue;
				}
				else { 
					amountOfCoinsGenerated += countCoinInUse;
					solValues.addElement(candidateNumber,candidateValue);
					sol.addElement(candidateNumber,countCoinInUse);
					if (solutionTest(coinValues)) {
						candidateValue = selectionFunctionFirstCandidate(coinValues);
						candidateNumber++;
						countCoinInUse = 0;
					}
					else loop = false;
				}
			}

		}
		else {
			candidateValue = selectionFunctionBestCandidate(coinValues);
			while (loop){
				if (feasibilityTest(candidateValue, amount, changeGenerated)){
					countCoinInUse++;
					changeGenerated += candidateValue;
				}
				else { 
					amountOfCoinsGenerated += countCoinInUse;
					solValues.addElement(candidateNumber,candidateValue);
					sol.addElement(candidateNumber,countCoinInUse);
					if (solutionTest(coinValues)) {
						candidateValue = selectionFunctionBestCandidate(coinValues);
						candidateNumber++;
						countCoinInUse = 0;
					}
					else {
						loop = false;
					}
				}
			}
		}
		//TO-DO
		for (int i = 0; i < originalCoinValues.length(); i++) coinValues.addElement(i, originalCoinValues.getElement(i));
		System.out.println("Total amount of coins used: " + amountOfCoinsGenerated);
		System.out.println("For a total value of: " + changeGenerated);
		for (int i = 0; i < sol.length(); i++){
			System.out.println("#" + i + " type of coin: " + solValues.getElement(i) + " / Amount: " + sol.getElement(i));
		}
		return sol;
	}
	
}
