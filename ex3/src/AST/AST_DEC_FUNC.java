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
		TYPE t;
		TYPE returnType = null;

		/*******************/
		/* [0] return type */
		/*******************/
		returnType = type.SemantMe();

        // check name
        // check not defined in current scope
        TYPE previouslyDefined = SYMBOL_TABLE.getInstance().findInCurrentScope(id);
        if (previouslyDefined != null)
            throw new Exception(String.format("ERROR(%d)\n", type.line));
        
        previouslyDefined = SYMBOL_TABLE.getInstance().findNotGlobal(id);

        TYPE_FUNCTION thisType = new TYPE_FUNCTION(returnType, id, null);
		SYMBOL_TABLE.getInstance().enter(id, thisType);

        SYMBOL_TABLE.getInstance().currentFuncReturnType = returnType;
	
		/****************************/
		/* [1] Begin Function Scope */
		/****************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Input Params */
		/***************************/
		TYPE_LIST tail = null;
		for (AST_TYPEID_LIST it = args; it  != null; it = it.tail)
		{
			t = it.headt.SemantMe();

            TYPE previouslyDefinedVar = SYMBOL_TABLE.getInstance().findInCurrentScope(it.headid);
            if (previouslyDefinedVar != null)
                throw new Exception(String.format("ERROR(%d)\n", it.line));
    
            if (t == TYPE_VOID.getInstance())
                throw new Exception(String.format("ERROR(%d)\n", it.line));
            if (tail == null)
            {
                thisType.params = new TYPE_LIST(t, null);
                tail = thisType.params;
            }
            else
            {
                tail.tail = new TYPE_LIST(t, null);
                tail = tail.tail;
            }
            SYMBOL_TABLE.getInstance().enter(it.headid, new TYPE_VAR(t, it.headid));
		}
        
        // check if legal in terms of overriding
        if (previouslyDefined != null)
        {
			//test for shadowing a function
            if (!(previouslyDefined instanceof TYPE_FUNCTION))
                throw new Exception(String.format("ERROR(%d)\n", type.line));
            TYPE_FUNCTION prevFunc = (TYPE_FUNCTION)previouslyDefined;
            //check for matching return types
            if (prevFunc.returnType != returnType)
                throw new Exception(String.format("ERROR(%d)\n", type.line));

			//check for matching parmeter types
            TYPE_LIST c_type_list = thisType.params;
            TYPE_LIST prev_type_list = prevFunc.params;
            while (c_type_list != null && prev_type_list != null)
            {
                if (c_type_list.head != prev_type_list.head)
                    throw new Exception(String.format("ERROR(%d)\n", type.line));
                c_type_list = c_type_list.tail;
                prev_type_list = prev_type_list.tail;
            }
            
            if (c_type_list != null || prev_type_list != null)
                throw new Exception(String.format("ERROR(%d)\n", type.line));
        }

		/*******************/
		/* [3] Semant Body */
		/*******************/
		body.SemantMe();

		/*****************/
		/* [4] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

        SYMBOL_TABLE.getInstance().currentFuncReturnType = null;

		return thisType;
	}
}