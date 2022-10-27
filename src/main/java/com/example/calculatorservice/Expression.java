package com.example.calculatorservice;

public class Expression {
    private String left;
    private String right;
    private String op;

    public Expression(String left, String right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }
    
    public Expression() {
    }

    public String getLeft() {
        return left;
    }
    public void setLeft(String left) {
        this.left = left;
    }
    public String getRight() {
        return right;
    }
    public void setRight(String right) {
        this.right = right;
    }
    public String getOp() {
        return op;
    }
    public void setOp(String op) {
        this.op = op;
    }

}
