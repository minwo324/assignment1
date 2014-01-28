package one.ui;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import one.main.JSONHelper;

public class HttpRequest {

	// ATTRIBUTES
	private String _type;
	private Map<String,String> _header;
	private String _json;
	private Map<String,Object> _map;

	// CONSTRUCTORS
	public HttpRequest( BufferedReader br ) {
		try {
			String input = br.readLine();
			this._type = input.split( " / " )[0].toUpperCase();
			this._header = new HashMap<String,String>();
			while ( input.compareTo("") != 0 ) {
				input = br.readLine();
				int splitIndex = input.indexOf( ':' );
				if ( splitIndex != -1 ) {
					this.getHeader().put( input.substring( 0, splitIndex ).trim(), input.substring( splitIndex + 1 ).trim() );
				}
			}
			StringBuilder sb = new StringBuilder();
			while ( br.ready() ) {
				sb.append( (char) br.read() );
			}
			this._json = sb.toString();
			this._map = JSONHelper.fromJSON( this._json );
		} catch ( Exception e ) {
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	// GETTERS
	public String getType() {
		return this._type;
	}
	public Map<String,String> getHeader() {
		return this._header;
	}
	public String getJSON() {
		return this._json;
	}
	public Map<String,Object> getMap() {
		return this._map;
	}

	// METHODS
	public Object get( String key ) {
		return this.getMap().get( key );
	}
	@Override
	public String toString() {
		return "UI.HttpRequest: RECEIVE REQUEST\n\t" + this.getJSON();
	}

}
