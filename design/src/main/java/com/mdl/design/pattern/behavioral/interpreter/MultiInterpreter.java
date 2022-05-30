package com.mdl.design.pattern.behavioral.interpreter;

/**
 * Created by meidanlong.
 */
public class MultiInterpreter implements Interpreter {

    private Interpreter firstExpression,secondExpression;
    public MultiInterpreter(Interpreter firstExpression, Interpreter secondExpression){
        this.firstExpression=firstExpression;
        this.secondExpression=secondExpression;
    }
    @Override
    public int interpret(){
        return this.firstExpression.interpret()*this.secondExpression.interpret();
    }
    @Override
    public String toString(){
        return "*";
    }

}
