package dev.pimous.pu.jutils.ui;

import javax.swing.*;
import java.io.Serial;

/**
 * @author APG-Gillardeau
 * @since 1.1.0
 */
public class EnumListModel<E extends Enum<E>> extends AbstractListModel<E>
	implements ComboBoxModel<E>
{

	@Serial
	private static final long serialVersionUID = 4598659212925665536L;

	private final Class<E> enumType;
	private E selection = null;

	public EnumListModel(final Class<E> enumClass){
		this.enumType = enumClass;
	}

	// GETTERS
	@Override
	public int getSize(){
		return enumType.getEnumConstants().length;
	}
	@Override
	public E getElementAt(int index){
		return enumType.getEnumConstants()[index];
	}
	@Override
	public E getSelectedItem(){
		return selection;
	}

	// SETTERS
	@Override
	public void setSelectedItem(Object anItem){
		if(anItem != null && !enumType.isInstance(anItem))
			throw new IllegalArgumentException("Incompatible item;");
		else if(anItem == null && selection == null
			|| anItem != null && anItem.equals(selection)
		) return;

		selection = enumType.cast(anItem);
		fireContentsChanged(this, -1, -1); // See DefaultComboBoxModel
	}
}
