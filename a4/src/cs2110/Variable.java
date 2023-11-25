package cs2110;

import java.util.HashSet;
import java.util.Set;

/**
 * Representation of a named variable
 */
public class Variable implements Expression{

    private String name;

    public Variable(String n){
        name=n;
    }

    /**
     * Returns the value of their variable in VarTable if it exists,
     * otherwise throws an UnboundVariableException
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return vars.get(name);
    }

    /**
     * Returns the number of operations used
     */
    @Override
    public int opCount() {
        return 0;
    }

    /**
     * Returns the name of the variable
     */
    @Override
    public String infixString() {
        return name;
    }

    /**
     * Returns the name of the variable
     */
    @Override
    public String postfixString() {
        return name;
    }

    /**
     * Returns whether two variables share the same variable name
     */
    public boolean equals(Object n){
        if (n == null || n.getClass() != getClass()) {
            return false;
        }
        return ((Variable) n).name.equals(name);
    }

    /**
     * Returns a new constant with the value of the variable if the variable
     * is in the VarTable provided, otherwise returns this variable
     */
    @Override
    public Expression optimize(VarTable vars) {
        if(vars.contains(name)){
            try {
                return new Constant(eval(vars));
            } catch (UnboundVariableException e) {
                return this;
            }

        }else{
            return this;
        }
    }
    /**
     * Returns a Set of Strings with the name of the variable used
     */
    @Override
    public Set<String> dependencies() {
        Set<String> v = new HashSet<>();
        v.add(name);
        return v;
    }
}
