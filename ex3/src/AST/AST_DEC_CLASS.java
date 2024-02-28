package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_DEC_CLASS
 */
public class AST_DEC_CLASS extends AST_DEC
{
	public String id;
    public String superid;
    public AST_CFIELD_LIST cfieldl;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_CLASS(String id, String superid, AST_CFIELD_LIST cfieldl)	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> classDec\n");
		if(superid != null) System.out.format("====================== classDec -> CLASS ID( %s ) EXTENDS ID( %s ) { cfield [cField]* }\n", id, superid);
		if(superid == null) System.out.format("====================== classDec -> CLASS ID( %s ) { cfield [cField]* }\n", id);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.id = id;
		this.superid = superid;
		this.cfieldl = cfieldl;
	}

	/******************************************************/
	/* The printing message for a AST node                */
	/******************************************************/
	public void PrintMe()
	{
        String extend = "";

        if (superid != null)
            extend = "\nextends " + superid;

		/**************************************/
		/* AST NODE TYPE                      */
		/**************************************/
		System.out.print("AST NODE CLASS DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (cfieldl != null) cfieldl.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("Class Dec(%s) %s", id, extend));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cfieldl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cfieldl.SerialNumber);
	}
	
	public TYPE SemantMe() throws Exception
	{	
        // TODO
        
		SYMBOL_TABLE.getInstance().beginScope();

        TYPE father = null;
        if (superid != null)
        {
            father = SYMBOL_TABLE.getInstance().find(superid);
            if (father == null || !(father instanceof TYPE_CLASS))
                throw new Exception(String.format("Error(%d)\n", line));
        }

        // add father's data members to scope and then start another scope??

		TYPE_CLASS t = new TYPE_CLASS((TYPE_CLASS)father, id, cfieldl.SemantMe());

		SYMBOL_TABLE.getInstance().endScope();

		SYMBOL_TABLE.getInstance().enter(id, t);
        
		return null;
	}
}