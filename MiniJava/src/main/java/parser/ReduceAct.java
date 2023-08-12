package parser;

public class ReduceAct extends Action {

    public ReduceAct(int number) {
        super(number);
    }

    @Override
    public String toString() {
        return "r";
    }

    @Override
    public String getAction() {
        return "reduce";
    }
}
