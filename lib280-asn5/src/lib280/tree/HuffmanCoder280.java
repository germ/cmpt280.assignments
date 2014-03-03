package lib280.tree;

/**
 * Implementation of a Huffman coder.
 * 
 * @author Mark Eramian
 *
 */
public class HuffmanCoder280 {
	
	/**
	 * For collecting the frequency of each character in the message.
	 */
	protected CharFrequency280[] frequencies;
	
	/**
	 * The message to be encoded.
	 */
	protected String message;
	
	/**
	 * Encoded message
	 */
	protected String encodedMessage;
	
	/** Codes for each ascii value between 0 and 127
	 * 
	 */
	protected String[] codes;
	
	/** 
	 * Forest of partial Huffman trees used in the build process.
	 */
	protected ArrayedMinHeap280<RootComparableLinkedSimpleTree280<CharFrequency280>> huffmanForest;
	
	/**
	 * Final Huffman tree.
	 */
	protected RootComparableLinkedSimpleTree280<CharFrequency280> huffmanTree;
	
	
	/**
	 * Obtain the encoded message as a string of bits.
	 * @return The encoded message as a bitstring.
	 */
	public String getEncodedMessage() {
		return encodedMessage;
	}

	/**
	 * Obtain the original, uncompressed message.
	 * @return The original message as a string.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Count the frequency of characters in this.message.  
	 * Results are stored in this.frequencies.
	 */
	private void countFrequencies() {
		//T.ODO 
        // read each character and record the frequencies
		for (int i = 0; i<128;i++){
			CharFrequency280 newChar = new CharFrequency280( (char) i, 0);
			frequencies[i] = newChar;
		}
        for (char x : this.message.toCharArray()){
        	frequencies[(int)x].incrementFreq();
        }
        System.out.println("frequencies: " + frequencies.toString());
		// Initialize the frequency of each possible character to zero.
		
		// Count the frequency of each character.	
	}
	
	
	/**
	 * Build the huffman tree.  
	 * @precond huffmanForest is initialized to an empty forest, this.countFrequences() has been called.
	 * @postcond the final huffman tree is stored in this.huffmanTree
	 */
	private void buildHuffmanTree() {
		//T.ODO
		
		for (int i =0; i<128;i++){
			if (frequencies[i].secondItem() == 0){
				RootComparableLinkedSimpleTree280<CharFrequency280> tree =  
						new RootComparableLinkedSimpleTree280<CharFrequency280>(null, frequencies[i], null);
				huffmanForest.insert(tree);
			}
		}
		
		while (!huffmanForest.isEmpty()){
			RootComparableLinkedSimpleTree280<CharFrequency280> t1 = huffmanForest.item();
			huffmanForest.removeItem();
			if (!huffmanForest.isEmpty()){
				RootComparableLinkedSimpleTree280<CharFrequency280> t2 = huffmanForest.item();
				huffmanForest.removeItem();
				int count = t1.rootItem().secondItem() + t2.rootItem().secondItem();
				CharFrequency280 newChar = new CharFrequency280(null, count);
				RootComparableLinkedSimpleTree280<CharFrequency280> t = 
						new RootComparableLinkedSimpleTree280<CharFrequency280>(t1, newChar, t2);
				huffmanForest.insert(t);
			}
			else
				this.huffmanTree = t1;
		}
		
		
		// For each character with non-zero frequency, add its character-frequency pair 
		// as a single-node tree to the Huffman Forest.
		// The min heap will allow us to easily obtain the trees in the forest with the smallest
		// frequency.

		// As long as there is more than one tree in the forest...

		    // Obtain two trees in the forest with smallest total frequencies and remove them from the forest.
			
			// Create a new tree t, with t1 and t2 as the left and right subtrees.
			
			// Put t back in the min heap.
		
		// Store the last remaining tree in the forest in this.huffmanTree
	}
	
	/**
	 * Extracts the bitstring codes in the huffman tree for each characater
	 * to the string array this.codes.
	 * @precond this.buildHuffmanTree() has been called.
	 * @param r root of the huffman tree (should pass in this.huffmanTree)
	 * @param bitString the bitstring prefix (normally "")
	 * @postcond bitstring for ascii code i is stored in this.codes[i]
	 */
	private void extractCodes(LinkedSimpleTree280<CharFrequency280> r, String bitString) {		
		//TODO test upload from windows eclipse
		
		// This should be a recursive method that traverses the (sub)tree r.  When the traversal
		// reaches a leaf, the code for the character stored at that leaf should be inserted
		// into the proper entry in the this.codes array.  The bitString parameter should
		// store the code prefix of the root of the current (sub)tree r.
	}
	
	/**
	 * Helper method for the toString() method.
	 * @param r Root of the huffman tree to output. (normally this.huffmanTree)
	 * @param bitString bitstring prefix (normally called with "")
	 * @return String representation of the tree showing the mapping of each character/frequency
	 * pair onto its encoded bitstring.
	 */
	private String codesToString(LinkedSimpleTree280<CharFrequency280> r, String bitString) {
		// TODO
		
		// This should build a string that shows the mapping from characters to codes, that is,
		// the text under the heading "The Huffman Code" in the sample output given on the assignment.
		// Essentially, you are converting the information in this.codes to a 
		// printable format stored in a String and returning it.
		
		return "";  // REPLACE THIS LINE WITH YOUR CODE -- this is just to prevent a compiler error.
	}
	
	@Override
	public String toString() {
		// Print out mapping from characters to codes.
		return this.codesToString(this.huffmanTree, "");
		
	}
	
	/**
	 * Encode the message using the huffman code.
	 * @precond this.extractCodes() has been called.
	 * @postcond encoded message is stored in this.encodedMessage()
	 */
	private void encodeMessage() {
		this.encodedMessage = "";
		for(int i=0; i < this.message.length(); i++) {
			this.encodedMessage += codes[this.message.charAt(i)];
		}
	}
	
	/**
	 * Decode the encoded version of the message and return it.
	 * @return A string containing the decoded message.
	 */
	public String decodeMessage() {
		//TODO
		
		// It's up to you to figure out the algoirthm.
		// Hint: use an iterative, not recursive algoirthm.
		
		return null;  // REPLACE THIS LINE WITH YOUR CODE -- this is just to prevent a compiler error.
	}
	
	
	
	/**
	 * Encode a message using a huffman coder.
	 * 
	 * @param message The message to encode.
	 */
	HuffmanCoder280(String message) {
		this.message = message;

		// big enough for entire extended ASCII
		this.frequencies = new CharFrequency280[128];  
		
		// Create an empty Huffman forest
		this.huffmanForest = new ArrayedMinHeap280<RootComparableLinkedSimpleTree280<CharFrequency280>>(128);
		
		// Count the frequency of characters in the message.
		this.countFrequencies();
		
		// Build the Huffman tree.
		this.buildHuffmanTree();
		
		// Extract the Huffman codes for each character to a string array
		// where the index corresponds to the ASCII code.
		this.codes = new String[128];
		this.extractCodes(huffmanTree, "");
		
		// Encode the original message.
		this.encodeMessage();
	}
	
	
	public static void main(String args[]) {
		
		HuffmanCoder280 C;
				
		if( args.length < 1 )  C = new HuffmanCoder280("see the bees");  // The default message
		else {
			C = new HuffmanCoder280(args[0]);
		}
		
		// Print original message
		System.out.println("Original Message:\n" + C.getMessage()+"\n");

		// Print the code table.
		System.out.println("The Huffman Code:\n");
		System.out.println(C);
		
		// Print encoded message and message lengths.
		System.out.println("Encoded message:\n" + C.getEncodedMessage()+"\n");
		System.out.printf("Original message would require %d bytes.\nEncoded message requires %.0f bytes if encoded with 8-bits per byte.",
				C.getMessage().length(), Math.ceil((float)C.getEncodedMessage().length()/8.0) );
		
		// Decode the message and print it out.
		System.out.println("Decoded message:\n" + C.decodeMessage());

	}
}
