package sample.reladomo;

import com.gs.fw.common.mithra.MithraSequence;

public class SequenceTable extends SequenceTableAbstract implements MithraSequence {
	public SequenceTable() {
		super();
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}

	@Override
	public void setSequenceName(String sequenceName) {
		this.setName(sequenceName);
	}

	@Override
	public long getNextId() {
		return this.getNextValue();
	}

	@Override
	public void setNextId(long nextValue) {
		this.setNextValue(nextValue);
	}
}
