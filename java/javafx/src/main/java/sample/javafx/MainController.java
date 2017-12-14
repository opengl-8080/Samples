package sample.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button button;
    
    @FXML
    public void click() {
        new Thread(() -> {
            System.out.println("thread = " + Thread.currentThread().getName());
            
            Platform.runLater(() -> {
                System.out.println("runLater thread = " + Thread.currentThread().getName());
                button.setText("hoge");
            });
        }).start();
    }
//    
//    @FXML
//    public void startTask() throws ExecutionException, InterruptedException {
//        System.out.println("Main Thread = " + Thread.currentThread().getName());
//        
//        Task<String> task = new Task<String>() {
//
//            @Override
//            protected String call() throws Exception {
//                this.updateMessage("foo");
//                System.out.println(Thread.currentThread().getName());
//                return "hoge";
//            }
//        };
//
//        task.setOnSucceeded(e -> {
//            System.out.println("succeeded thread = " + Thread.currentThread().getName());
//        });
//
//        Thread thread = new Thread(task);
//        System.out.println("begin thread");
//        thread.start();
//
//        String result = task.get();
//        System.out.println("end thread. result=" + result);
//    }
//    
//    @FXML
//    public void startService() {
//        Service<String> service = new Service<String>() {
//            @Override
//            protected Task<String> createTask() {
//                return new Task<String>() {
//
//                    @Override
//                    protected String call() throws Exception {
//                        System.out.println(Thread.currentThread().getName());
//                        return "fuga";
//                    }
//                };
//            }
//        };
//
//        System.out.println("start service");
//        service.start();
//        service.setOnSucceeded(e -> {
//            System.out.println("value=" + service.getValue());
//            System.out.println("thread = " + Thread.currentThread().getName());
//        });
//    }
}
