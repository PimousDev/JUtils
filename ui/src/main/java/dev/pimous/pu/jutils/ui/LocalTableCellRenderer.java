package dev.pimous.pu.jutils.ui;

import dev.pimous.pu.jutils.i18n.I18nBundle;
import dev.pimous.pu.jutils.i18n.Localizable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author APG-Gillardeau
 * @since 1.1.0
 */
public class LocalTableCellRenderer implements TableCellRenderer{

	private final I18nBundle bundle;
	private final TableCellRenderer delegate;

	public LocalTableCellRenderer(final I18nBundle bundle,
		final TableCellRenderer delegate
	){
		this.bundle = bundle;
		this.delegate = delegate;
	}

	// GETTERS
	@Override
	public Component getTableCellRendererComponent(final JTable table,
		final Object value,
		final boolean isSelected, final boolean hasFocus,
		final int row, final int column
	){
		final var c = delegate.getTableCellRendererComponent(table,
			value,
			isSelected, hasFocus,
			row, column
		);

		if(c instanceof JLabel jl)
			jl.setText(switch(value){
				case String s when row == -1 -> bundle.get(s);
				case Localizable l -> bundle.get(l);
				default -> value.toString();
			});

		return c;
	}
}
