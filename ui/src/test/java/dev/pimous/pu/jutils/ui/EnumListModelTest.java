package dev.pimous.pu.jutils.ui;

import org.junit.jupiter.api.Test;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnumListModelTest{

	@Test
	void listModel(){
		final var list = new EnumListModel<>(Fruits.class);

		assertEquals(3, list.getSize());
		assertEquals(Fruits.STRAWBERRY, list.getElementAt(0));
		assertEquals(Fruits.LEMON, list.getElementAt(2));
		assertNull(list.getSelectedItem());

		assertNull(list.getSelectedItem());
	}

	@Test
	void comboBoxModel(){
		final var list = new EnumListModel<>(Fruits.class);
		final var listener = new ListEventRecorder();
		list.addListDataListener(listener);

		assertNull(list.getSelectedItem());

		assertDoesNotThrow(() -> list.setSelectedItem(null));
		assertTrue(listener.events.isEmpty());

		assertThrows(IllegalArgumentException.class,
			() -> list.setSelectedItem("")
		);
		assertTrue(listener.events.isEmpty());

		assertDoesNotThrow(() -> list.setSelectedItem(Fruits.STRAWBERRY));
		assertEquals(Fruits.STRAWBERRY, list.getSelectedItem());


		assertDoesNotThrow(() -> list.setSelectedItem(Fruits.STRAWBERRY));
		assertEquals(ListDataEvent.CONTENTS_CHANGED,
			listener.events.getFirst().getType()
		);
		listener.events.clear();

		assertDoesNotThrow(() -> list.setSelectedItem(null));
		assertNull(list.getSelectedItem());
		assertEquals(ListDataEvent.CONTENTS_CHANGED,
			listener.events.getFirst().getType()
		);
		listener.events.clear();
	}

	// INNER CLASSES
	private enum Fruits{
		STRAWBERRY,
		TOMATO,
		LEMON
	}

	private static class ListEventRecorder implements ListDataListener{

		public List<ListDataEvent> events = new ArrayList<>();

		@Override
		public void intervalAdded(ListDataEvent e){ events.add(e); }
		@Override
		public void intervalRemoved(ListDataEvent e){ events.add(e); }
		@Override
		public void contentsChanged(ListDataEvent e){ events.add(e); }
	}
}
