/**
 * Lexilyer for COSC 455
 * by Wesley Lancaster
 * Submitted on 10/22/22
 * @ wlanca2@students.towson.edu
 *
 * sample location: /Users/johnmetz/Desktop/cosc455/Project1Lex455
 **/
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class lexicalyzer {
    public static void main(String[] args){

        System.out.print("""
                |
                * Lexilyzer for COSC 455
                * by Wesley Lancaster
                * Submitted on 10/22/22""");

        Scanner fileName = new Scanner(System.in);
        System.out.println("\n|\nplease enter directory location of .txt file.....");
        String name = fileName.nextLine();
        System.out.print(".....path entered");
        fileName.close();
        File x = new File(name);

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
            System.out.print("|\nNext Line: " + text + "\nlength of this line is: " + text.length() + "\n");
            String lexeme = " ";

            next n = new next();
            position p = new position();
            kind k = new kind();
            //value v = new value();

            int i = 0;
            int l = 1;
            boolean noError = true;

//THE CORE LOOP---------------------------------------------------------------------------------------------------------
            // TODO 1 how do I ommit an empty line between lines? calling nextline() twice just breaks everything
            while (sc.hasNextLine() && noError) {
                Scanner txt = new Scanner(text);

                while (txt.hasNext()) {
                    lexeme = n.next(txt); //calls next lexeme
                    System.out.print("\nlexeme being read is: " + lexeme);
                    i = text.indexOf(lexeme);
                    p.position(i, l); //returns current position of lexeme
                    k.kind(lexeme, txt); //v.value(lexeme) is called inside the kind() class
                    System.out.print("\n");

                    if (!txt.hasNext()) { //iterating to the next line
                        text = sc.nextLine();
                        System.out.print("|\nNext Line: " + text + "\nlength of this line is: " + text.length() + "\n");
                        i = 0;
                        l++;
                    }
                }

                if(!sc.hasNextLine()){ //internal to make sure the last line is called and ends with "end"
                    if(text.contains("end")){
                        System.out.print("\nlexeme being read is: "+ text +
                                "\nposition within line is: "+i +" and position is line: "+l+
                                "\nkind is: end of file declaration: end\n" +
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

        } catch (FileNotFoundException e) {
            System.out.println("Error: file was not found (exception e)");
        }
    }
}

//THE NEXT METH---------------------------------------------------------------------------------------------------------
class next extends lexicalyzer {

    //todo A: need to make my own method for reading next lines... by storing the entire .txt into a String
    public String next(Scanner txt) {
        String lexeme = txt.next();
        return lexeme;
    }

    public String nextLine(String txt) {
        //todo B: this one somehow needs to figure out what lines are
        String line = txt;
        return line;
    }

    public boolean hasNextLine(String txt) {
        //todo C: this one will mechanically branch off of nextLine(), have exception()
        String line = txt;
        boolean tr = false;
        return tr;
    }

    public boolean endOfLine(String txt) {
        //todo D: this one returns true if the the current position is the end of the last lexeme
        String line = txt;
        boolean tr = false;
        return tr;
    }
}

//THE POSITION METH-----------------------------------------------------------------------------------------------------
class position extends lexicalyzer {
    public void position(int i, int l) {
        System.out.print("\nposition within line is: "+i+" and position is line: "+l);
    }
}

//THE VALUE METH--------------------------------------------------------------------------------------------------------
class value extends lexicalyzer {
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

//THE KIND METH---------------------------------------------------------------------------------------------------------
class kind extends lexicalyzer {
    //TODO 3 I need to store the identifiers for future loops.
    //TODO 4 this does not account for identifiers with names similar to keywords. like 'print' and 'printy'
    boolean noError = true;

    public Scanner kind(String lexeme, Scanner txt) {
        kind k = new kind();
        value v = new value();

        if (lexeme.contains("//")) {
            v.value(lexeme, noError);
            System.out.print("\nkind is: Single comment: " + lexeme);
            while (txt.hasNext()) {
                System.out.print(" " + txt.next());
            }
        }

        String letter = "";
        String number = "";
        String symbol = "";
        String operator = "";

        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        StringBuilder stringBuilder4 = new StringBuilder();

        for (int i = 0; i < lexeme.length(); i++) {
            if (lexeme.charAt(i) == 'a' || lexeme.charAt(i) == 'b' || lexeme.charAt(i) == 'c' || lexeme.charAt(i) == 'd'
                    || lexeme.charAt(i) == 'e' || lexeme.charAt(i) == 'f' || lexeme.charAt(i) == 'g' || lexeme.charAt(i) == 'h'
                    || lexeme.charAt(i) == 'i' || lexeme.charAt(i) == 'j' || lexeme.charAt(i) == 'k' || lexeme.charAt(i) == 'l'
                    || lexeme.charAt(i) == 'm' || lexeme.charAt(i) == 'n' || lexeme.charAt(i) == 'o' || lexeme.charAt(i) == 'p'
                    || lexeme.charAt(i) == 'q' || lexeme.charAt(i) == 'r' || lexeme.charAt(i) == 's' || lexeme.charAt(i) == 't'
                    || lexeme.charAt(i) == 'u' || lexeme.charAt(i) == 'v' || lexeme.charAt(i) == 'w' || lexeme.charAt(i) == 'x'
                    || lexeme.charAt(i) == 'y' || lexeme.charAt(i) == 'z'
                    || lexeme.charAt(i) == 'A' || lexeme.charAt(i) == 'B' || lexeme.charAt(i) == 'C' || lexeme.charAt(i) == 'D'
                    || lexeme.charAt(i) == 'E' || lexeme.charAt(i) == 'F' || lexeme.charAt(i) == 'G' || lexeme.charAt(i) == 'H'
                    || lexeme.charAt(i) == 'I' || lexeme.charAt(i) == 'J' || lexeme.charAt(i) == 'K' || lexeme.charAt(i) == 'L'
                    || lexeme.charAt(i) == 'M' || lexeme.charAt(i) == 'N' || lexeme.charAt(i) == 'O' || lexeme.charAt(i) == 'P'
                    || lexeme.charAt(i) == 'Q' || lexeme.charAt(i) == 'R' || lexeme.charAt(i) == 'S' || lexeme.charAt(i) == 'T'
                    || lexeme.charAt(i) == 'U' || lexeme.charAt(i) == 'V' || lexeme.charAt(i) == 'W' || lexeme.charAt(i) == 'X'
                    || lexeme.charAt(i) == 'Y' || lexeme.charAt(i) == 'Z') {
                char chaL = lexeme.charAt(i);
                stringBuilder1.append(letter).append(chaL);
            }

            if (lexeme.charAt(i) == '1' || lexeme.charAt(i) == '2' || lexeme.charAt(i) == '3' || lexeme.charAt(i) == '4'
                    || lexeme.charAt(i) == '5' || lexeme.charAt(i) == '6' || lexeme.charAt(i) == '7' || lexeme.charAt(i) == '8'
                    || lexeme.charAt(i) == '9' || lexeme.charAt(i) == '0') {
                char chaN = lexeme.charAt(i);
                stringBuilder2.append(number).append(chaN);
                v.value(number, noError);
            }
            if (lexeme.charAt(i) == '{' || lexeme.charAt(i) == '}' || lexeme.charAt(i) == '(' || lexeme.charAt(i) == ')'
                    || lexeme.charAt(i) == ';' || lexeme.charAt(i) == '[' || lexeme.charAt(i) == ']' || lexeme.charAt(i) == ':') {
                char chaS = lexeme.charAt(i);
                stringBuilder3.append(symbol).append(chaS);
            }
            if (lexeme.charAt(i) == '+' || lexeme.charAt(i) == '-' || lexeme.charAt(i) == '/' || lexeme.charAt(i) == '*'
                    || lexeme.charAt(i) == '<' || lexeme.charAt(i) == '>' || lexeme.charAt(i) == '=' || lexeme.charAt(i) == '!'
                    || lexeme.charAt(i) == '|' || lexeme.charAt(i) == '%' || lexeme.charAt(i) == ':') {
                char chaR = lexeme.charAt(i);
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

        if (lexeme.contains(":") || lexeme.contains("=")) {
            if(!lexeme.matches(":=")){
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN ':=' ?");
                System.exit(0);
            }
            else if(lexeme.contains(":=")||lexeme.contains("=<")||lexeme.contains(">=")||lexeme.contains("!=")){
                System.out.print("\nkind is RelationalOperator: " + lexeme);
            }
        }

        else if (letter.contains("program")) {
            System.out.print("\nkind is keyword: " + letter);
            if(!letter.matches("program")){ //if int is misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'program' ?");
                System.exit(0);
            }
            if(txt.hasNext()){
                letter = txt.next();
            }
            else{
                System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM MUST BE IDENTIFIED FOLLOWED BY ':' ");
                System.exit(0);
            }
            if(letter.contains(":")){
                System.out.print("\nidentifier: " + letter);
            }
            else if (!letter.contains(":")){
                System.out.print("\nidentifier: " + letter);
                if(txt.hasNext()){
                    letter = txt.next();
                }
                if(letter.contains(":")){
                    System.out.print(letter);
                }
                else if(!letter.contains(":")){
                    System.out.print(" \nSYNTAX ERROR DETECTED, PROGRAM IDENTIFIER MUST BE FOLLOWED BY ':' ?");
                    System.exit(0);
                }
            }
        }

        //todo 3 fix me
        else if (letter.contains("int") && !letter.contains("print")) { //reads keyword 'int'
            System.out.print("\nkind is keyword Declaration: " + letter);

            if(!letter.matches("int")){ //if int is misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'int' ?");
                System.exit(0);
            }
            if(!txt.hasNext()){ //if int isn't given a name to initialize.
                System.out.print(" \nSYNTAX ERROR DETECTED, 'int' MUST BE USED TO INITIALIZE A IDENTIFIER");
                System.exit(0);
            }
            //while(!letter.contains(";"))
            //if(!txt.hasNext)
            //{} print "error this needs to end with ';'
            //{} somehow loop through each item, and return its value, kind and position
            //if identifier = true and .next() is also an identifier and not an operator, return an error.
        }
        //todo 3 fix me
        else if (letter.contains("bool")) { //reads keyword
            System.out.print("\nkind is keyword: " + letter);

            if(!letter.matches("bool")){
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'bool' ?");
                System.exit(0);
            }
        }
        //todo 3 fix me
        else if (letter.contains("if")) { //reads 'if' statement
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);

            if(!letter.matches("if")){ //misspell catcher
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'if' ?");
                System.exit(0);
            }
            letter = txt.next();
            if(letter.contains("not")){
                if(!letter.matches("not")){ //misspell catcher
                    System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'not' ?");
                    System.exit(0);
                }
                while(!letter.contains("then")){//this is an issue

                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                    if(letter.contains("then")){
                        if(!letter.matches("then")){ //misspell catcher
                            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                            System.exit(0);
                        }
                    }
                }
                while(txt.hasNext()) {


                    //add stuff to this so contents are also lexilized


                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                }
            }
            else if(!letter.contains("not")){
                while(!letter.contains("then")){
                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                    if(letter.contains("then")){
                        if(!letter.matches("then")){ //misspell catcher
                            System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'then' ?");
                            System.exit(0);
                        }
                    }
                }
                while(txt.hasNext()) {


                    //add stuff to this so contents are also lexilized


                    System.out.print(" " + letter + " ");
                    letter = txt.next();
                }
            }
        }
        //todo 3 fix me
        else if (letter.contains("else")) {
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);

            if(!letter.matches("else")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'else' ?");
                System.exit(0);
            }
        }

        else if (letter.contains("fi")) {
            System.out.print("\nkind is keyword ConditionalStatement: " + letter);
            if(!letter.matches("fi")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'fi' ?");
                System.exit(0);
            }
        }
        //todo 3 fix me
        else if (letter.contains("while")) { //while statements
            System.out.print("\nkind is keyword IterativeStatement: " + letter);

            if(!letter.matches("while")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'while' ?");
                System.exit(0);
            }
        }

        else if (letter.contains("od")) {
            System.out.print("\nkind is keyword IterativeStatement: " + letter);

            if(!letter.matches("od")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'od' ?");
                System.exit(0);
            }
        }

        else if (letter.contains("print")) {
            System.out.print("\nkind is keyword PrintStatement: " + letter);

            if(!letter.matches("print")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'print' ?");
                System.exit(0);
            }
        }

        else if (letter.contains("false")) {
            System.out.print("\nkind is keyword BooleanLiteral: " + letter);

            if(!letter.matches("false")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'false' ?");
                System.exit(0);
            }
        }

        else if (letter.contains("true")) {
            System.out.print("\nkind is keyword BooleanLiteral: " + letter);
            if(!letter.matches("true")){ //if misspelled
                System.out.print(" \nSYNTAX ERROR DETECTED, DID YOU MEAN 'true' ?");
                System.exit(0);
            }
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
        }
        else{
            System.out.print("\nkind is Identifier: " + letter);
        }

        return txt;
    }

    //TODO N THE AST TREE
    class ast extends lexicalyzer{
        public void ast (String contents){
        }
    }
}







//LINE 500!! :D