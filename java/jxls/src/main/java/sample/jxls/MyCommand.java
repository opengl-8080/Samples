package sample.jxls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.Size;
import org.jxls.transform.poi.PoiTransformer;

public class MyCommand extends AbstractCommand {
    
    private Area area;
    private String value;
    
    @Override
    public String getName() {
        return "myCommand";
    }

    @Override
    public Size applyAt(CellRef cellRef, Context context) {
        PoiTransformer transformer = (PoiTransformer)area.getTransformer();
        Workbook wb = transformer.getWorkbook();
        Sheet sheet = wb.getSheet(cellRef.getSheetName());
        int rownum = cellRef.getRow();
        int colnum = cellRef.getCol();
        
        Row row = sheet.getRow(rownum);
        Cell cell = row.getCell(colnum);
        
        cell.setCellValue("<<" + this.value + ">>");
        
        return this.area.getSize();
    }
    
    @Override
    public Command addArea(Area area) {
        super.addArea(area);
        this.area = area;
        return this;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
