package sample.reladomo;

import com.gs.fw.common.mithra.MithraSequence;
import com.gs.fw.common.mithra.MithraSequenceObjectFactory;
import com.gs.fw.common.mithra.finder.Operation;

public class SequenceTableFactory implements MithraSequenceObjectFactory {
    
    @Override
    public MithraSequence getMithraSequenceObject(String sequenceName, Object sourceAttribute, int initialValue) {
        Operation operation = SequenceTableFinder.name().eq(sequenceName);
        SequenceTable sequence = SequenceTableFinder.findOne(operation);
        
        if (sequence == null) {
            sequence = new SequenceTable();
            sequence.setSequenceName(sequenceName);
            sequence.setNextId(initialValue);
            sequence.insert();
        }

        return sequence;
    }
}
