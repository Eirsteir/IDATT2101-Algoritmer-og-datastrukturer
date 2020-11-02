public class Main {

    public static void main(String[] args) {
        Rule a = new Rule(3, "fizz");
        Rule b = new Rule(5, "buzz");
        Rule c = new Rule(15, "fizzbuzz");

        fizzBuzz(new Rule[]{a, b, c});
    }

    static void fizzBuzz(Rule[] rules) {
        String output = "";
        for (int n = 1; n <= 100; n++) {
            output = String.valueOf(n);
            for (Rule rule : rules)
                if (rule.evaluateCondition(n))
                    output = rule.output;

            System.out.println(output);
        }
    }

    static void fizzBuzzExtended(Rule[] rules) {
        String output = "";
        for (int n = 1; n <= 100; n++) {
            output = String.valueOf(n);
            for (Rule rule : rules)
                if (rule.evaluateCondition(n))
                    output += rule.output;

            System.out.println(output.isBlank() ? n : output);
        }
    }

}

class Rule {
    private int conditionValue;
    String output;

    public Rule(int conditionValue, String output) {
        this.conditionValue = conditionValue;
        this.output = output;
    }

    public boolean evaluateCondition(int n) {
        return n % conditionValue == 0;
    }
}