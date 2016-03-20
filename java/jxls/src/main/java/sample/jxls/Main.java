package sample.jxls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

public class Main {

    public static void main(String[] args) throws Exception {
        try (InputStream in = new FileInputStream(new File("./excel/template.xlsx"));
             OutputStream out = new FileOutputStream(new File("./excel/out.xlsx"))) {
            
            Context context = new Context();
            context.putVar("gridHeaders", Arrays.asList("ほげ", "ふが", "ぴよ"));
            context.putVar("gridData", Arrays.asList(
                new Main("hoge1", "fuga1", "piyo1"),
                new Main("hoge2", "fuga2", "piyo2"),
                new Main("hoge3", "fuga3", "piyo3")
            ));
            
            JxlsHelper.getInstance().processTemplate(in, out, context);
        }
        
        System.out.println("end");
    }
    
    private String hoge;
    private String fuga;
    private String piyo;
    
    public Main(String hoge, String fuga, String piyo) {
        this.hoge = hoge;
        this.fuga = fuga;
        this.piyo = piyo;
    }

    public String getHoge() {
        return hoge;
    }

    public String getFuga() {
        return fuga;
    }

    public String getPiyo() {
        return piyo;
    }
}
