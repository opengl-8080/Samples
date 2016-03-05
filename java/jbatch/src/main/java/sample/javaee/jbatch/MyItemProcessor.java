package sample.javaee.jbatch;

import javax.batch.api.chunk.ItemProcessor;

public class MyItemProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        System.out.println("[Processor] item = " + item);
        return ((String)item).toUpperCase();
    }
}