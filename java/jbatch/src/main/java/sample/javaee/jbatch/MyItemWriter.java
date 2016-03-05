package sample.javaee.jbatch;

import java.io.Serializable;
import java.util.List;

import javax.batch.api.chunk.ItemWriter;

public class MyItemWriter implements ItemWriter {

    @Override
    public void open(Serializable checkPoint) throws Exception {
        System.out.println("[Writer] open");
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {
        System.out.println("[Writer] writeItems. items = " + items);
    }

    @Override
    public void close() throws Exception {
        System.out.println("[Writer] close");
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        System.out.println("[Writer] checkpointInfo");
        return null;
    }
}