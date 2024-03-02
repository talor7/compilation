package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CFIELD_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_CFIELD head;
	public AST_CFIELD_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CFIELD_LIST(AST_CFIELD head, AST_CFIELD_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== cFieldList -> cField cFieldList\n");
		if (tail == null) System.out.print("====================== cFieldList -> cField           \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE CFIELD LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CFIELD\nLIST\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}

	public TYPE_CLASS_VAR_DEC_LIST SemantMe(TYPE_CLASS type) throws Exception
	{
		if (tail == null)
		{
			type.data_members =  new TYPE_CLASS_VAR_DEC_LIST(
				(TYPE_CLASS_VAR_DEC)head.SemantMe(),
				type.data_members);
		}
		else
		{
			type.data_members =  new TYPE_CLASS_VAR_DEC_LIST(
				(TYPE_CLASS_VAR_DEC)head.SemantMe(),
				(TYPE_CLASS_VAR_DEC_LIST)type.data_members);
            tail.SemantMe(type);
		}
        return null;
	}
	
}
