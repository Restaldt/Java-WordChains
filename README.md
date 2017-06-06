# Java-WordChains
A word chain is a sequence of valid (dictionary) words that differ from adjacent words on the chain by a single character. Given a start word and a final word, the goal is to find a sequence, or chain, of words that link the first word to the final word. 

The goal of this program is to find a shortest possible word chain between two user input 4 letter words.

My implementation of this problem builds and adjacencyList graph representation where each word is stored and has a list of its "adjacent" words (words that differ by one letter). The program builds the graph with the first entered word as the root and then adds its adjacent words to the next level down. It repeats this for each word in each level until the second entered word is added to a level.

The program then builds the chain by traversing backwards from the second entered word to the root (which is the first entered word). This logically should be a shortest possible chain between the two words (multiple chains of the same length can exist)

This implementation only uses 4 letter words but can be easily modified to work with any length word so long as a dictionary of words of that length is provided

