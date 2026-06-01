package dev.pimous.pu.jutils.i18n;

import java.util.List;

/**
 * @author APG-Gillardeau
 * @since 1.1.0
 */
public record Sentence(String sentence, Object... args) implements Localizable{

	// GETTERS
	@Override
	public Sentence getSentence(){ return this; }

	// FUNCTIONS
	@Override
	public boolean equals(Object obj){
		return obj instanceof Sentence(var s, var a)
			&& s.equals(sentence)
			&& List.of(a).equals(List.of(args));
	}
}