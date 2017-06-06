import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner fileScan = new Scanner(new File("fourLetterDictionary.txt"));
		HashMap<String, ArrayList<String>> dictionaryMap = new HashMap<>(); //contains every word in the dictionary and each words "adjacent" words
		HashMap<String,Boolean> usedWords = new HashMap<>();//used to keep track of how many words have been used while trying to build the graph
		ArrayList<String> dictionary = new ArrayList<>();
		
		long preProcessingStartTime = System.currentTimeMillis();
		while (fileScan.hasNext()) {
			
			String temp = fileScan.next();
			dictionaryMap.put(temp, new ArrayList<String>());
			dictionary.add(temp);
		}
		
		fileScan.close();
		System.out.println(dictionary.size() + " words added to dictionary");
		System.out.print("Processing dictionary. ");

		int count = 0;
		int diffCount;
		for (String key : dictionaryMap.keySet()) {
			
			//just used to clean up the output some
			if (count % 25 == 0) {
				System.out.print(". ");
				if(count != 0){
					if(count%1500 == 0){
						System.out.println();
					}
				}
			}
			
			count++;

			char[] keyArr = key.toCharArray();

			for (String word : dictionary) {
				diffCount = 0;
				char[] wordArr = word.toCharArray();

				for (int i = 0; i < word.length(); i++) {
					
					if (keyArr[i] != wordArr[i]) { //if the letters do not match increment difference count between the two words
	
						diffCount++;
					
					}
				}
				
				if(diffCount == 1){ //iff diffCount = 1 then the words only differ by one letter and are "adjacent"
					dictionaryMap.get(key).add(word);
				}
			}

		}
		long preProcessingFinishTime = System.currentTimeMillis();
		System.out.println("Dictionary Processed!");

		
		System.out.print("Enter your first four letter word: ");
		Scanner scan = new Scanner(System.in);
		String word1,word2;
		word1 = scan.next();
		System.out.print("\nEnter your second four letter word: ");
		word2 = scan.next();
		
		scan.close();
		long startTime = System.currentTimeMillis();
		//just in case
		word1 = word1.toUpperCase();
		word2 = word2.toUpperCase();
		
		if(word1.equals(word2)){
			System.out.println("The chain was 1 word long and was: ");
			System.out.println(word1);
			return;
		}
		
		
		ArrayList<Set<String>> adjacencyList = new ArrayList<>();
		
		//sleep deprived magic? or just plain graph theory...probably shouldnt code at 5am
		
		if(dictionary.contains(word1) && dictionary.contains(word2)){
			if(dictionaryMap.get(word1).size() == 0 || dictionaryMap.get(word2).size() == 0){
				System.out.println("One of the entered words has no adjacent words... \nTerminating program.");
				return;
			}
			System.out.print("Building Graph: ");
			
			adjacencyList.add(new TreeSet<String>());
			adjacencyList.get(0).add(word1);
			
			boolean found = false;
			
			count = 0;
			int dotCount = 0;
			int usedWordsCount = -1;
	
			//while word 2 has not been added to the graph
			while(!found){ 
				count++;
				
				if(usedWords.size()==usedWordsCount){ //if the size of usedWords has not changed since
													  //the last iteration then no chain could be made
					System.out.println("No reasonable chain could be made sorry");
					return;		//terminate program
				}else{ //otherwise update usedWordsCount
					usedWordsCount = usedWords.size();
				}
				
				Set<String> nextLevel = new TreeSet<>(); //handles duplicates and represents the next
														//level of the adjacencyList graph
				
				for(Set<String> level: adjacencyList){ //level by level
					
					//used to help clean up output
					System.out.print(". ");
					dotCount++;
					if(dotCount == 75){
						dotCount = 0;
						System.out.println();
					}
					
					
					for(String w: level){ //foreach word in the current level of the graph
						
						if(!usedWords.containsKey(w)){ //if the word has not been used before
							usedWords.put(w, true);
							for(String k: dictionaryMap.get(w)){ //addAll words adjacent to w to the next level 
								nextLevel.add(k);
							}
						}
					}
				}
				
				if(nextLevel.contains(word2)){ //if nextLevel contains target word then the graph is complete
					found = true;
					adjacencyList.add(nextLevel);
				}else{
					adjacencyList.add(nextLevel);
				}
				
			}
			
		ArrayList<String> wordChain = new ArrayList<>();
		String currentWord = word2;
		
	
		
		count = 0;
		
		System.out.println();
		System.out.print("Building chain: "); 
		
			for(int i = adjacencyList.size()-2; i >= 0; i--){ //builds the chain backwards starting from word2 returning to root which is word1
				wordChain.add(currentWord);
				
				System.out.print(". ");
				for(String s: adjacencyList.get(i)){
		
					if(dictionaryMap.get(s).contains(currentWord)){
						count++;
						//used to clean up output but will probably never happen
						if(count%20 == 0){
							System.out.println();
						}
						currentWord = s; //if s is an adjacent word to currentWord
										//update currentWord to s
						break;
					}
				}
			}
			
			
			wordChain.add(word1);
			System.out.println("\nThe chain found was "+wordChain.size()+" words long and is: ");
			for(int i = wordChain.size()-1;i >=0; i--){ //iterate through the list backwards to print it out in correct order
				System.out.println(wordChain.get(i));
			}
			
			long finishTime = System.currentTimeMillis();
			
			long totalTime = (preProcessingFinishTime - preProcessingStartTime) + (finishTime-startTime);

			System.out.println("\nTotal time this program took to complete (excluding user input time) was: "+totalTime+"ms"); 
		}else{
			System.out.println("One of the words was not found in the dictionary");
		}


		
	}

}
