package AST;

/**
 * AST_CLASS_DEC
 */
public class AST_CLASS_DEC extends AST_Node
{
	public String id;
    public String superid;
    public AST_CFIELD_LIST cfieldl;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CLASS_DEC(String id, String superid, AST_CFIELD_LIST cfieldl)	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
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
}