package cs2110;

import java.util.Set;
import java.util.function.DoubleUnaryOperator;

/**
 * Representation of the application of a function to an argument;
 * the argument can be any non-empty subexpression
 */
public class Application implements Expression {
    private Expression argument;

    private UnaryFunction func;

    public Application(UnaryFunction f, Expression a) {
        argument = a;
        func = f;
    }

    /**
     * Returns true if two applications have equal function and arguments and false otherwise
     */
    public boolean equals(Object n){
        if (n == null || n.getClass() != getClass()) {
            return false;
        }
        return ((Application) n).func.name().equals(func.name())
                && ((Application) n).argument.equals(argument);
    }

    /**
     * Given a unary function and expression, returns the value of applying
     * the application's function to the value of its argument child
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return func.apply(argument.eval(vars));
    }

    /**
     * Returns the number of operations used
     */
    @Override
    public int opCount() {
        return 1 + argument.opCount();
    }

    /**
     * Returns the infix string representation of its argument
     */
    @Override
    public String infixString() {
        return func.name()+"("+argument.infixString()+")";
    }

    /**
     * Returns the postfix string representation of its argument
     */
    @Override
    public String postfixString() {
        return argument.postfixString()+" "+ func.name()+"()";
    }

    /**
     * Returns an Application Node with a constant as an argument if all
     * variables can be found in the given VarTable, otherwise returns the original
     * Application Node
     */
    @Override
    public Expression optimize(VarTable vars) {
        if(argument.optimize(vars) instanceof Constant){
            try {
                return new Constant(eval(vars));
            } catch (UnboundVariableException e) {
                return new Application(func, argument.optimize(vars));
            }
        }else{
            return new Application(func, argument.optimize(vars));
        }
    }

    /**
     * Returns a Set of Strings containing all the names of the variables in the
     * argument of the Application node
     */
    @Override
    public Set<String> dependencies() {
        return argument.dependencies();
    }
}
