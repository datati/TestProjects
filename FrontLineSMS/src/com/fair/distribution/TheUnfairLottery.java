package com.fair.distribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import java.util.Scanner;

public class TheUnfairLottery {

	/**
	 * This program takes two lines of input:
	 * - A comma-separated list of this week's prizes' values
	 * - A comma-separated names of this week's winners
	 * from the console as entered by the user.For example, the input could be:
	 * 100,800,200,500,400,1000
	 * Joshua,Mahesh,Lilian
	 * The program then outputs the fairest possible distribution of prizes, by displaying one line for each winner, with the values of the prizes allocated to them. 
	 * For example, given the input above, the output could be:
	 * Joshua:100,400,500
	 * Mahesh:1000
	 * Lilian:800,200
	 * -13
	 * @author Douglas O. Atati 
	 * @version 09/14/2014
	 */
	
	public static final String PRIZE = "prizes";
	public static final String WINNER = "winners";
	
	public static void main(String[] args) {
		new TheUnfairLottery().start();
	}

	/**
	 * Reads in text input of winners and prizes and distributes the prizes fairly amongst the winners
	 * 
	 */

	public void start(){
		
		System.out.print("Enter prize values separated by commas(integers only): ");
		String ofPrizes = getUserInput(PRIZE);

		System.out.print("Enter winners' names separated by commas: ");
		String ofWinners = getUserInput(WINNER);

		//make a winners list and shuffle it
		ArrayList<String> winnersList = new ArrayList<String>(Arrays.asList(ofWinners.split(",")));
		Collections.shuffle(winnersList);

		//convert prize values to numbers
		ArrayList<Integer> prizeList = new ArrayList<Integer>();
		for(String number : Arrays.asList(ofPrizes.split(","))) {
			prizeList.add(Integer.parseInt(number));
		};

		//sort prizes in descending order
		Collections.sort(prizeList ,Collections.reverseOrder());

		//create map to hold prize and winner values
		HashMap <String,ArrayList<Integer>> prizeAndWinners = new HashMap <String,ArrayList<Integer>>();

		//add winners to the map			
		for(String winner:winnersList){
			prizeAndWinners.put(winner, new ArrayList<Integer>());
		}

		//add prizes to the map
		for(int i = 0; i < winnersList.size(); i++){
			if(winnersList.size() <= prizeList.size()){
				prizeAndWinners.get(winnersList.get(i)).add(prizeList.get(i));
			}
			else{
				if(i < prizeList.size()){
					prizeAndWinners.get(winnersList.get(i)).add(prizeList.get(i));
				}
				else{
					prizeAndWinners.get(winnersList.get(i)).add(0);
				}
			}

		}

		//if there are more prizes than there are winners
		if(winnersList.size() < prizeList.size()){
			//make a list of the extra prizes			
			ArrayList<Integer> prizesLeftToDistribute = new ArrayList<Integer>();
			for(int i = winnersList.size(); i < prizeList.size(); i++){
				prizesLeftToDistribute.add(prizeList.get(i));
			}
			//assign the extra prize(s) to a winner with the lowest total wins
			for(Integer prize : prizesLeftToDistribute){
				String receipient = winnerWithLowestPrizeTotal(prizeAndWinners,winnersList);
				prizeAndWinners.get(receipient).add(prize);
			}
		}

		//print the output
		printOutput(prizeAndWinners);

		
	}

	/**
	 * Gets the user input from the console
	 * @param prizeOrWinner
	 * @return
	 */

	public String getUserInput(String prizeOrWinner){
		Scanner scan = new Scanner(System.in);
		String input = "";	
		input = scan.nextLine();

		while (!isValidInput(input,prizeOrWinner)){
			System.out.print("Invalid format. Try again: ");
			input = scan.nextLine();
		}
		return input;
	}

	/**
	 * Checks if the user input is formatted correctly i.e comma separated if more than one entry 
	 * and only numeric for prize entries
	 * 
	 * @param input
	 * @param prizeOrWinner
	 * @return
	 */
	public boolean isValidInput(String input,String prizeOrWinner){
		boolean isValid = true;
		if(input.isEmpty()){
			isValid = false;
		}
		else{
			String [] splits = input.split(",");
			if(prizeOrWinner.equals(PRIZE)){
				for(String s: splits){
					if(!isInteger(s.trim())){
						isValid = false;
						break;
					}
				}
			}
		}
		return isValid;
	}

	/**
	 *  
	 *  Checks if a string can be converted to a number
	 * @param number
	 * @return
	 */

	public boolean isInteger(String number){
		boolean isAnumber = true;
		try{
			Integer.parseInt(number);
		}catch(NumberFormatException n){
			isAnumber = false;
		}

		return isAnumber;
	}

	/**
	 * Prints the contents of a hashmap in a comma separated format
	 * 
	 * @param prizeAndWinners
	 */

	public void printOutput(HashMap <String,ArrayList<Integer>> prizeAndWinners){

		for(String s : prizeAndWinners.keySet()){
			String prizes = "";
			int prizeListSize = prizeAndWinners.get(s).size();
			ArrayList<Integer> prizeList = prizeAndWinners.get(s);

			for(int i = 0; i < prizeList.size(); i++ ){
				if((i == 0 && prizeListSize == 1) || (i == prizeListSize-1)){
					prizes = prizes + prizeList.get(i);
				}
				else{
					prizes = prizes + prizeList.get(i) +", ";
				}	
			}

			System.out.println(s + " : " + prizes);
		}
	}

	/**
	 * Calculates the sum of the integers contained in an arraylist
	 * 
	 * @param prizes
	 * @return the sum of the numbers in the array list
	 */

	public Integer addPrizes(ArrayList<Integer> prizes){
		Integer b = 0;
		for(Integer number : prizes){
			b = b + number;
		}
		return b;
	}

	/**
	 * Finds the winner with the lowest total amount in prizes
	 * 
	 * 
	 * @param prizeAndWinners
	 * @param winnersList
	 * @return
	 */

	public String winnerWithLowestPrizeTotal(HashMap <String,ArrayList<Integer>> prizeAndWinners,ArrayList<String>  winnersList){

		ArrayList<Integer> totals = new ArrayList<Integer>();
		ArrayList<String> winners = new ArrayList<String>();
		String finalWinnerWithLowestPrizeTotal = "";

		//get the totals of the each winner
		for(String s : winnersList){
			totals.add(addPrizes(prizeAndWinners.get(s)));
		}
		//get the minimum total
		Integer min = Collections.min(totals);

		//Find the winner with the minimum total. 
		for(int i = 0; i < totals.size(); i++){
			if(totals.get(i) == min){
				winners.add(winnersList.get(i));
			}
		}

		//If more than one winner has the lowest total, pick one at random. Otherwise pick the winner with the lowest total
		if(winners.size() > 1){
			Collections.shuffle(winners);
			finalWinnerWithLowestPrizeTotal = winners.get(0);
		}
		else{
			finalWinnerWithLowestPrizeTotal = winners.get(0);
		}
		return finalWinnerWithLowestPrizeTotal;
	}

}
