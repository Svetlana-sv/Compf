//Стековый компилятор формул.
public class Compf extends Stack {
    protected static String str = "";
    //Типы символов (скобки, знаки операций, иное).
    protected final static int SYM_LEFT = 0,// левая скобка
            SYM_RIGHT = 1,// правая скобка
            SYM_OPER = 2,// операция
            SYM_OTHER = 3;

    public Compf() {
    }

    private int symType(char c) {
        switch (c) {
            case '(':
                return SYM_LEFT;
            case ')':
                return SYM_RIGHT;
            case '+':
            case '-':
            case '*':
            case '/':
                return SYM_OPER;
            default:
                return symOther(c);
        }
    }

    private void processSymbol(char c) {
        switch (symType(c)) {
            case SYM_LEFT:
                push(c);
                break;
            case SYM_RIGHT:
                processSuspendedSymbols(c);
                pop();
                break;
            case SYM_OPER:
                processSuspendedSymbols(c);
                push(c);
                break;
            case SYM_OTHER:
                nextOther(c);
                break;
        }
    }

    private void processSuspendedSymbols(char c) {
        while (precedes(top(), c))
            nextOper(pop());
    }

    private int priority(char c) {
        return c == '+' || c == '-' ? 1 : 2;
    }

    private boolean precedes(char a, char b) {
        if (symType(a) == SYM_LEFT) return false;
        if (symType(b) == SYM_RIGHT) return true;

        return priority(a) >= priority(b);
    }

    protected int symOther(char c) {
        if (c < 'a' || c > 'z') {
            System.out.println("Недопустимый символ: " + c);
            System.exit(0);
        }

        return SYM_OTHER;
    }

    protected void nextOper(char c) {
        str += c;
        if (Character.toString(top()).matches("([0-9]|[a-z])+")) {
        } else {
            System.out.print(str+" ");
            str = "";
        }
        }

    protected void nextOther(char c) {
        nextOper(c);
    }

    public void compile(char[] str) {
        processSymbol('(');

        for (int i = 0; i < str.length; i++)
            processSymbol(str[i]);

        processSymbol(')');

        System.out.print("\n");
    }
}
// 1 задание: мгозначность сделать
