package scanner;

import scanner.token.Token;

public class LAFacade {
    private lexicalAnalyzer lexicalAnalyzer;

    public LAFacade(java.util.Scanner sc) {
        lexicalAnalyzer = new lexicalAnalyzer(sc);
    }

    public Token getNextToken() {
        return lexicalAnalyzer.getNextToken();
    }
}
