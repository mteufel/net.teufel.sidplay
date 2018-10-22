package net.teufel.sidplay.domain;

import java.util.ArrayList;
import java.util.List;

public class TableData<T> {

    private List<Column> tableColumns;
    private List<T>  tableData;

    public TableData() {
        this.tableColumns = new ArrayList<>();
    }

    public List<Column> getTableColumns() {
        return tableColumns;
    }

    public List<T> getTableData() {
        return tableData;
    }

    public void setTableData(List<T> tableData) {
        this.tableData = tableData;
    }

    public void addColumn(String key, String title) {
        Column column = new Column();
        column.setKey(key);
        column.setTitle(title);
        this.tableColumns.add(column);
    }


}
