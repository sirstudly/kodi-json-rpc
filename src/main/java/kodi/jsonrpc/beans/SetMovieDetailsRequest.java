
package kodi.jsonrpc.beans;

import com.google.gson.annotations.SerializedName;

public class SetMovieDetailsRequest extends JsonRequest {

    static class Params {

        @SerializedName( "movieid" )
        public final int movieid;

        @SerializedName( "title" )
        public final String title;

        public Params( int movieid, String title ) {
            this.movieid = movieid;
            this.title = title;
        }
    }

    @SerializedName( "params" )
    public final Params params;

    public SetMovieDetailsRequest( int movieid, String title ) {
        this.params = new Params( movieid, title );
        setMethod( "VideoLibrary.SetMovieDetails" );
    }

}
