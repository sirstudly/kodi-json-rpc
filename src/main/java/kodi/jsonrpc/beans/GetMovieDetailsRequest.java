
package kodi.jsonrpc.beans;

import com.google.gson.annotations.SerializedName;

public class GetMovieDetailsRequest extends JsonRequest {

    static class Params {

        @SerializedName( "movieid" )
        public final int movieid;

        @SerializedName( "properties" )
        public final String[] properties = { "originaltitle", "imdbnumber", "title", "sorttitle" };

        public Params( int movieid ) {
            this.movieid = movieid;
        }
    }

    @SerializedName( "params" )
    public final Params params;

    public GetMovieDetailsRequest( int movieid ) {
        this.params = new Params( movieid );
        setMethod( "VideoLibrary.GetMovieDetails" );
    }
}
