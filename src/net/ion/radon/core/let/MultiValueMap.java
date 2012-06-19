package net.ion.radon.core.let;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;

//Thread Unsafe
public class MultiValueMap implements Map, Serializable {

	private HashMap inner = new HashMap<String, Object>();

	MultiValueMap() {
		super();
	}

	public Object getFirstValue(String name) {
		Object value = inner.get(name);
		if (value == null)
			return null;
		if (value instanceof List)
			return ObjectUtil.toString(((List) value).get(0));
		else
			return value;
	}

	public void putParameter(String name, String[] valuesArray) {
		if (valuesArray != null && valuesArray.length > 1) {
			List list = ListUtil.newList();
			for (String value : valuesArray) {
				list.add(implicitTypeValue(value));
			}
			put(name, list);
		} else if (valuesArray != null) {
			put(name, implicitTypeValue(valuesArray[0]));
		}
	}

	public void putParameter(String name, Object value) {
		if (value.getClass().isArray()) {
			Object[] values = (Object[]) value;
			for (Object object : values) {
				putParameter(name, object);
			}
		} else {
			if (inner.containsKey(name)) {
				List list = getAsList(name);
				list.add(implicitTypeValue(value));
				put(name, list);
			} else {
				put(name, implicitTypeValue(value));
			}
		}
	}

	private Object implicitTypeValue(Object _value) {
		if (_value == null) {
			return ObjectUtil.NULL ;
//		} else if (NumberUtil.isNumber(_value.toString()) && String.valueOf(NumberUtil.toLong(_value.toString())).equals(_value.toString())) {
//			return NumberUtil.toLong(_value.toString());
//		} else if (NumberUtil.isNumber(_value.toString())) {
//			return NumberUtil.toDouble(_value.toString());
		} else {
			return _value;
		}
	}

	public List getAsList(String key) {
		if (containsKey(key)) {
			Object o = inner.get(key);
			if (o instanceof List) {
				return (List) o;
			} else {
				List list = new ArrayList();
				list.add(o);
				return list;
				// return ListUtil.create(o) ;
			}
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	public Object put(Object key, Object _value) {

		return inner.put((String) key, _value);

		/*
		 * if (_value == null) return null; Object value = null ; if (NumberUtil.isNumber(_value.toString()) && String.valueOf(NumberUtil.toLong(_value.toString())).equals(_value.toString())) { value = NumberUtil.toLong(_value.toString()) ; } else if (NumberUtil.isNumber(_value.toString())) { value = NumberUtil.toDouble(_value.toString()) ; } else
		 * { value = _value ; }
		 * 
		 * if (containsKey(key)) { Object other = get(key); if (other instanceof List) { ((List) other).add(value); } else { List list = ListUtil.newList(); list.add(other); list.add(value); return inner.put((String) key, (Object) list); } } else { return inner.put((String) key, value); } return get(key);
		 */
	}

	public void clear() {
		inner.clear();
	}

	public boolean containsKey(Object key) {
		return inner.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return inner.containsKey(value);
	}

	public Set<Entry> entrySet() {
		return inner.entrySet();
	}

	public Object get(Object key) {
		return getFirstValue(key.toString());
	}

	public boolean isEmpty() {
		return inner.isEmpty();
	}

	public Set keySet() {
		return inner.keySet();
	}

	public void putAll(Map m) {
		inner.putAll(m);
	}

	public Object remove(Object key) {
		return inner.remove(key);
	}

	public int size() {
		return inner.size();
	}

	public Collection values() {
		return inner.values();
	}

	public String toString() {
		return inner.toString();
	}
}
