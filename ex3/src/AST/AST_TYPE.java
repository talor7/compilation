package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_TYPE
 */
public class AST_TYPE extends AST_Node
{
    public int type;
	public String id;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_TYPE(int type, String id)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (type == 0) System.out.format("====================== type -> TYPE_INT\n");
		if (type == 1) System.out.format("====================== type -> TYPE_STRING\n");
		if (type == 2) System.out.format("====================== type -> TYPE_VOID\n");
		if (type == 3) System.out.format("====================== type -> ID(%s)\n", id);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
        this.type = type;
		this.id = id;
	}

	/************************************************/
	/* The printing message for an STRING EXP AST node */
	/************************************************/
	public void PrintMe()
	{
        String typeString = "";
		/*******************************/
		/* AST NODE TYPE = AST STRING EXP */
		/*******************************/

		if (type == 0) typeString = "TYPE_INT";
		if (type == 1) typeString = "TYPE_STRING";
		if (type == 2) typeString = "TYPE_VOID";
		if (type == 3) typeString = String.format("ID(%s)",id);

		System.out.format("AST NODE TYPE ( %s )\n", typeString);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("TYPE(%s)",typeString));
	}

	public TYPE SemantMe() throws Exception
	{
		if (type == 0) return TYPE_INT.getInstance();
		if (type == 1) return TYPE_STRING.getInstance();
		if (type == 2) return TYPE_VOID.getInstance();
		if (type == 3)
        {
            TYPE symbolType = SYMBOL_TABLE.getInstance().find(id);
            if (symbolType != null && (symbolType.isClass() || symbolType.isArray()))
                return symbolType;
        }
        
        throw new Exception(String.format("ERROR(%d)\n", line));
	}
}