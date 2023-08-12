package parser;

public class ShiftAct extends Action {

    public ShiftAct(int number) {
        super(number);
    }

    @Override
    public String toString() {
        return "s";
    }

    @Override
    public String getAction() {
        return "shift";
    }
}

