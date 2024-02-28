package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_STMT_FUNC
 */
public class AST_STMT_FUNC extends AST_STMT
{
	public AST_VAR v;
	public String id;
	public AST_EXP_LIST expl;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_FUNC(AST_VAR v, String id, AST_EXP_LIST expl)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (v != null && expl != null) System.out.print("====================== stmt -> var.ID()\n");
		if (v != null && expl == null) System.out.print("====================== stmt -> var.ID(exp [, exp]*)\n");
		if (v == null && expl != null) System.out.print("====================== stmt -> ID()\n");
		if (v == null && expl == null) System.out.print("====================== stmt -> ID(exp [, exp]*)\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
		this.id = id;
		this.expl = expl;
	}

	/******************************************************/
	/* The printing message for a AST node                */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE                      */
		/**************************************/
		System.out.print("AST NODE STMT FUNC\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (v != null) v.PrintMe();
		if (expl != null) expl.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STMT FUNC(%s)", id));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, v.SerialNumber);
		if (expl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, expl.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
        // TODO
        if (v == null)
        {
            // TODO: check if function exists and if the parameters match
        }
        else
        {
            // TODO: check if function exists in the class and if the parameters match
        }
        return null;
	}
}