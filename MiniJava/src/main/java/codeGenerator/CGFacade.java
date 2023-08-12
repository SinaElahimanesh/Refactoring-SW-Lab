package codeGenerator;

import scanner.token.Token;

public class CGFacade {

    private CodeGenerator codeGenerator;

    public CGFacade() {
        codeGenerator = new CodeGenerator();
    }

    public void semanticFunction(int func, Token next) {
        codeGenerator.semanticFunction(func, next);
    }

    public void printMemory() {
        codeGenerator.printMemory();
    }
}
