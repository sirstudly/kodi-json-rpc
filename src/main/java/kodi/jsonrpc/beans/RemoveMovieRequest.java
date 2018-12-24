
package kodi.jsonrpc.beans;

import com.google.gson.annotations.SerializedName;

public class RemoveMovieRequest extends JsonRequest {

    static class Params {

        @SerializedName( "movieid" )
        public final int movieid;

        public Params( int movieid ) {
            this.movieid = movieid;
        }
    }

    @SerializedName( "params" )
    public final Params params;

    public RemoveMovieRequest( int movieid ) {
        this.params = new Params( movieid );
        setMethod( "VideoLibrary.RemoveMovie" );
    }

}
