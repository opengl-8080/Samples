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
            context.putVar("list", Arrays.asList(1, 2, 3));
            
            JxlsHelper.getInstance().processTemplate(in, out, context);
        }
        
        System.out.println("end");
    }
}
