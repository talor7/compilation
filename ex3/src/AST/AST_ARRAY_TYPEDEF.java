package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_ARRAY_TYPEDEF
 */
public class AST_ARRAY_TYPEDEF extends AST_DEC
{

    public String id;
    public AST_TYPE type;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ARRAY_TYPEDEF(String id, AST_TYPE type)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> arrayTypedef\n");
		System.out.format("====================== arrayTypedef -> Array ID( %s ) EQ type[]\n", id);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.id = id;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST ARRAY TYPEDEF */
		/*************************************/
		System.out.print("AST NODE ARRAY TYPEDEF\n");

		/**********************************************/
		/* RECURSIVELY PRINT type, then id        ... */
		/**********************************************/
        
		if (type != null) type.PrintMe();
        System.out.format("ARRAY name %s", id);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ArrayTypeDef (%s)", id));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
    }
	
	public TYPE SemantMe() throws Exception
	{
        TYPE arrayType = type.SemantMe();
        if (arrayType == TYPE_VOID.getInstance())
        {
            throw new Exception(String.format("ERROR(%d)\n", line));
        }

		SYMBOL_TABLE.getInstance().enter(id, new TYPE_ARRAY(arrayType, id));

		return null;
	}
}