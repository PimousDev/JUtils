package dev.pimous.pu.jutils.base;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Xibitol
 * @since 1.1.0
 */
public final class StreamUtils{

	private StreamUtils(){}

	// FUNCTIONS
	public static <K, V> Collector<
		? super Map.Entry<K, V>, ?, Map<K, V>
	> toMap(){
		return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
	}
}
