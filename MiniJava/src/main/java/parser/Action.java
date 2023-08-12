package parser;

public abstract class Action {
    //if action = shift : number is state
    //if action = reduce : number is number of rule
    public int number;

    public Action(int number) {
        this.number = number;
    }

    public String getAction() {
        return "act";
    }
}

