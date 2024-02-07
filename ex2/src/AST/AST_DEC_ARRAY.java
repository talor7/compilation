package AST;

/**
 * AST_DEC_ARRAY
 */
public class AST_DEC_ARRAY extends AST_DEC
{

    public AST_ARRAY_TYPEDEF array;

    public AST_DEC_ARRAY(AST_ARRAY_TYPEDEF array)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> arrayTypedef\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.array = array;
	}


    public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST DEC ARRAY     */
		/*************************************/
		System.out.print("AST DEC_ARRAY\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (array != null) array.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("DEC(ARRAY)"));


        if (array != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, array.SerialNumber);
	}
}