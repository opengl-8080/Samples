package sample.thymeleaf.web;

import java.util.ArrayList;
import java.util.List;

public class MyForm {
    private List<Row> rows = new ArrayList<>();
    
    public void appendRow() {
        this.rows.add(new Row());
    }
    
    public void removeRow(int index) {
        this.rows.remove(index);
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public static class Row {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
