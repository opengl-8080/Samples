package sample.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.PartitionService;

public class Main {
    public static void main(String[] args) throws Exception {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();

        IList<Object> foo = instance.getList("foo");
        IList<Object> bar = instance.getList("bar");
        IList<Object> fizz = instance.getList("fizz@" + foo.getPartitionKey());

        System.out.println("foo.key = " + foo.getPartitionKey());
        System.out.println("bar.key = " + bar.getPartitionKey());
        System.out.println("fizz.key = " + fizz.getPartitionKey());
        
        PartitionService partitionService = instance.getPartitionService();
        System.out.println("foo.partition=" + partitionService.getPartition(foo.getPartitionKey()));
        System.out.println("bar.partition=" + partitionService.getPartition(bar.getPartitionKey()));
        System.out.println("fizz.partition=" + partitionService.getPartition(fizz.getPartitionKey()));
    }
}
