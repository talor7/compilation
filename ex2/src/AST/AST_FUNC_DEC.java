package AST;

/**
 * AST_FUNC_DEC
 */
public class AST_FUNC_DEC extends AST_Node
{

	public AST_TYPE type;
	public String id;
	public AST_TYPEID_LIST args;
	public AST_STMT_LIST body;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_FUNC_DEC(AST_TYPE type, String id, AST_TYPEID_LIST args, AST_STMT_LIST body)	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
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
}