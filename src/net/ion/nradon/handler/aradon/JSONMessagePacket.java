package net.ion.nradon.handler.aradon;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;

import org.apache.commons.lang.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONMessagePacket {

	public final static JSONMessagePacket EMPTY = new JSONMessagePacket(ValueObject.create(new JSONObject()));
	public static final JSONMessagePacket PING = new JSONMessagePacket(ValueObject.createPing());

	private JSONMessagePacket parent;
	private ValueObject vo;

	private JSONMessagePacket(ValueObject vo) {
		this.vo = vo;
	}

	private JSONMessagePacket(JSONMessagePacket parent, ValueObject valueObj) {
		this.parent = parent;
		this.vo = valueObj;
	}

	public final static JSONMessagePacket load(String message) {
		try {
			return new JSONMessagePacket(ValueObject.load(message));
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage() + " : " + message);
		}
	}

	public static JSONMessagePacket create() {
		return new JSONMessagePacket(new ValueObject(new JSONObject()));
	}

	public static JSONMessagePacket load(JSONObject jsonObject) {
		return new JSONMessagePacket(new ValueObject(jsonObject));
	}

	public JSONMessagePacket inner(String inname) throws IllegalArgumentException {
		try {
			if (vo.has(inname)) {
				return new JSONMessagePacket(this, ValueObject.load(vo.getJSONObject(inname)));
			} else {
				JSONObject newJSON = new JSONObject();
				vo.put(inname, newJSON);
				return new JSONMessagePacket(this, ValueObject.create(newJSON));
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public JSONMessagePacket[] child(String path) {
		try {
			JSONArray array = (JSONArray) (get(path));

			List<JSONMessagePacket> result = ListUtil.newList();
			for (int i = 0; i < array.length(); i++) {
				result.add(new JSONMessagePacket(this, ValueObject.create(array.getJSONObject(i))));
			}
			return result.toArray(new JSONMessagePacket[0]);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public Object get(String path) {
		try {
			return vo.get(path);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public String getString(String path) {
		try {
			return vo.getString(path, "");
		} catch (JSONException e) {
			return "";
		}
	}

	public String getString(String path, String dftValue) {
		try {
			return vo.getString(path, dftValue);
		} catch (JSONException e) {
			return dftValue;
		}
	}

	public JSONMessagePacket toParent() {
		return parent;
	}

	public JSONMessagePacket toRoot() {
		JSONMessagePacket current = this;
		while (current.parent != null) {
			current = current.parent;
		}
		return current;
	}

	public String getFullString() {
		String result = toRoot().vo.getString();
		toRoot();
		return result;
	}

	public boolean has(String path) {
		return vo.has(path);
	}

	public JSONMessagePacket put(String key, Object value) {
		try {
			vo.put(key, value);
			return this;
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public JSONMessagePacket array(String key, Object[] values) {
		try {
			vo.array(key, values);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this;
	}

	public JSONMessagePacket append(String key, Object value) {
		try {
			vo.append(key, value);
			return this;
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public JSONObject getJSONObject(String key) {
		try {
			return vo.getJSONObject(key);
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public Map<String, Object> toMap() {
		try {
			Iterator<String> iter = vo.json.keys();
			Map<String, Object> result = MapUtil.newMap();

			while (iter.hasNext()) {
				String key = iter.next();
				result.put(key, vo.json.get(key));
			}
			return result;
		} catch (JSONException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public String toString() {
		return getFullString();
	}

	private static class ValueObject {

		private JSONObject json;
		private static ValueObject EMPTY = new ValueObject(new JSONObject());

		private ValueObject(JSONObject json) {
			this.json = json;
		}

		private static ValueObject createPing() {
			try {
				return load("{head:{command:'ping'},body:{}}");
			} catch (JSONException e) {
				return EMPTY;
			}
		}

		public String getString() {
			return json.toString();
		}

		public String getString(String path) throws JSONException {
			return ObjectUtil.toString(get(path));
		}

		public String getString(String path, String dftValue) throws JSONException {
			if (has(path)) {
				return getString(path);
			} else {
				return dftValue;
			}
		}

		public static ValueObject load(String msg) throws JSONException {
			return load(new JSONObject(msg));
		}

		public static ValueObject load(JSONObject json) {
			return new ValueObject(json);
		}

		public final static ValueObject create(JSONObject json) {
			return new ValueObject(json);
		}

		public JSONObject getJSONObject(String name) throws JSONException {
			return json.getJSONObject(toKeyId(name));
		}

		public Object get(String path) throws JSONException {
			if (StringUtil.isBlank(path))
				return null;
			String[] names = StringUtil.split(path, "./");
			if (names.length == 1) {
				return getObject(names[0]);
			} else {
				return ValueObject.create(this.getJSONObject(names[0])).get(StringUtil.join(ArrayUtils.subarray(names, 1, names.length), "."));
			}
		}

		public Object getObject(String name) throws JSONException {
			return json.get(toKeyId(name));
		}

		public void put(String key, Object value) throws JSONException {
			json.put(toKeyId(key), value);
		}

		public void array(String key, Object[] values) throws JSONException {
			json.put(key, ListUtil.toList(values));
		}

		public void append(String key, Object value) throws JSONException {
			json.accumulate(key, value);
		}

		boolean has(String path) {
			if (StringUtil.isBlank(path))
				return false;

			String[] names = StringUtil.split(path.toLowerCase(), "./");
			if (names.length == 1) {
				return json.has(names[0]);
			} else {
				try {
					if (!json.has(names[0]))
						return false;
					if (!(json.get(names[0]) instanceof JSONObject))
						return false;
					return ValueObject.create(this.getJSONObject(names[0])).has(StringUtil.join(ArrayUtils.subarray(names, 1, names.length), "."));
				} catch (JSONException ignore) {
					return false;
				}
			}
		}

		private String toKeyId(String key) {
			return key.toLowerCase();
		}

	}

}
