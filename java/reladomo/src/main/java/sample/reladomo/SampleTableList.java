package sample.reladomo;
import com.gs.fw.finder.Operation;
import java.util.*;

import static java.util.stream.Collectors.*;

public class SampleTableList extends SampleTableListAbstract
{
	public SampleTableList()
	{
		super();
	}

	public SampleTableList(int initialSize)
	{
		super(initialSize);
	}

	public SampleTableList(Collection c)
	{
		super(c);
	}

	public SampleTableList(Operation operation)
	{
		super(operation);
	}
	
	public String toString() {
		return "SampleTableList(\n  "
				+ this.stream().map(SampleTable::toString).collect(joining(",\n  "))
				+ "\n)";
	}
}
