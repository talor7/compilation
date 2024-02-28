package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_NEW_EXP
 */
public class AST_NEW_EXP extends AST_Node
{

    /****************/
    /* DATA MEMBERS */
    /****************/
    public AST_TYPE type;
    public AST_EXP exp;


	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_NEW_EXP(AST_TYPE type, AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (exp != null) System.out.print("====================== newExp -> NEW type [exp]\n");
		if (exp == null) System.out.print("====================== newExp -> NEW type      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.exp = exp;
	}

	/******************************************************/
	/* The printing message for a expresion list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXPRESION LIST */
		/**************************************/
		System.out.print("AST NODE NEW EXP\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (type != null) type.PrintMe();
		if (exp != null) exp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"NEW\nEXP\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
        // TODO
        return null;
	}
}