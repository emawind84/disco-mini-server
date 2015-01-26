package pmis.common.util;

public abstract class MapAdapter<C,V> extends AbstractMapAdapter<String, V> {

	private C container;

	public MapAdapter ( C container ) {
		super();
		// TODO Auto-generated constructor stub
		this.container = container;
	}

	public abstract V get ( C container, String key );

	@Override
	public V get ( Object key ) {
		if ( key instanceof String ) {
			return get( container, (String) key );
		} else {
			throw new RuntimeException( "Key must be a String" );
		}
	}

	@Override
	public V put(String key, V value) {
		return put( container, key, value);
	}
	
	public V put( C container, String key, V value ) {
		throw incompatible();
	}

	public C getContainer() {
		return container;
	}
	
	
}
