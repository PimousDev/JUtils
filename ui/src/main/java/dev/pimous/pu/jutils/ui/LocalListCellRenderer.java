package dev.pimous.pu.jutils.ui;

import dev.pimous.pu.jutils.i18n.I18nBundle;
import dev.pimous.pu.jutils.i18n.Localizable;

import javax.swing.*;
import java.awt.*;

/**
 * @author APG-Gillardeau
 * @since 1.1.0
 */
public class LocalListCellRenderer<E extends Localizable>
	implements ListCellRenderer<E>
{

	private final I18nBundle bundle;
	private final ListCellRenderer<? super E> delegate;

	public LocalListCellRenderer(final I18nBundle bundle,
		final ListCellRenderer<? super E> delegate
	){
		this.bundle = bundle;
		this.delegate = delegate;
	}

	// GETTERS
	@Override
	public Component getListCellRendererComponent(
		final JList<? extends E> list,
		final E value, final int index,
		final boolean isSelected, final boolean cellHasFocus
	){
		final var c = delegate.getListCellRendererComponent(list,
			value, index,
			isSelected, cellHasFocus
		);

		if(c instanceof JLabel jl)
			jl.setText(bundle.get(value));

		return c;
	}
}
