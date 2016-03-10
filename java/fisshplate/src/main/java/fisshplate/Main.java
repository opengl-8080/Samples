package fisshplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.seasar.fisshplate.template.FPTemplate;

public class Main {

    public static void main(String[] args) throws Exception {
        FPTemplate t = new FPTemplate();
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        List<Item> list = new ArrayList<Item>();
        
        for (int i=0; i<5; i++) {
            for (int j=0; j<15; j++) {
                Item item = new Item();
                item.header1 = "header1-" + i;
                item.header2 = "header2-" + i;
                
                item.date = new Date();
                item.code = "code-" + j;
                item.name = "name-" + j;
                item.type = "type-" + j;
                item.price = new BigDecimal(j + 1000);
                item.summary = "sum-" + j;
                list.add(item);
                
                map.put("records", list);
            }
        }
        
        File template = new File("excel/template.xls");
        FileInputStream fin = null;
        HSSFWorkbook wb = null;
        try {
            fin = new FileInputStream(template);
            
            wb = t.process(fin, map);
            
            File out = new File("excel/out.xls");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(out);
                wb.write(fos);
            } finally {
                fos.close();
            }
        } finally {
            if (fin != null) {
                fin.close();
            }
        }
        System.out.println("end");
    }
    
    public static class Item {
        private String header1;
        private String header2;
        private Date date;
        private String code;
        private String name;
        private String type;
        private BigDecimal price;
        private String summary;
        
        public String getHeader1() {
            return header1;
        }
        public void setHeader1(String header1) {
            this.header1 = header1;
        }
        public String getHeader2() {
            return header2;
        }
        public void setHeader2(String header2) {
            this.header2 = header2;
        }
        public Date getDate() {
            return date;
        }
        public void setDate(Date date) {
            this.date = date;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public BigDecimal getPrice() {
            return price;
        }
        public void setPrice(BigDecimal price) {
            this.price = price;
        }
        public String getSummary() {
            return summary;
        }
        public void setSummary(String summary) {
            this.summary = summary;
        }
        @Override
        public String toString() {
            return "Item [date=" + date + ", code=" + code + ", name=" + name + ", type=" + type + ", price=" + price + ", summary=" + summary + "]";
        }
        
    }
}
