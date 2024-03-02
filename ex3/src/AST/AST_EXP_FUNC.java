package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

/**
 * AST_EXP_FUNC
 */
public class AST_EXP_FUNC extends AST_EXP
{
	public AST_VAR v;
	public String id;
	public AST_EXP_LIST expl;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_FUNC(AST_VAR v, String id, AST_EXP_LIST expl)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (v != null && expl != null) System.out.print("====================== exp -> var.ID()\n");
		if (v != null && expl == null) System.out.print("====================== exp -> var.ID(exp [, exp]*)\n");
		if (v == null && expl != null) System.out.print("====================== exp -> ID()\n");
		if (v == null && expl == null) System.out.print("====================== exp -> ID(exp [, exp]*)\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
		this.id = id;
		this.expl = expl;
	}

	/******************************************************/
	/* The printing message for a AST node                */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE                      */
		/**************************************/
		System.out.print("AST NODE EXP FUNC\n");

		/*************************************/
		/* RECURSIVELY PRINT             ... */
		/*************************************/
		if (v != null) v.PrintMe();
		if (expl != null) expl.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("EXP FUNC(%s)", id));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, v.SerialNumber);
		if (expl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, expl.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE functionType = null;
        if (v == null)
        {
            functionType = SYMBOL_TABLE.getInstance().find(id);
            if (functionType == null || !functionType.isFunction())
                throw new Exception(String.format("ERROR(%d)\n", line));
        }
        else
        {
            TYPE varType = v.SemantMe();
            if (!varType.isClass())
                throw new Exception(String.format("ERROR(%d)\n", line));
            TYPE_CLASS classType = (TYPE_CLASS) varType;
            while (classType != null && functionType == null)
            {
                for (TYPE_CLASS_VAR_DEC_LIST data_member_list = classType.data_members; data_member_list != null; data_member_list = data_member_list.tail)
                {
                    if (data_member_list.head.name.equals(id))
                    {
                        functionType = data_member_list.head.t;
                        break;
                    }
                }
                classType = classType.father;
            }
            if (functionType == null || !functionType.isFunction())
                throw new Exception(String.format("ERROR(%d)\n", line));
        }
	
        TYPE_LIST paramsTypes = null;
        if (expl != null)
            paramsTypes = expl.SemantMe();
        TYPE_LIST funcParamsTypes = ((TYPE_FUNCTION)functionType).params;
        while (paramsTypes != null && funcParamsTypes != null)
        {
            if (paramsTypes.head == null) // nil
            {
                if (!funcParamsTypes.head.isClass() && !funcParamsTypes.head.isArray())
                    throw new Exception(String.format("ERROR(%d)\n", line));
            }
            else if (funcParamsTypes.head.isClass())
            {
                if (!paramsTypes.head.isClass())
                    throw new Exception(String.format("ERROR(%d)\n", line));
                if (!((TYPE_CLASS)paramsTypes.head).isSubClassOf((TYPE_CLASS)funcParamsTypes.head))
                    throw new Exception(String.format("ERROR(%d)\n", line));
            }
            else if (paramsTypes.head != funcParamsTypes.head)
                throw new Exception(String.format("ERROR(%d)\n", line));
            paramsTypes = paramsTypes.tail;
            funcParamsTypes = funcParamsTypes.tail;
        }

        if (((TYPE_FUNCTION)functionType).returnType == TYPE_VOID.getInstance())
            throw new Exception(String.format("ERROR(%d)\n", line));

        return ((TYPE_FUNCTION)functionType).returnType;
    }
}