package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_STMT_VARDEC
 */
public class AST_STMT_VARDEC extends AST_STMT
{

    public AST_DEC_VAR var;

    public AST_STMT_VARDEC(AST_DEC_VAR var)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
	}


    public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST STMT VARDEC   */
		/*************************************/
		System.out.print("AST STMT_VARDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (var != null) var.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STMT(VARDEC)"));


        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
	}
    
	public TYPE SemantMe() throws Exception
	{
		TYPE t = var.SemantMe();
		return t;
	}
}