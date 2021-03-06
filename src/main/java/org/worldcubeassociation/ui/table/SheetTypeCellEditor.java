package org.worldcubeassociation.ui.table;

import org.worldcubeassociation.WorkbookAssistantEnv;
import org.worldcubeassociation.workbook.MatchedSheet;
import org.worldcubeassociation.workbook.SheetType;
import org.worldcubeassociation.workbook.WorkbookValidator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lars Vandenbergh
 */
public class SheetTypeCellEditor extends DefaultCellEditor {

    private MatchedSheet fMatchedSheet;
    private WorkbookAssistantEnv fEnv;

    public SheetTypeCellEditor(WorkbookAssistantEnv aEnv) {
        super(createComboBox());
        fEnv = aEnv;
        setClickCountToStart(2);
    }

    private static JComboBox createComboBox() {
        List<SheetType> sheetTypes = new ArrayList<SheetType>(Arrays.asList(SheetType.values()));
        return new JComboBox(sheetTypes.toArray());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {
        fMatchedSheet = (MatchedSheet) value;
        getComponent().setFont(getComponent().getFont().deriveFont(fEnv.getFontSize()));
        return super.getTableCellEditorComponent(table, fMatchedSheet.getSheetType(), isSelected, row, column);
    }


    @Override
    public boolean stopCellEditing() {
        SheetType newSheetType = (SheetType) getCellEditorValue();
        if (newSheetType != fMatchedSheet.getSheetType()) {
            fMatchedSheet.setSheetType(newSheetType);
            fMatchedSheet.setFirstDataRow(4);
            fMatchedSheet.setLastDataRow(fMatchedSheet.getTableData().length - 1);
            WorkbookValidator.validate(fEnv.getMatchedWorkbook(), fEnv.getDatabase(), fEnv.getScrambles());
            fEnv.fireSheetChanged(fMatchedSheet);
        }
        return super.stopCellEditing();
    }

    @Override
    public void cancelCellEditing() {
        super.cancelCellEditing();
    }

}
