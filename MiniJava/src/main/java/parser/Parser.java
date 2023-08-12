package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import Log.Log;
import codeGenerator.CGFacade;
import errorHandler.ErrorHandler;
import scanner.LAFacade;
import scanner.token.Token;

public class Parser {
    private ArrayList<Rule> rules;
    private Stack<Integer> parsStack;
    private ParseTable parseTable;
    private LAFacade lexicalAnalyzerFacade;
    private CGFacade cgFacade;

    public Parser() {
        setParsStack(new Stack<Integer>());
        getParsStack().push(0);
        try {
            setParseTable(new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRules(new ArrayList<Rule>());
        try {
            for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
                getRules().add(new Rule(stringRule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCgFacade(new CGFacade());
    }

    public void startParse(java.util.Scanner sc) {
        setLexicalAnalyzerFacade(new LAFacade(sc));
        Token lookAhead = getLexicalAnalyzerFacade().getNextToken();
        boolean finish = false;
        Action currentAction;
        while (!finish) {
            try {
                Log.print(/*"lookahead : "+*/ lookAhead.toString() + "\t" + getParsStack().peek());
//                Log.print("state : "+ parsStack.peek());
                currentAction = getParseTable().getActionTable(getParsStack().peek(), lookAhead);
                Log.print(currentAction.toString());
                //Log.print("");

                switch (currentAction.getAction()) {
                    case "shift":
                        lookAhead = getToken(currentAction);
                        break;
                    case "reduce":
                        extracted(lookAhead, currentAction);
                        break;
                    case "accept":
                        finish = true;
                        break;
                }
                Log.print("");
            } catch (Exception ignored) {
                ignored.printStackTrace();
//                boolean find = false;
//                for (NonTerminal t : NonTerminal.values()) {
//                    if (parseTable.getGotoTable(parsStack.peek(), t) != -1) {
//                        find = true;
//                        parsStack.push(parseTable.getGotoTable(parsStack.peek(), t));
//                        StringBuilder tokenFollow = new StringBuilder();
//                        tokenFollow.append(String.format("|(?<%s>%s)", t.name(), t.pattern));
//                        Matcher matcher = Pattern.compile(tokenFollow.substring(1)).matcher(lookAhead.toString());
//                        while (!matcher.find()) {
//                            lookAhead = lexicalAnalyzer.getNextToken();
//                        }
//                    }
//                }
//                if (!find)
//                    parsStack.pop();
            }
        }
        if (!ErrorHandler.hasError) getCgFacade().printMemory();
    }

    private void extracted(Token lookAhead, Action currentAction) {
        Rule rule = getRules().get(currentAction.number);
        for (int i = 0; i < rule.RHS.size(); i++) {
            getParsStack().pop();
        }

        Log.print(/*"state : " +*/ getParsStack().peek() + "\t" + rule.LHS);
//                        Log.print("LHS : "+rule.LHS);
        getParsStack().push(getParseTable().getGotoTable(getParsStack().peek(), rule.LHS));
        Log.print(/*"new State : " + */getParsStack().peek() + "");
//                        Log.print("");
        try {
            getCgFacade().semanticFunction(rule.semanticAction, lookAhead);
        } catch (Exception e) {
            Log.print("Code Genetator Error");
        }
        return;
    }

    private Token getToken(Action currentAction) {
        Token lookAhead;
        getParsStack().push(currentAction.number);
        lookAhead = getLexicalAnalyzerFacade().getNextToken();
        return lookAhead;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public Stack<Integer> getParsStack() {
        return parsStack;
    }

    public void setParsStack(Stack<Integer> parsStack) {
        this.parsStack = parsStack;
    }

    public ParseTable getParseTable() {
        return parseTable;
    }

    public void setParseTable(ParseTable parseTable) {
        this.parseTable = parseTable;
    }

    public LAFacade getLexicalAnalyzerFacade() {
        return lexicalAnalyzerFacade;
    }

    public void setLexicalAnalyzerFacade(LAFacade lexicalAnalyzerFacade) {
        this.lexicalAnalyzerFacade = lexicalAnalyzerFacade;
    }

    public CGFacade getCgFacade() {
        return cgFacade;
    }

    public void setCgFacade(CGFacade cgFacade) {
        this.cgFacade = cgFacade;
    }
}
