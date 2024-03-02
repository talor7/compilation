package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_BINOP extends AST_EXP
{
	public int OP;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left, AST_EXP right, int OP)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		String sOP = "";
		
		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
		if (OP == 2) {sOP = "*";}
		if (OP == 3) {sOP = "/";}
		if (OP == 4) {sOP = "<";}
		if (OP == 5) {sOP = ">";}
		if (OP == 6) {sOP = "=";}

		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE BINOP(" + sOP + ") EXP\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format(String.format("EXP BINOP(%s)", sOP)));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, right.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
        TYPE leftType = left.SemantMe();
        TYPE rightType = right.SemantMe();
        if (OP == 0) // +
        {
            if (leftType == TYPE_STRING.getInstance() && rightType == TYPE_STRING.getInstance())
                return TYPE_STRING.getInstance();
			if (leftType == TYPE_INT.getInstance() && rightType == TYPE_INT.getInstance())
                return TYPE_INT.getInstance();
				
			throw new Exception(String.format("ERROR(%d)\n", line));
        }
        else if (OP == 6) // =
        {
            if (leftType == rightType)
                return TYPE_INT.getInstance();
            
            if (left instanceof AST_EXP_NIL && (rightType.isClass() || rightType.isArray()))
                return TYPE_INT.getInstance();
            if (right instanceof AST_EXP_NIL && (leftType.isClass() || leftType.isArray()))
                return TYPE_INT.getInstance();
            
            if (leftType.isClass() && rightType.isClass()
                && (((TYPE_CLASS)leftType).isSubClassOf(((TYPE_CLASS)rightType))
                     || ((TYPE_CLASS)rightType).isSubClassOf(((TYPE_CLASS)leftType))))
                return TYPE_INT.getInstance();
        }
        else if (OP == 3) // /
        {
            if (leftType == TYPE_INT.getInstance() && rightType == TYPE_INT.getInstance())
            {
                if (!(right instanceof AST_EXP_INT) || ((AST_EXP_INT)right).value != 0)
                    return TYPE_INT.getInstance();
            }
        }
        else
        {
            if (leftType == TYPE_INT.getInstance() && rightType == TYPE_INT.getInstance())
                return TYPE_INT.getInstance();
        }

        throw new Exception(String.format("ERROR(%d)\n", line));
	}
}
