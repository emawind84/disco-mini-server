package pmis.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapAdapter<K, V> implements Map<K, V> {

	public void clear () {
		throw incompatible();
	}

	public Set<Entry<K, V>> entrySet () {
		throw incompatible();
	}

	public abstract V get ( Object key );
	
	public boolean isEmpty () {
		return false;
	}

	@Override
	public Set<K> keySet () {
		throw incompatible();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw incompatible();
	}

	public V remove ( Object key ) {
		throw incompatible();
	}

	public int size () {
		return 0;
	}

	public Collection<V> values () {
		throw incompatible();
	}

	protected RuntimeException incompatible () {
		return new RuntimeException( "Incompatible method" );
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V put(K key, V value) {
		throw incompatible();
	}

}
