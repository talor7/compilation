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
    public int idLine;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_CLASS(String id, String superid, AST_CFIELD_LIST cfieldl, int idLine)	{
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
        this.idLine = idLine;
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
        // check name
        // check not defined
        TYPE previouslyDefined = SYMBOL_TABLE.getInstance().find(id);
        if (previouslyDefined != null)
            throw new Exception(String.format("ERROR(%d)\n", idLine));

        TYPE father = null;
        if (superid != null)
        {
            father = SYMBOL_TABLE.getInstance().find(superid);
            if (father == null || !(father instanceof TYPE_CLASS))
                throw new Exception(String.format("ERROR(%d)\n", idLine));
        }

		TYPE_CLASS t = new TYPE_CLASS((TYPE_CLASS)father, id, null);

		SYMBOL_TABLE.getInstance().enter(id, t);

        int scopeCounter = StartFatherScope((TYPE_CLASS)father) + 1;

		SYMBOL_TABLE.getInstance().beginScope();

        cfieldl.SemantMe(t);

        for (int i = 0; i < scopeCounter; i++)
		    SYMBOL_TABLE.getInstance().endScope();
        
		return null;
	}

    private int StartFatherScope(TYPE_CLASS father)
    {
        if (father == null)
            return 0;
        
        TYPE_CLASS nextFather = father.father;
        int counter = StartFatherScope(nextFather);
        
        SYMBOL_TABLE.getInstance().beginScope();
        for (TYPE_CLASS_VAR_DEC_LIST it = ((TYPE_CLASS)father).data_members; it  != null; it = it.tail)
        {
            SYMBOL_TABLE.getInstance().enter(it.head.name, it.head.t);
        }
        
        return counter + 1;
    }
}