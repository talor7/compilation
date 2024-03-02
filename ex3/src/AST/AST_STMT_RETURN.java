package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_STMT_RETURN
 */
public class AST_STMT_RETURN extends AST_STMT
{

    public AST_EXP exp;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_RETURN(AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (exp != null) System.out.print("====================== stmt -> RETURN exp;\n");
		if (exp == null) System.out.print("====================== stmt -> RETURN;\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.exp = exp;
	}
	
	/***********************************************/
	/* The default message for an exp var AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = RETURN STMT AST NODE */
		/************************************/
		System.out.print("AST NODE RETURN STMT\n");

		/*****************************/
		/* RECURSIVELY PRINT exp ... */
		/*****************************/
		if (exp != null) exp.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"RETURN STMT\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
			
	}
    
	public TYPE SemantMe() throws Exception
	{
        TYPE returnType = SYMBOL_TABLE.getInstance().currentFuncReturnType;
        if (returnType == null)
            throw new Exception(String.format("ERROR(%d)\n", line));
        if (exp == null && returnType != TYPE_VOID.getInstance())
            throw new Exception(String.format("ERROR(%d)\n", line));
        if (exp != null && returnType == TYPE_VOID.getInstance())
            throw new Exception(String.format("ERROR(%d)\n", line));
        if (exp != null && returnType != TYPE_VOID.getInstance())
        {
            TYPE expType = exp.SemantMe();
            if ((!expType.isClass() || !returnType.isClass()) && returnType != expType)
                throw new Exception(String.format("ERROR(%d)\n", line));
            if ((expType.isClass() && returnType.isClass())
                && !((TYPE_CLASS)expType).isSubClassOf(((TYPE_CLASS)returnType)))
                throw new Exception(String.format("ERROR(%d)\n", line));
        }
		return null;
	}
}