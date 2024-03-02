package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_CFIELD_FUNC
 */
public class AST_CFIELD_FUNC extends AST_CFIELD
{

    public AST_DEC_FUNC func;

    public AST_CFIELD_FUNC(AST_DEC_FUNC func)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== cField -> funcDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.func = func;
	}


    public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST CFIELD FUNC */
		/*************************************/
		System.out.print("AST CFIELD_FUNC\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (func != null) func.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CFIELD(FUNC)"));


        if (func != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, func.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
        TYPE t = func.SemantMe();
        return new TYPE_CLASS_VAR_DEC(t, func.id);
	}
}