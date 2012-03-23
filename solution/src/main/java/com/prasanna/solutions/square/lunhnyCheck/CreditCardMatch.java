package com.prasanna.solutions.square.lunhnyCheck;


public class CreditCardMatch
{
	int start = 0;

	int end = 0;

	String mask = "";

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

	public String getMask()
	{
		return mask;
	}

	public void setMask(String replacement)
	{
		this.mask = replacement;
	}

	@Override
    public int hashCode()
    {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + end;
	    result = prime * result + ((mask == null) ? 0 : mask.hashCode());
	    result = prime * result + start;
	    return result;
    }

	@Override
    public boolean equals(Object obj)
    {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    CreditCardMatch other = (CreditCardMatch) obj;
	    if (end != other.end)
		    return false;
	    if (mask == null)
	    {
		    if (other.mask != null)
			    return false;
	    }
	    else if (!mask.equals(other.mask))
		    return false;
	    if (start != other.start)
		    return false;
	    return true;
    }
}
