package parser;

public class AcceptAct extends Action {

    public AcceptAct(int number) {
        super(number);
    }

    @Override
    public String toString() {
        return "acc";
    }

    @Override
    public String getAction() {
        return "accept";
    }
}
