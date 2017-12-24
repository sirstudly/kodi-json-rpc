
package kodi.jsonrpc.beans;

public class GetMoviesRequest extends JsonRequest {

    public GetMoviesRequest() {
        setMethod( "VideoLibrary.GetMovies" );
    }
}
