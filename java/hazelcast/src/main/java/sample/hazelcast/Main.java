package sample.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import sample.hazelcast.shell.Shell;

import java.util.ArrayList;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws Exception {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        IList<String> list = instance.getList("list");
        
        Shell shell = new Shell(instance, command -> {
            if (command.startsWith("add")) {
                String value = command.substring(4).trim();
                list.add(value);
                System.out.println("list=" + new ArrayList<>(list));
            } else if (command.equals("show")) {
                System.out.println("list=" + new ArrayList<>(list));
            } else if (command.equals("destroy")) {
                list.destroy();
                System.out.println("list=" + new ArrayList<>(list));
            }
            
            return Optional.of("show");
        }, message -> {
            System.out.println("list=" + new ArrayList<>(list));
        });
        
        shell.start();
    }
}
