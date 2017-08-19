package sample.reladomo;

public class SampleTable extends SampleTableAbstract
{
	public SampleTable()
	{
		super();
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}
	
	@Override
	public String toString() {
		return "SampleTable(id=" + this.getId() + ", name=" + this.getName() + ")";
	}
}
