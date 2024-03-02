package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_DEC_VAR_NEW
 */
public class AST_DEC_VAR_NEW extends AST_DEC_VAR
{

    public AST_TYPE type;
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
        // check name
        // check not defined in current scope
        TYPE previouslyDefined = SYMBOL_TABLE.getInstance().findInCurrentScope(id);
        if (previouslyDefined != null)
            throw new Exception(String.format("ERROR(%d)\n", line));

        TYPE varType = type.SemantMe();

        if (varType == TYPE_VOID.getInstance()
            || (!varType.isClass() && !varType.isArray()))
            throw new Exception(String.format("ERROR(%d)\n", line));

        TYPE expType = nexp.SemantMe();
        if (expType.isClass() && varType.isClass())
        {
            if (nexp.exp != null)
                throw new Exception(String.format("ERROR(%d)\n", line));
            if (!((TYPE_CLASS)expType).isSubClassOf(((TYPE_CLASS)varType)))
                throw new Exception(String.format("ERROR(%d)\n", line));
        }
        else if (varType.isArray())
        {
            if (nexp.exp == null)
                throw new Exception(String.format("ERROR(%d)\n", line));
            if (((TYPE_ARRAY)varType).type.isClass() && expType.isClass())
            {
                if (!((TYPE_CLASS)expType).isSubClassOf((TYPE_CLASS)((TYPE_ARRAY)varType).type))
                    throw new Exception(String.format("ERROR(%d)\n", line));
            }
            else if (((TYPE_ARRAY)varType).type != expType)
                throw new Exception(String.format("ERROR(%d)\n", line));
        }
        else if (expType != varType)
            throw new Exception(String.format("ERROR(%d)\n", line));

		SYMBOL_TABLE.getInstance().enter(id, new TYPE_VAR(varType, id));
        return new TYPE_VAR(varType, id);
	}

}