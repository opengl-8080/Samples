package sample.jxls;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        try (InputStream in = new FileInputStream(new File("./excel/template.xlsx"));
             OutputStream out = new FileOutputStream(new File("./excel/out.xlsx"))) {
            
            Context context = new Context();

            JxlsHelper.getInstance().processTemplate(in, out, context);
        }
    }
}
