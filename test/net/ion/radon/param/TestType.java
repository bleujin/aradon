package net.ion.radon.param;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import junit.framework.TestCase;

public class TestType extends TestCase {

	public void testFirst() throws Exception {
		Object object0 = new StringHome().getTypeParameterClass().newInstance();
		Object object1 = new StringBuilderHome().getTypeParameterClass().newInstance();
		Object object2 = new StringBufferHome().getTypeParameterClass().newInstance();
		System.out.println(object0.getClass().getSimpleName());
		System.out.println(object1.getClass().getSimpleName());
		System.out.println(object2.getClass().getSimpleName());

		System.out.println(new Home<StringBuilder>().getClass().getSimpleName());
	}

	private static class StringHome extends Home<String> {
	}

	private static class StringBuilderHome extends Home<StringBuilder> {
	}

	private static class StringBufferHome extends Home<StringBuffer> {
	}

}

class Home<E> {

	@SuppressWarnings("unchecked")
	public Class<E> getTypeParameterClass() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		return (Class<E>) paramType.getActualTypeArguments()[0];
	}

}