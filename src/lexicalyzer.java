/**
 * Lexilyer for COSC 455
 * by Wesley Lancaster
 * Submitted on 10/22/22
 * @ wlanca2@students.towson.edu
 *
 * sample location: /Users/johnmetz/Desktop/cosc455/455JAVA/project1/Examples/ab.txt
 **/
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

//this is the main method. It will prompt for the txt file location
//then use a loop to cycle through each lexeme, of each line, while callin each of the 4 methods
public class lexicalyzer {
    public static void main(String[] args){

        System.out.print("""
                |
                * Lexilyzer for COSC 455
                * by Wesley Lancaster
                * Submitted on 10/22/22""");

        //this scanner is only used to access the text file and called to read nextLine-
        //which is then stored into a string. However, I read between lexemes with my own next() method.
        Scanner fileName = new Scanner(System.in);
        System.out.println("\n|\nplease enter directory location of .txt file.....");
        String name = fileName.nextLine();
        System.out.print(".....path entered");
        fileName.close();
        File x = new File(name);

        //checking if the file exist with the scanner
        if(x.exists()) {
            System.out.print("....."+x.getName() +  " exists!" + "\n");
        }
        else {
            System.out.println("......file does not exist");
        }
        System.out.println("|");

        try {
            Scanner sc = new Scanner(x);
            String text = sc.nextLine();
            System.out.print("|\nNext Line: " + text + "\n");
            String lexeme = "";

            next n = new next();
            position p = new position();
            value v = new value();
            kind k = new kind();

            int i = 0;
            int l = 1;
            boolean noError = true;

//THE CORE LOOP---------------------------------------------------------------------------------------------------------
            //this loop is significant, as its job is to loop for each lexeme, until the end of a line,
            //and then loop each line until the end of the text file.
            while (n.hasNext(text, i) || noError || sc.hasNextLine()) {
                Scanner txt = new Scanner(text);

                while (txt.hasNext()) {
                    lexeme = n.next(text, i); //calls next lexeme
                    System.out.print("\nlexeme being read is: " + lexeme);
                    i += lexeme.length() + 1;
                    p.position(i, l, lexeme); //returns current position of lexeme
                    i = k.kind(lexeme, text, i);
                    System.out.print("\n");

                    //iterating to the next line
                    if (i >= text.length()) {
                        text = sc.nextLine();
                        System.out.print("|\nNext Line: " + text + "\n" + "length: " + text.length() + "\n");
                        i = 0;
                        l++;
                    }
                }
                //to make sure the last line says: "end"
                if(!sc.hasNextLine()){
                    if(text.contains("end")){
                        System.out.print("\nlexeme being read is: "+ text +
                                "\nposition within line is: "+i +" and position is line: "+l+
                                "\nkind is: end of text: end\n" +
                                "letter/s read: end\n");
                    }
                    else{
                        System.out.print("ERROR: FILE DOES NOT TERMINATE WITH 'END' KEYWORD ");
                    }
                }
                txt.close();
            }
            System.out.print("""

                    |
                    | end of text file
                    * Lexilyzer for COSC455
                    * by Wesley Lancaster
                    * Submitted on 10/22/22""");
            sc.close();
        }
        //catch if theres no file in location
        catch (FileNotFoundException e) {
            System.out.println("Error: file was not found (exception e)");
        }
    }
}

//THE NEXT METHOD-------------------------------------------------------------------------------------------------------
class next extends lexicalyzer {
    //the method for reading lexemes
        public String next(String text, int i) {

        String lexeme ="";
        char c;

            //is current char empty or not?
            //if yes, then iterate until either not empty, or end of line
            if(!Character.isWhitespace(text.charAt(i)) || !Character.isSpaceChar(text.charAt(i))){
                while(!Character.isWhitespace(text.charAt(i)) || !Character.isSpaceChar(text.charAt(i))){
                    c = text.charAt(i);
                    lexeme = lexeme + c;
                    i++;
                    if(i >= text.length()){break;}
                }
            }
            else if(Character.isWhitespace(text.charAt(i)) || Character.isSpaceChar(text.charAt(i))){
                while(Character.isWhitespace(text.charAt(i)) || Character.isSpaceChar(text.charAt(i))){
                    i++;
                    if(i >= text.length()){break;}
                }
                while(!Character.isWhitespace(text.charAt(i)) || !Character.isSpaceChar(text.charAt(i))){
                    c = text.charAt(i);
                    lexeme = lexeme + c;
                    i++;
                    if(i >= text.length()){break;}
                }
            }
            return lexeme;
    }

    //used to determine if there is any lexemes remaining in the current line of text
        public Boolean hasNext(String text, int i) {
        boolean hasNext = false;

            if(Character.isWhitespace(text.charAt(i)) || Character.isSpaceChar(text.charAt(i))){
                while(Character.isWhitespace(text.charAt(i)) || Character.isSpaceChar(text.charAt(i))){
                    i++;
                    if(i >= text.length()){
                        hasNext = false;
                        break;
                    }
                    else if(!Character.isWhitespace(text.charAt(i)) || !Character.isSpaceChar(text.charAt(i))){
                        hasNext = true;
                        break;
                    }
                }
            }
            return hasNext;
        }

        //this is my own method to compare strings of text
        public Boolean matches(String text, String compared){
            boolean matches = true;
            int  i = 0;

            while(i <= compared.length()){
                if(text.charAt(i) == compared.charAt(i)){
                    i++;
                }
                else{
                    matches = false;
                    break;
                }
            }
            return matches;
        }
    }

//THE POSITION METHOD---------------------------------------------------------------------------------------------------
class position extends lexicalyzer {
    //this method simply prints the line and position of a lexeme being read.
    public void position(int i, int l, String lexeme) {
        i -= lexeme.length() - 1;
        System.out.print("\nline: "+l+" index: "+i);
    }
}

//THE VALUE METHOD------------------------------------------------------------------------------------------------------
class value extends lexicalyzer {
    //this method prints the value, but relies on the kind() method to actually determine the value of the lexeme
    public void value(String lexeme, boolean noError) {
        boolean result = lexeme.matches("[0-9]+");
        boolean symbol = lexeme.matches("[(,),{,},:,;]+");

        if(noError) {
            if (result) {
                System.out.print("\nvalue: NUM");
            } else if (symbol) {
                System.out.print("\nvalue: NULL");
            } else {
                System.out.print("\nvalue: ID");
            }
        }
        else{
            System.out.print("\nvalue: ERROR");
            //System.exit(0);
        }
    }
}

//THE KIND METHOD-------------------------------------------------------------------------------------------------------
class kind extends lexicalyzer {
    //this method is a monster, mainly because it already has some foundation for the upcoming project 2
    //only about 1/3 of the code in kind() is really used for identifying the kind of lexeme,
    //with the remaining for future syntax analysis and rules

    //TODO 3 I need to store the identifiers for future loops.
    //TODO 4 this does not account for identifiers with names similar to keywords. like 'print' and 'printy'
    //no error is used to pass to value, and if returns false, will be returned to the 'core loop' to end the code.
    boolean noError = true;

    public int kind(String lexeme, String text, int i) {
        kind k = new kind();
        value v = new value();
        next n = new next();

        if (lexeme.contains("//")) {
            v.value(lexeme, noError);
            System.out.print("\nkind is: Single comment: ");
            while(i < text.length()){
                lexeme = n.next(text, i);
                i += lexeme.length() + 1;
                System.out.print(" " + lexeme);
            }
        }

        String letter = "";
        String number = "";
        String symbol = "";
        String operator = "";
        char c = '1';

        //string builder is used for the .value() method to construct the different values of a lexeme read.
        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        StringBuilder stringBuilder4 = new StringBuilder();

        //these next few blocks are for categorizing the list of symbols.
        for (int j = 0; j < lexeme.length(); j++) {
            if (lexeme.charAt(j) == 'a' || lexeme.charAt(j) == 'b' || lexeme.charAt(j) == 'c' || lexeme.charAt(j) == 'd'
                    || lexeme.charAt(j) == 'e' || lexeme.charAt(j) == 'f' || lexeme.charAt(j) == 'g' || lexeme.charAt(j) == 'h'
                    || lexeme.charAt(j) == 'i' || lexeme.charAt(j) == 'j' || lexeme.charAt(j) == 'k' || lexeme.charAt(j) == 'l'
                    || lexeme.charAt(j) == 'm' || lexeme.charAt(j) == 'n' || lexeme.charAt(j) == 'o' || lexeme.charAt(j) == 'p'
                    || lexeme.charAt(j) == 'q' || lexeme.charAt(j) == 'r' || lexeme.charAt(j) == 's' || lexeme.charAt(j) == 't'
                    || lexeme.charAt(j) == 'u' || lexeme.charAt(j) == 'v' || lexeme.charAt(j) == 'w' || lexeme.charAt(j) == 'x'
                    || lexeme.charAt(j) == 'y' || lexeme.charAt(j) == 'z'
                    || lexeme.charAt(j) == 'A' || lexeme.charAt(j) == 'B' || lexeme.charAt(j) == 'C' || lexeme.charAt(j) == 'D'
                    || lexeme.charAt(j) == 'E' || lexeme.charAt(j) == 'F' || lexeme.charAt(j) == 'G' || lexeme.charAt(j) == 'H'
                    || lexeme.charAt(j) == 'I' || lexeme.charAt(j) == 'J' || lexeme.charAt(j) == 'K' || lexeme.charAt(j) == 'L'
                    || lexeme.charAt(j) == 'M' || lexeme.charAt(j) == 'N' || lexeme.charAt(j) == 'O' || lexeme.charAt(j) == 'P'
                    || lexeme.charAt(j) == 'Q' || lexeme.charAt(j) == 'R' || lexeme.charAt(j) == 'S' || lexeme.charAt(j) == 'T'
                    || lexeme.charAt(j) == 'U' || lexeme.charAt(j) == 'V' || lexeme.charAt(j) == 'W' || lexeme.charAt(j) == 'X'
                    || lexeme.charAt(j) == 'Y' || lexeme.charAt(j) == 'Z') {
                char chaL = lexeme.charAt(j);
                stringBuilder1.append(letter).append(chaL);
            }

            if (lexeme.charAt(j) == '1' || lexeme.charAt(j) == '2' || lexeme.charAt(j) == '3' || lexeme.charAt(j) == '4'
                    || lexeme.charAt(j) == '5' || lexeme.charAt(j) == '6' || lexeme.charAt(j) == '7' || lexeme.charAt(j) == '8'
                    || lexeme.charAt(j) == '9' || lexeme.charAt(j) == '0') {
                char chaN = lexeme.charAt(j);
                stringBuilder2.append(number).append(chaN);
                v.value(number, noError);
            }
            if (lexeme.charAt(j) == '{' || lexeme.charAt(j) == '}' || lexeme.charAt(j) == '(' || lexeme.charAt(j) == ')'
                    || lexeme.charAt(j) == ';' || lexeme.charAt(j) == '[' || lexeme.charAt(j) == ']' || lexeme.charAt(j) == ':') {
                char chaS = lexeme.charAt(j);
                stringBuilder3.append(symbol).append(chaS);
            }
            if (lexeme.charAt(j) == '+' || lexeme.charAt(j) == '-' || lexeme.charAt(j) == '/' || lexeme.charAt(j) == '*'
                    || lexeme.charAt(j) == '<' || lexeme.charAt(j) == '>' || lexeme.charAt(j) == '=' || lexeme.charAt(j) == '!'
                    || lexeme.charAt(j) == '|' || lexeme.charAt(j) == '%' || lexeme.charAt(j) == ':') {
                char chaR = lexeme.charAt(j);
                stringBuilder4.append(operator).append(chaR);
            }
        }
        letter = String.valueOf(stringBuilder1);
        number = String.valueOf(stringBuilder2);
        symbol = String.valueOf(stringBuilder3);
        operator = String.valueOf(stringBuilder4);

        if (letter != "") {
            System.out.print("\nletter/s read: " + letter);
            v.value(letter, noError);
        }
        if (number != "") {
            System.out.print("\nnumber/s read: " + number);
            v.value(number, noError);
        }
        if (symbol != "") {
            System.out.print("\nsymbol/s read: " + symbol);
            v.value(symbol, noError);
        }
        if (operator != "") {
            System.out.print("\noperator/s read: " + operator);
            v.value(operator, noError);
        }

        //this is the start of out .kind() logic, for determining keywords and rules they follow.
        if (lexeme.contains(":") || lexeme.contains("=")) {
            if(!lexeme.equals(":=")){
                //System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN ':=' ?");
                //System.exit(0);
            }
            else if(lexeme.contains(":=")||lexeme.contains("=<")||lexeme.contains(">=")||lexeme.contains("!=")){
                System.out.print("\nkind is RelationalOperator: " + lexeme);
            }
        }

        else if (letter.contains("program")) {
            System.out.print("\nkind is keyword: " + letter);
            if(!letter.equals("program")){ //if int is misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'program' ?");
                System.exit(0);
            }
            if(n.hasNext(text, i)){
                i += letter.length();
                letter = n.next(text, i);
                i += lexeme.length() + 1;
            }
            else{
                System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM MUST BE GIVEN ID FOLLOWED BY ':' ");
                //System.exit(0);
            }
            if(letter.contains(":")){
                System.out.print("\nidentifier: " + letter);
            }
            else if (!letter.contains(":")){
                System.out.print("\nidentifier: " + letter);
                if(n.hasNext(text, i)){
                    i += letter.length();
                    letter = n.next(text, i);
                    i += lexeme.length() + 1;
                }
                if(letter.contains(":")){
                    System.out.print(letter);
                }
                else if(!letter.contains(":")){
                    //System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM IDENTIFIER MUST BE FOLLOWED BY ':' ?");
                    //System.exit(0);
                }
            }
        }

        //todo int must be:
        // followed by a id and contain a ';'
        // or be initialized to some combination of logic that ends with ';'
        else if (letter.contains("int") && !letter.contains("print")) { //reads keyword 'int'
            System.out.print("\nkind is keyword Declaration: " + letter);
            if(!letter.equals("int")){ //if int is misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'int' ?");
                System.exit(0);
            }
/**
            if(!n.hasNext(text, i)){ //if int isn't given a name to initialize.
                System.out.print(" \nSYNTAX ERROR DETECTED, 'int' MUST BE USED TO INITIALIZE A IDENTIFIER");
                //System.exit(0);
            }
            //while(!letter.contains(";"))
            //if(!txt.hasNext)
            //{} print "error this needs to end with ';'
            //{} somehow loop through each item, and return its value, kind and position
            //if identifier = true and .next() is also an identifier and not an operator, return an error.
 **/
        }
        else if (letter.contains("bool")) { //reads keyword
            System.out.print("\nkind is keyword: " + letter);
            if(!letter.equals("bool")){
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'bool' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("if")) { //reads 'if' statement
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);
            if(!letter.equals("if")){ //misspell catcher
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'if' ?");
                System.exit(0);
            }
            /**
            letter = n.next(text, i);
            i += lexeme.length() + 1;

            if(letter.contains("not")){
                if(!letter.matches("not")){ //misspell catcher
                    //System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'not' ?");
                    //System.exit(0);
                }
                while(!letter.contains("then")){//this is an issue
                    System.out.print(" " + letter + " ");
                    letter = n.next(text, i);
                    i += lexeme.length() + 1;
                    if(letter.contains("then")){
                        if(!letter.matches("then")){ //misspell catcher
                            //System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                            //System.exit(0);
                        }
                    }
                }
                while(n.hasNext(text, i)) {

                    //todo add stuff to this so contents are also lexilized

                    System.out.print(" " + letter + " ");
                    letter = n.next(text, i);
                    i += lexeme.length() + 1;
                }
            }
            else if(!letter.contains("not")){
                while(!letter.contains("then")){
                    System.out.print(" " + letter + " ");
                    letter = n.next(text, i);
                    i += lexeme.length() + 1;
                    if(letter.contains("then")){
                        if(!letter.matches("then")){ //misspell catcher
                            //System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                            //System.exit(0);
                        }
                    }
                }
                while(n.hasNext(text, i)) {

                    //todo add stuff to this so contents are also lexilized

                    System.out.print(" " + letter + " ");
                    letter = n.next(text, i);
                    i += lexeme.length() + 1;
                }
            }
             **/
        }
        else if (letter.contains("not")) { //reads 'if' statement
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);
            if (!letter.equals("not")) { //misspell catcher
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'not' ?");
                System.exit(0);
            }
        }
            else if (letter.contains("then")) { //reads 'if' statement
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);
            if(!lexeme.equals("then")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("else")) {
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);
            if(!letter.equals("else")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'else' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("fi")) {
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);
            if(!letter.equals("fi")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'fi' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("while")) { //while statements
            System.out.print("\nkind is keyword IterativeStatement: " + letter);
            if(!letter.equals("while")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'while' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("od")) {
            System.out.print("\nkind is keyword IterativeStatement: " + letter);
            if(!letter.equals("od")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'od' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("print")) {
            System.out.print("\nkind is keyword PrintStatement: " + letter);
            if(!letter.equals("print")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'print' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("false")) {
            System.out.print("\nkind is keyword BooleanLiteral: " + letter);
            if(!letter.equals("then")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'false' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("true")) {
            System.out.print("\nkind is keyword BooleanLiteral: " + letter);
            if(!letter.equals("true")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'true' ?");
                System.exit(0);
            }
        }
        else if (letter.contains("end")) {
            System.out.print("\nkind is keyword end of text: " + letter);
            if(!letter.equals("end")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'end' ?");
                System.exit(0);
            }
            System.out.print("""

                    |
                    | end of text file
                    * Lexilyzer for COSC455
                    * by Wesley Lancaster
                    * Submitted on 10/22/22""");
            System.exit(0);
        }


        else if(lexeme.contains(";")){
            System.out.print("\nkind is keyword: " + symbol);
            System.out.print("\nkind is identifier: " + letter);
        }

        else if (symbol.contains("{")) {
            System.out.print("\nkind is symbol: " + symbol);
        } else if (symbol.contains("}")) {
            System.out.print("\nkind is symbol: " + symbol);
        } else if (symbol.contains("[")) {
            System.out.print("\nkind is symbol: " + symbol);
        } else if (symbol.contains("]")) {
            System.out.print("\nkind is symbol: " + symbol);
        }

        else if (symbol.contains("(")) {
            System.out.print("\nkind is symbol: (");
            System.out.print("\nkind is identifier: " + letter);
        } else if (symbol.contains(")")) {
            System.out.print("\nkind is symbol: )");
            System.out.print("\nkind is identifier: " + letter);
        }

        else if (symbol.contains(";")) {
            System.out.print("\nkind is symbol: " + symbol);
        } else if (symbol.contains(":")) {
            System.out.print("\nkind is symbol: " + symbol);
        } else if (operator.contains("|")) {
            System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("<")) {
            System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains(">")) {
            System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("=")) {
            System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("+")) {
            System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("-")) {
            System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("%")) {
            System.out.print("\nkind is operator: " + operator);
        } else if (operator.contains("*")) {
            System.out.print("\nkind is operator: " + operator);
        }
        else if (operator.contains("//")) {
            //????
        }
        else{
            //todo identifyer:
            // this cannot account for 'int' as 'in' how to fix?
            System.out.print("\nkind is unrecignized Identifier: " + letter);
        }
        return i;
    }

    //TODO THE AST TREE
    class ast extends lexicalyzer{
        public void ast (String contents){
        }
    }
}