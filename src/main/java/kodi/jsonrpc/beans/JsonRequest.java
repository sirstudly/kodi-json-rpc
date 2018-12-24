
package kodi.jsonrpc.beans;

import com.google.gson.annotations.SerializedName;

public class JsonRequest {

    @SerializedName( "jsonrpc" )
    private String version = "2.0";

    @SerializedName( "id" )
    private int id = 1;

    @SerializedName( "method" )
    private String method;

    public JsonRequest() {
        // default constructor
    }

    public JsonRequest( String method ) {
        setMethod( method );
    }

    public String getVersion() {
        return version;
    }

    public void setVersion( String version ) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod( String method ) {
        this.method = method;
    }

}
