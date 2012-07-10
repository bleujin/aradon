package net.ion.radon.param;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.Debug;





abstract class TypeRef<T> {
	private final Type type;
	protected TypeRef() {
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		type = superclass.getActualTypeArguments()[0];
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof TypeRef && ((TypeRef) o).type.equals(type);
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}
}

public class Favorites2 {
	private Map<TypeRef<?>, Object> favorites = new HashMap<TypeRef<?>, Object>();

	public <T> void setFavorite(TypeRef<T> type, T thing) {
		favorites.put(type, thing);
	}

	public <T> T getFavorite(TypeRef<T> type) {
		return (T) favorites.get(type);
	}

	public static void main(String[] args) {
		Favorites2 f = new Favorites2();
		List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
		f.setFavorite(new TypeRef<List<String>>() {
		}, stooges);
		List<String> ls = f.getFavorite(new TypeRef<List<String>>() {
		});

		Debug.debug(ls);
	}
}
