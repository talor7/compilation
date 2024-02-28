package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_DEC_VAR_NEW
 */
public class AST_DEC_VAR_NEW extends AST_DEC_VAR
{

    public AST_TYPE type;
    public String id;
    public AST_NEW_EXP nexp;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_VAR_NEW(AST_TYPE type ,String id, AST_NEW_EXP nexp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> varDec\n");
		System.out.format("====================== varDec -> type ID( %s ) := newExp\n", id);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.id = id;
        this.nexp = nexp;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE VAR DEC NEW\n");

		/**********************************************/
		/* RECURSIVELY PRINT type, then id ... */
		/**********************************************/
		if (type != null) type.PrintMe();
        if (nexp != null) nexp.PrintMe();
        
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("VarDec(%s)", id));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        if (nexp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,nexp.SerialNumber);
    }

	public TYPE SemantMe() throws Exception
	{
        // TODO
        TYPE varType = type.SemantMe();
		SYMBOL_TABLE.getInstance().enter(id, varType);
        return null;
	}

}