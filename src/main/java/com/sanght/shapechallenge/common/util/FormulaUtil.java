package com.sanght.shapechallenge.common.util;

import com.sanght.shapechallenge.domain.Formula;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.PrimitiveElement;

import java.util.Map;

public class FormulaUtil {
    public static double calculate(Formula formula, Map<String, Double> dimensions) {
        Function function = new Function(formula.getFunction());
        String fName = formula.getFunction().split("=", 2)[0];
        String[] args = formula.getArguments().split(", ");
        PrimitiveElement[] params = new PrimitiveElement[args.length + 1];
        params[0] = function;
        for (int i = 1, len = args.length + 1; i < len; i++) {
            Double value = dimensions.get(args[i - 1]);
            Argument arg = new Argument(args[i - 1] + " = " + value);
            params[i] = arg;
        }
        Expression exp = new Expression(fName, params);
        return exp.calculate();
    }

    public static void main(String [] args) {
        Function at = new Function("at(a,b) = 1/2 * a * b");
        Expression e1 = new Expression("at(2,4)", at);
        System.out.println(e1.calculate());

        Argument a1 = new Argument("b = 3");
        Argument a2 = new Argument("h = 10");

        PrimitiveElement[] ar = new PrimitiveElement[3];
        ar[0] = at;
        ar[1] = a1;
        ar[2] = a2;
        Expression e2 = new Expression("at(B,h)", ar);
        System.out.println(e2.calculate());
    }
}
