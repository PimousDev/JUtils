package dev.pimous.pu.jutils.base;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class IntrospectiveStringifierTest{

	@Test
	void classes(){
		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$A{af=31}",
			IntrospectiveStringifier.fromPublics(A.class)
		);
		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$B{bf='l';ba=\"I'm \\\"foobar\\\", the famous text!\"}",
			IntrospectiveStringifier.fromPublics(B.class)
		);
		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$C{ca=null;bf='l';ba=\"I'm \\\"foobar\\\", the famous text!\";af=31}",
			IntrospectiveStringifier.fromPublics(C.class)
		);

		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$A{af=31;ab=1;ad=0}",
			IntrospectiveStringifier.fromAll(A.class)
		);
		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$B{bf='l';ba=\"I'm \\\"foobar\\\", the famous text!\"}",
			IntrospectiveStringifier.fromAll(B.class)
		);
		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$C{ca=null;cc=31;dvd=null}",
			IntrospectiveStringifier.fromAll(C.class)
		);
	}

	@Test
	void instances(){
		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$A(52){aa=0.0}",
			IntrospectiveStringifier.fromPublics(new A())
		);
		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$C(1612){cf=\"test\";aa=0.0}",
			IntrospectiveStringifier.fromPublics(new C())
		);

		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$A(52){aa=0.0;ac=20;micro=0.24}",
			IntrospectiveStringifier.fromAll(new A())
		);
		assertEquals(
			"dev.pimous.pu.jutils.base.IntrospectiveStringifierTest$C(1612){cf=\"test\";cb=null;cd=dev.pimous.pu.jutils.base.InternalException: There is no errors.}",
			IntrospectiveStringifier.fromAll(new C())
		);
	}

	// INNER CLASSES
	private static class A{

		public static int af = 31;
		public float aa;
		protected static short ab = 1;
		protected byte ac = 20;
		private static long ad;
		private double micro = 0.24D;

		// FUNCTIONS
		@Override
		public int hashCode(){
			return af + ab + ac;
		}
	}
	private static interface B{

		public final char bf = 'l'; // Static by default?
		public static final String ba = "I'm \"foobar\", the famous text!";
	}
	private static final class C extends A implements B{

		public String cf = "test";
		public static ResourcePaths ca;
		protected Logger cb;
		protected static AtomicLong cc = new AtomicLong(31);
		private InternalException cd = new InternalException("There is no errors.");
		private static BadResourceException dvd;

		// FUNCTIONS
		@Override
		public int hashCode(){
			return (af + ab + ac)*cc.intValue();
		}
	}
}
