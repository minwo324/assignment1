package one.ui;

import java.io.DataOutputStream;
import java.util.Map;

import one.main.JSONHelper;

public class HttpResponse {

	// ATTRIBUTES
	private String _status;
	private String[] _header;
	private String _json;
	private Map<String,Object> _map;

	public HttpResponse( DataOutputStream dos, Map<String,Object> map ) {
		this._map = map;
		this._json = JSONHelper.toJSON( map );
		this._header = new String[]{
			"Content-Type: application/json",
			"Content-Length: " + this.getJSON().length()
		};
		this._status = "200 OK";
		this.send( dos );
	}

	// GETTERS
	public String getStatus() {
		return this._status;
	}
	public String[] getHeader() {
		return this._header;
	}
	public String getJSON() {
		return this._json;
	}
	public Map<String,Object> getMap() {
		return this._map;
	}

	// METHODS
	public void send( DataOutputStream dos ) {
		try {
			dos.writeBytes( "HTTP/1.1 " + this.getStatus() + "\r\n" );
			for ( String line: this.getHeader() ) {
				dos.writeBytes( line + "\r\n" );
			}
			dos.writeBytes( "\r\n" );
			dos.writeBytes( this.getJSON() + "\r\n" );
		} catch ( Exception e) {
			System.exit( 1 );
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return "UI.HttpResponse: SEND RESPONSE\n\t" + this.getJSON();
	}

}
