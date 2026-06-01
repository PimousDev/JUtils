package dev.pimous.pu.jutils.ui;

import dev.pimous.pu.jutils.i18n.Localizable;
import dev.pimous.pu.jutils.i18n.Sentence;
import org.junit.jupiter.api.Test;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class EnumTableModelTest{

	@Test
	void tableModel(){
		final var table = new EnumTableModel<>(Vegetables.class);
		final var listener = new TableEventRecorder();
		table.addTableModelListener(listener);

		assertEquals(3, table.getColumnCount());
		assertEquals(0, table.getRowCount());
		assertNull(table.getColumnName(0));
		assertEquals("", table.getColumnName(1));
		assertEquals("Mmm", table.getColumnName(2));
		assertNull(table.getColumnClass(0));
		assertEquals(Localizable.class, table.getColumnClass(1));
		assertEquals(Integer.class, table.getColumnClass(2));

		assertThrows(IndexOutOfBoundsException.class,
			() -> table.getValueAt(0, 0)
		);

		assertDoesNotThrow(() -> table.setData(List.of("Hello")));
		assertEquals(TableModelEvent.ALL_COLUMNS,
			listener.events.getFirst().getColumn()
		);
		assertEquals(TableModelEvent.UPDATE,
			listener.events.getFirst().getType()
		);
		listener.events.clear();

		assertEquals("Hello", table.getValueAt(0, 0));
		assertEquals(new Sentence("afl", "Hello"), table.getValueAt(0, 1));
		assertEquals(69609650, table.getValueAt(0, 2));
	}

	@Test
	void localization(){
		final var table = new EnumTableModel<>(I18nColumn.class);

		assertEquals("l", table.getColumnName(0));
	}

	// INNER CLASSES
	private enum Vegetables implements ColumnModel{
		TOMATO(null, null, Function.identity()),
		CUCUMBER("", Localizable.class, o -> new Sentence("afl", o)),
		SALAD("Mmm", Integer.class, Object::hashCode);

		private final String name;
		private final Class<?> type;
		private final Function<Object, ?> parser;

		<E> Vegetables(final String name,
			final Class<E> type,
			final Function<Object, E> parser
		){
			this.name = name;
			this.type = type;
			this.parser = parser;
		}

		@Override
		public String getName(){ return name; }
		@Override
		public Class<?> getType(){ return type; }
		@Override
		public Function<Object, ?> getParser(){ return parser; }
	}
	private enum I18nColumn implements ColumnModel, Localizable{

		COLUMN("af");

		private final String name;

		I18nColumn(final String name){
			this.name = name;
		}

		@Override
		public String getName(){ return name; }
		@Override
		public Class<?> getType(){ return Object.class; }
		@Override
		public Function<Object, ?> getParser(){ return Function.identity(); }

		@Override
		public Sentence getSentence(){ return new Sentence("l"); }
	}

	private static class TableEventRecorder implements TableModelListener{

		public List<TableModelEvent> events = new ArrayList<>();

		@Override
		public void tableChanged(TableModelEvent e){ events.add(e); }
	}
}
