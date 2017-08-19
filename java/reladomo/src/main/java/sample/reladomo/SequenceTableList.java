package sample.reladomo;
import com.gs.fw.finder.Operation;
import java.util.*;
public class SequenceTableList extends SequenceTableListAbstract
{
	public SequenceTableList()
	{
		super();
	}

	public SequenceTableList(int initialSize)
	{
		super(initialSize);
	}

	public SequenceTableList(Collection c)
	{
		super(c);
	}

	public SequenceTableList(Operation operation)
	{
		super(operation);
	}
}
