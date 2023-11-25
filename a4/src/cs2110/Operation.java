package cs2110;

import java.util.Set;

/**
 * Representation of the binary operators common in arithmetic requiring two operands
 */
public class Operation implements Expression{
    protected Expression leftOperand;
    protected Expression rightOperand;
    protected Operator op;

    public Operation(Operator b, Expression a, Expression c){
       // op=op;
        leftOperand=a;
        rightOperand=c;
        op=b;
    }

    /**
     * Returns true if two operations have equal operands and operators and false otherwise
     */
    public boolean equals(Object n){
        if (n == null || n.getClass() != getClass()) {
            return false;
        }
        return ((Operation) n).leftOperand.equals(leftOperand)
                && ((Operation) n).rightOperand.equals(rightOperand) && ((Operation) n).op.symbol()
                .equals(op.symbol());
    }

    /**
     * Given two expressions and an operator, returns the value of evaluating both of
     * its operand children and combining those value with its operator
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return op.operate(leftOperand.eval(vars),rightOperand.eval(vars));
    }
    /**
     * Returns the number of operations used
     */
    @Override
    public int opCount() {
        return 1 + leftOperand.opCount() + rightOperand.opCount();
    }

    /**
     * Returns the infix string representation of its argument
     */
    @Override
    public String infixString() {
        return "("+ leftOperand.infixString() + " " + op.symbol() +  " " + rightOperand.infixString() + ")";
    }
    /**
     * Returns the postfix string representation of its argument
     */
    @Override
    public String postfixString() {
        return leftOperand.postfixString()+ " " + rightOperand.postfixString() + " " + op.symbol();
    }

    /**
     * Returns an Operation Node with constants as operands if all variables can be found
     * in the given VarTable, otherwise returns the original Operation Node
     */
    @Override
    public Expression optimize(VarTable vars) {
        if(rightOperand.optimize(vars) instanceof Constant && leftOperand.optimize(vars) instanceof Constant){
            try {
                return new Constant(eval(vars));
            } catch (UnboundVariableException e) {
                return new Operation(op, leftOperand.optimize(vars), rightOperand.optimize(vars));
            }
        }else{
            return new Operation(op, leftOperand.optimize(vars), rightOperand.optimize(vars));
        }
    }

    /**
     * Returns a Set of Strings containing all the names of the variables in the
     * operands of the Operation node
     */
    @Override
    public Set<String> dependencies() {
        Set<String> a = leftOperand.dependencies();
        Set<String> b = rightOperand.dependencies();
        for (String vars : b) {
            if (a.add(vars)) {
                a.add(vars);
            }
        }
        return a;
    }
}
