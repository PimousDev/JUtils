package dev.pimous.pu.jutils.i18n;

/**
 * @author APG-Gillardeau
 * @since 1.1.0
 */
public record Sentence(String sentence, Object... args) implements Localizable{

	@Override
	public Sentence getSentence(){ return this; }
}