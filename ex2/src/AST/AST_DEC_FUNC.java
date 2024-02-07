package AST;

/**
 * AST_DEC_FUNC
 */
public class AST_DEC_FUNC extends AST_DEC
{

    public AST_FUNC_DEC func;

    public AST_DEC_FUNC(AST_FUNC_DEC func)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> funcDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.func = func;
	}


    public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST DEC FUNC      */
		/*************************************/
		System.out.print("AST DEC_FUNC\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (func != null) func.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("DEC(FUNC)"));


        if (func != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, func.SerialNumber);
	}
}