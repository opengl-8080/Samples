package sample.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import sample.hazelcast.shell.Shell;

public class Main {
    public static void main(String[] args) throws Exception {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        Shell shell = new Shell(instance);
        shell.setCommandListener(command -> {
            System.out.println(command);
        });
        shell.start();
    }
}
