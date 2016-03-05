package sample.javaee.jbatch;

import java.io.Serializable;

import javax.batch.api.chunk.ItemReader;

public class MyItemReader implements ItemReader {

    @Override
    public void open(Serializable checkPoint) throws Exception {
        System.out.println("[Reader] open");
    }

    private int counter;

    @Override
    public Object readItem() throws Exception {
        String item;

        if (counter < 22) {
            item = String.format("item-%03d", ++counter);
        } else {
            item = null;
        }

        System.out.println("[Reader] readItem. item = " + item);

        return item;
    }

    @Override
    public void close() throws Exception {
        System.out.println("[Reader] close");
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        System.out.println("[Reader] checkpointInfo");
        return null;
    }
}