package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_DEC_FUNC
 */
public class AST_DEC_FUNC extends AST_DEC
{

	public AST_TYPE type;
	public String id;
	public AST_TYPEID_LIST args;
	public AST_STMT_LIST body;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_FUNC(AST_TYPE type, String id, AST_TYPEID_LIST args, AST_STMT_LIST body)	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> funcDec\n");
		if(args != null) System.out.format("====================== funcDec -> type ID( %s ) (typeIdList) { stmtList }\n", id);
		if(args == null) System.out.format("====================== funcDec -> type ID( %s ) () { stmtList }          \n", id);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.id = id;
		this.args = args;
		this.body = body;
	}

	/******************************************************/
	/* The printing message for a AST node                */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE                      */
		/**************************************/
		System.out.print("AST NODE FUNC DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (type != null) type.PrintMe();
		if (args != null) args.PrintMe();
		if (body != null) body.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("Func Dec(%s)", id));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
		if (args != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
        // TODO: check the name of the function
		TYPE t;
		TYPE returnType = null;
		TYPE_LIST type_list = null;

		/*******************/
		/* [0] return type */
		/*******************/
		returnType = type.SemantMe();
	
		/****************************/
		/* [1] Begin Function Scope */
		/****************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Input Params */
		/***************************/
		for (AST_TYPEID_LIST it = args; it  != null; it = it.tail)
		{
			t = it.headt.SemantMe();
            type_list = new TYPE_LIST(t, type_list);
            SYMBOL_TABLE.getInstance().enter(it.headid, t);
		}

		/*******************/
		/* [3] Semant Body */
		/*******************/
		body.SemantMe();

		/*****************/
		/* [4] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
		/***************************************************/
        TYPE thisType = new TYPE_FUNCTION(returnType, id, type_list);
		SYMBOL_TABLE.getInstance().enter(id, thisType);

		return new TYPE_CLASS_VAR_DEC(thisType, id);		
	}
}