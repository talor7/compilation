package TYPES;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_CLASS_VAR_DEC_LIST data_members;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father, String name, TYPE_CLASS_VAR_DEC_LIST data_members)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
	}

	public boolean isClass(){ return true;}

    public boolean isSubClassOf(TYPE_CLASS other)
    {
		TYPE_CLASS current = this;
        while (current != null && current != other)
        {
            current = ((TYPE_CLASS)current).father;
        }
        if (other == current)
            return true;
        return false;
    }
}
