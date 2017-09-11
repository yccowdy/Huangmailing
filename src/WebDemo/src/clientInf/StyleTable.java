package clientInf;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class StyleTable extends JTable {
	private String[] color = null; // 用于设定行颜色的数组

	public StyleTable() {
		super();
	}

	public void fitTableColumns(JTable table) {
		// strategy - get max width for cells in column and
		// make that the preferred width
		TableColumnModel columnModel = table.getColumnModel();
		for (int col = 0; col < table.getColumnCount(); col++) {

			int maxwidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer rend = table.getCellRenderer(row, col);
				Object value = table.getValueAt(row, col);
				Component comp = rend.getTableCellRendererComponent(table,
						value, false, false, row, col);
				maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
			} // for row
				// TableColumn column = columnModel.getColumn (col);
				// column.setPreferredWidth (maxwidth);
			TableColumn column = columnModel.getColumn(col);
			TableCellRenderer headerRenderer = column.getHeaderRenderer();
			if (headerRenderer == null)

				headerRenderer = table.getTableHeader().getDefaultRenderer();
			Object headerValue = column.getHeaderValue();
			Component headerComp = headerRenderer
					.getTableCellRendererComponent(table, headerValue, false,
							false, 0, col);
			maxwidth = Math.max(maxwidth, headerComp.getPreferredSize().width);
			column.setPreferredWidth(maxwidth);
		} // for col
	}
}
