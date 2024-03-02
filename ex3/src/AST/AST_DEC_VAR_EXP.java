package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_DEC_VAR_EXP
 */
public class AST_DEC_VAR_EXP extends AST_DEC_VAR
{

    public AST_TYPE type;
    public AST_EXP exp;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_VAR_EXP(AST_TYPE type ,String id, AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> varDec\n");
		if (exp != null) System.out.format("====================== varDec -> type ID( %s ) ASSIGN exp\n", id);
        if (exp == null) System.out.format("====================== varDec -> type ID( %s )\n", id);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.id = id;
        this.exp = exp;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE VAR DEC\n");

		/**********************************************/
		/* RECURSIVELY PRINT type, then id ... */
		/**********************************************/
        
		if (type != null) type.PrintMe();
        if (exp != null) exp.PrintMe();

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
        if (exp  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
    }

	public TYPE SemantMe() throws Exception
	{
        // check name
        // check not defined in current scope
        TYPE previouslyDefined = SYMBOL_TABLE.getInstance().findInCurrentScope(id);
        if (previouslyDefined != null)
            throw new Exception(String.format("ERROR(%d)\n", line));

        TYPE varType = type.SemantMe();

        if (varType == TYPE_VOID.getInstance())
            throw new Exception(String.format("ERROR(%d)\n", line));

        if (exp != null && exp instanceof AST_EXP_NIL && !varType.isClass() && !varType.isArray()) // assigning nil to primitive
            throw new Exception(String.format("ERROR(%d)\n", line));
        
        if (exp != null && !(exp instanceof AST_EXP_NIL && (varType.isClass() || varType.isArray())))
        {
            TYPE expType = exp.SemantMe();
            if (expType.isClass() && varType.isClass())
            {
                if (!((TYPE_CLASS)expType).isSubClassOf(((TYPE_CLASS)varType)))
                    throw new Exception(String.format("ERROR(%d)\n", line));
            }
            else if (expType != varType)
                throw new Exception(String.format("ERROR(%d)\n", line));
        }

		SYMBOL_TABLE.getInstance().enter(id, new TYPE_VAR(varType, id));
        return new TYPE_VAR(varType, id);
	}

}