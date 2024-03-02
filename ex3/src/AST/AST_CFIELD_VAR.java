package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_CFIELD_VAR
 */
public class AST_CFIELD_VAR extends AST_CFIELD
{

    public AST_DEC_VAR var;

    public AST_CFIELD_VAR(AST_DEC_VAR var)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== cField -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
	}


    public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST CFIELD VAR */
		/*************************************/
		System.out.print("AST CFIELD_VAR\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (var != null) var.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CFIELD(VAR)"));


        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
	}
    
	public TYPE SemantMe() throws Exception
	{
        TYPE previouslyDefined = SYMBOL_TABLE.getInstance().findNotGlobal(var.id);
        if (previouslyDefined != null)
            throw new Exception(String.format("ERROR(%d)\n", line));

		TYPE t = var.SemantMe();
        if (var instanceof AST_DEC_VAR_EXP)
        {
            AST_EXP exp = ((AST_DEC_VAR_EXP)var).exp;
            
            if (exp == null
                || exp instanceof AST_EXP_INT
                || exp instanceof AST_EXP_STRING
                || exp instanceof AST_EXP_NIL)
            {
                return new TYPE_CLASS_VAR_DEC(t, var.id);
            }
        }

        throw new Exception(String.format("ERROR(%d)\n", line));
	}
}