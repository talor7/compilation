package AST;

/**
 * AST_DEC_VAR
 */
public class AST_DEC_VAR extends AST_DEC
{

    public AST_VAR_DEC var;

    public AST_DEC_VAR(AST_VAR_DEC var)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
	}


    public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST DEC VAR       */
		/*************************************/
		System.out.print("AST DEC_VAR\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (var != null) var.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("DEC(VAR)"));


        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
	}
}