package TYPES;

public class TYPE_ARRAY extends TYPE
{
	public TYPE type;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_ARRAY(TYPE type, String name)
	{
		this.name = name;
		this.type = type;
	}

	public boolean isClass() { return true; }
	public boolean isArray() { return true; }
}
