package AST;

/**
 * AST_DEC_CLASS
 */
public class AST_DEC_CLASS extends AST_DEC
{

    public AST_CLASS_DEC classDec;

    public AST_DEC_CLASS(AST_CLASS_DEC classDec)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== dec -> classDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.classDec = classDec;
	}


    public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST DEC CLASS     */
		/*************************************/
		System.out.print("AST DEC_CLASS\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (classDec != null) classDec.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("DEC(CLASS)"));


        if (classDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, classDec.SerialNumber);
	}
}