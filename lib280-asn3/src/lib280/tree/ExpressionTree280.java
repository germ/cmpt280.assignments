package lib280.tree;

// Hint: you can use this to tokenize the initial infix expression.

import lib280.dispenser.LinkedStack280;  // needed for infix-to-postfix, and postfix-to-expression-tree algoirthms
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.InvalidState280Exception;
import lib280.list.LinkedList280;        // Needed to create the list of tokens for the postfix expression (if you want)
import lib280.tree.BinaryNode280;        // Needed for creating new tree nodes when building the expression tree
import lib280.tree.LinkedSimpleTree280;  // The parent class for ExpressionTree280
import static java.lang.Math.pow;



public class ExpressionTree280 extends LinkedSimpleTree280<String> {


	
	
	/**
	 * Determine the priority of a token
	 * @param str A token from an expression
	 * @return Priority of the token.  ^ has priority 3, * and / have priority 2, + and - have priority 1.  All other tokens have priority 0.
	 */
	protected int precedence( String str ) {
		char x = str.charAt(0);
		if( x == '^' ) return 3;
		if( x == '*' || x == '/' ) return 2;
		if( x == '+' || x == '-' ) return 1 ;
		return 0;
	}
	
	/**
	 * Determine if a string is an expression operand token
	 * @param str String to query
	 * @return true if 'str' is an operand (contains only digits and decimal points), false otherwise.
	 */
	protected boolean isOperand(String str) {
		
		// An operand can only consist of positive floating point values, so all characters must be 
		// either digits between 0 and 1 or '.'
		for(int i=0; i < str.length(); i++)
			if( !Character.isDigit(str.charAt(i)) && str.charAt(i) != '.' ) return false;
		
		return true;
	}
	
	/**
	 * Determine if a string is an expression operator token
	 * @param str String to query
	 * @return true if 'str' is one of the single characters +,-,*,/ or ^, false otherwise.
	 */
	protected boolean isOperator(String str) {	
		// An operator is only one character long and is one of the characters +, -, /, *, or ^.
		char x = str.charAt(0);
		return ( x == '+' || x == '-' || x == '*' || x == '/' || x == '^') && str.length() == 1;
	}
	
	/**
	 * Determine if a string is an opening parenthesis expression token.
	 * @param str String to query
	 * @return true if 'str' is equal to "(".  False otherwise.
	 */
	protected boolean isOpenParen(String str) {
		char x = str.charAt(0);
		return x == '(' && str.length() == 1;
	}
	
	/**
	 * Determine if a string is an closing parenthesis expression token.
	 * @param str String to query
	 * @return true if 'str' is equal to ")".  False otherwise.
	 */
	protected boolean isCloseParen(String str) {
		char x = str.charAt(0);
		return x == ')' && str.length() == 1;
	}
	
	
	/**
	 * Construct a new expression tree.
	 * @param expr Infix expression from which to construct an expression tree.  All operators, operands and parentheses are assumed to be separated by one or more spaces.
	 * @throws InvalidState280Exception when an invalid expression is detected.
	 */
	public ExpressionTree280( String expr ) {
		LinkedList280<String> postFix = infixToPostFixTokenList(expr);
		postFixToExpression(postFix);
		
		
	}
	
	// TODO do Eval, and the to__fixExpressions.
	// Add the other methods required by the question.
	
	public String toInfixExpression(){
		String output ="";
		output = output + this.rootItem().toString();
		return "";
	}
	
	public String toPrefixExpression(){
		String output ="";
		
		return output;
	}
	
	// TODO wtf is going on with this godamn recursion
	public String toPostfixExpression(){
		String output = "";
		if (rootNode == null){
			return "";
		}
		else{
			if (!rootLeftSubtree().isEmpty()){
				ExpressionTree280 left = (ExpressionTree280) rootLeftSubtree();
				left.toPostfixExpression();
			}
			if (!rootRightSubtree().isEmpty()){
				ExpressionTree280 right = (ExpressionTree280) rootRightSubtree();
				right.toPostfixExpression();
			}
			System.out.print(rootNode.toString());
			//output = rootNode.toString() + output;
		}
		return output + rootNode.toString();
			
	}
		
	
	public double evaluate(BinaryNode280<String> n){
		if (n.leftNode() == null && n.rightNode() == null){
			double number = Double.parseDouble((String) n.item());
			return number;
		}
		
		double x = evaluate(n.leftNode());
		double y = evaluate(n.rightNode());
		
		switch(n.item()){
		case "+": return x+y;
		case "-": return x-y;
		case "/": return x/y;
		case "*": return x*y;
		case "^": return pow(x,y);
		default: return 0;
		}
		

	}
	
	public LinkedList280<String> infixToPostFixTokenList(String expr){
		LinkedStack280<String> tokenStack = new LinkedStack280<String>();
		LinkedList280<String> postFix = new LinkedList280<String>();
		
		for (String cur: expr.split(" ")){
			if (isOperand(cur))
				postFix.insertLast(cur);
			else if (isOpenParen(cur))
				tokenStack.insert(cur);
			else if (isCloseParen(cur)){
				if (tokenStack.isEmpty())
						throw new ContainerEmpty280Exception("Unbalanced parentheses");
				while (!isOpenParen(tokenStack.item())){
					postFix.insertLast(tokenStack.item());
					tokenStack.deleteItem();
					if (tokenStack.isEmpty())
						throw new ContainerEmpty280Exception("Unbalanced parentheses");
				}
				tokenStack.deleteItem();
			}
			else if (isOperator(cur)){
				if (tokenStack.isEmpty())
					tokenStack.insert(cur);
				else{
					while (!tokenStack.isEmpty() && precedence(tokenStack.item()) >=(precedence(cur))){
						postFix.insertLast(tokenStack.item());
						tokenStack.deleteItem();
					}
					tokenStack.insert(cur);
				}
			}
			else
				throw new InvalidState280Exception("error: invalid token in the expression");
		}
		while (!tokenStack.isEmpty()){
			postFix.insertLast(tokenStack.item());
			tokenStack.deleteItem();
		}
		//System.out.println(postFix);
		return postFix;
	}
	
	public void postFixToExpression(LinkedList280<String> postFix){
		LinkedStack280<BinaryNode280<String>> nodeStack = new LinkedStack280<BinaryNode280<String>>();
		
		while (!postFix.isEmpty()){
			String cur = postFix.firstItem();
			postFix.deleteFirst();
			BinaryNode280<String> newNode = new BinaryNode280<String>(cur);
			
			if (isOperand(cur)){
				newNode.setLeftNode(null);
				newNode.setRightNode(null);
				nodeStack.insert(newNode);
			}
			else{
				BinaryNode280<String> operand1 = nodeStack.item();
				nodeStack.deleteItem();
				BinaryNode280<String> operand2 = nodeStack.item();
				nodeStack.deleteItem();
				newNode.setLeftNode(operand2);
				newNode.setRightNode(operand1);
				nodeStack.insert(newNode);
			}
		}

		BinaryNode280<String> rootNode = nodeStack.item();
		this.setRootNode(rootNode);	
	}
	
	public static void main(String args[]) {
		
		
		ExpressionTree280 E = new ExpressionTree280("1.5 / ( 2 - 3 ) ^ 3");
		System.out.println("Original expression: 1.5 / ( 2 - 3 ) ^ 3");
//		System.out.println("Infix from tree:    " + E.toInfixExpression());
//		System.out.println("Prefix from tree:   " + E.toPrefixExpression());
		System.out.println("Postfix from tree:  " + E.toPostfixExpression());
		System.out.printf("Expression value = %f\n", E.evaluate(E.rootNode));
		System.out.println("The tree: \n");
		System.out.println(E.toStringByLevel());
		System.out.println("-----------------------------------------------");
		
		System.out.println("No Parenthesis Test");
		
		E = new ExpressionTree280("2 ^ 2 + 5");
		System.out.println("Original expression: 2 ^ 2 + 5");
//		System.out.println("Infix from tree:    " + E.toInfixExpression());
//		System.out.println("Prefix from tree:   " + E.toPrefixExpression());
		System.out.println("Postfix from tree:  " + E.toPostfixExpression());
		System.out.printf("Expression value = %f\n", E.evaluate(E.rootNode));
		System.out.println("The tree: \n");
		System.out.println(E.toStringByLevel());
		System.out.println("-----------------------------------------------");
		
		
		
				
		
	}
	
	

}
