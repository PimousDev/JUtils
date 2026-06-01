package dev.pimous.pu.jutils.ui;

import dev.pimous.pu.jutils.i18n.Sentence;
import dev.pimous.pu.jutils.i18n.impl.I18nConcreteBundle;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LocalCellRendererTest{

	@Test
	void list(){
		final var renderer = new LocalListCellRenderer<>(
			new I18nConcreteBundle(Locale.ENGLISH),
			new DefaultListCellRenderer()
		);

		final var c = assertDoesNotThrow(
			() -> renderer.getListCellRendererComponent(
				new JList<>(), new Sentence("afl"), 0, false, false
			)
		);
		assertEquals("#UNDEFINED#", ((JLabel) c).getText());
	}

	@Test
	void table(){
		final var renderer = new LocalTableCellRenderer(
			new I18nConcreteBundle(Locale.ENGLISH),
			new DefaultTableCellRenderer()
		);
		final var table = new JTable();

		var c = assertDoesNotThrow(
			() -> renderer.getTableCellRendererComponent(
				table, "afl", false, false, 0, 0
			)
		);
		assertEquals("afl", ((JLabel) c).getText());

		c = assertDoesNotThrow(
			() -> renderer.getTableCellRendererComponent(
				table, "afl", false, false, -1, 0
			)
		);
		assertEquals("#UNDEFINED#", ((JLabel) c).getText());

		c = assertDoesNotThrow(
			() -> renderer.getTableCellRendererComponent(
				table, new Sentence("afl"), false, false, 0, 0
			)
		);
		assertEquals("#UNDEFINED#", ((JLabel) c).getText());

		c = assertDoesNotThrow(
			() -> renderer.getTableCellRendererComponent(
				table, 31, false, false, 0, 0
			)
		);
		assertEquals("31", ((JLabel) c).getText());
	}
}
