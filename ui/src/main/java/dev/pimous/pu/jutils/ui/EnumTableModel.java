package dev.pimous.pu.jutils.ui;

import dev.pimous.pu.jutils.i18n.Localizable;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.util.*;

/**
 * @author APG-Gillardeau
 * @since 1.1.0
 */
public class EnumTableModel<E extends Enum<E> & ColumnModel>
	extends AbstractTableModel
{

	@Serial
	private static final long serialVersionUID = -859379548615581298L;

	private final Class<E> enumType;
	private transient List<?> data = Collections.emptyList();

	public EnumTableModel(final Class<E> enumClass){
		this.enumType = enumClass;
	}

	// GETTERS
	@Override
	public int getColumnCount(){
		return enumType.getEnumConstants().length;
	}
	@Override
	public int getRowCount(){ return data.size(); }

	@Override
	public String getColumnName(int column){
		ColumnModel m = enumType.getEnumConstants()[column];
		return m instanceof Localizable l
			? l.getSentence().sentence() : m.getName();
	}
	@Override
	public Class<?> getColumnClass(int columnIndex){
		return enumType.getEnumConstants()[columnIndex].getType();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		return Objects.requireNonNull(
			enumType.getEnumConstants()[columnIndex].getParser()
		).apply(
			data.get(rowIndex)
		);
	}

	// SETTERS
	public void setData(final List<?> data){
		this.data = data;
		fireTableDataChanged();
	}
}
