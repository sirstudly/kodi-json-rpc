
package kodi.jsonrpc.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import kodi.jsonrpc.beans.GetMovieDetailsRequest;
import kodi.jsonrpc.beans.GetMoviesRequest;
import kodi.jsonrpc.beans.JsonRequest;
import kodi.jsonrpc.beans.MovieEntry;
import kodi.jsonrpc.beans.RemoveMovieRequest;
import kodi.jsonrpc.beans.SetMovieDetailsRequest;

@Component
public class KodiJsonRpcService {

    private static final Logger LOGGER = LoggerFactory.getLogger( KodiJsonRpcService.class );

    @Value( "${kodi.jsonrpc.host}" )
    private String kodiHost;

    @Value( "${kodi.jsonrpc.port}" )
    private int kodiPort;

    @Autowired
    private Gson gson;

    /**
     * Retrieves all films from the default source.
     * 
     * @return stream of MovieEntry with the basic details
     */
    public Stream<MovieEntry> exportLibrary() {
        LOGGER.info( "Starting ExportLibrary on " + kodiHost + ":" + kodiPort );
        ResponseEntity<String> response = doPost( gson.toJson( new GetMoviesRequest() ) );
        return StreamSupport.stream(
                gson.fromJson( response.getBody(), JsonElement.class ).getAsJsonObject()
                        .get( "result" ).getAsJsonObject()
                        .get( "movies" ).getAsJsonArray().spliterator(), false )
                .map( e -> gson.fromJson( e, MovieEntry.class ) );
    }

    /**
     * Updates the title of the given film.
     * 
     * @param movieId unique movie ID
     * @param title new title to set
     * @throws IOException on update error
     */
    public void updateMovieTitle( int movieId, String title ) throws IOException {
        LOGGER.info( "Updating film title " + movieId + " to " + title );
        ResponseEntity<String> response = doPost( gson.toJson(
                new SetMovieDetailsRequest( movieId, title ) ) );
        String status = gson.fromJson( response.getBody(), JsonElement.class ).getAsJsonObject()
                .get( "result" ).getAsString();
        if ( false == "OK".equals( status ) ) {
            throw new IOException( "Unexpected response: " + status );
        }
    }

    /**
     * Processes each film with the detailed movie properties for the given film.
     * 
     * @param film non-null film with valid movieId
     */
    public void populateMovieDetails( MovieEntry film ) {

        ResponseEntity<String> response = doPost( gson.toJson( new GetMovieDetailsRequest( film.getMovieId() ) ) );
        JsonObject details = gson.fromJson( response.getBody(), JsonElement.class ).getAsJsonObject()
                .get( "result" ).getAsJsonObject()
                .get( "moviedetails" ).getAsJsonObject();

        // update our record
        film.setImdbNumber( details.get( "imdbnumber" ).getAsString() );
        film.setOriginalTitle( details.get( "originaltitle" ).getAsString() );
        film.setTitle( details.get( "title" ).getAsString() );
        film.setSortTitle( details.get( "sorttitle" ).getAsString() );
    }

    /**
     * Removes the given film.
     * 
     * @param movieId unique movie ID
     * @throws IOException on update error
     */
    public void removeMovie( int movieId ) throws IOException {
        LOGGER.info( "Removing film " + movieId );
        ResponseEntity<String> response = doPost( gson.toJson(
                new RemoveMovieRequest( movieId ) ) );
        String status = gson.fromJson( response.getBody(), JsonElement.class ).getAsJsonObject()
                .get( "result" ).getAsString();
        if ( false == "OK".equals( status ) ) {
            throw new IOException( "Unexpected response: " + status );
        }
    }

    public void refreshVideoLibrary() throws IOException {
        LOGGER.info( "Refreshing video library" );
        ResponseEntity<String> response = doPost( gson.toJson(
                new JsonRequest( "VideoLibrary.Scan" ) ) );
        String status = gson.fromJson( response.getBody(), JsonElement.class ).getAsJsonObject()
                .get( "result" ).getAsString();
        if ( false == "OK".equals( status ) ) {
            throw new IOException( "Unexpected response: " + status );
        }
    }

    /**
     * Posts the given requet to KODI
     * 
     * @param requestBody JSON body
     * @return response
     */
    private ResponseEntity<String> doPost( String requestBody ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        headers.setAccept( Arrays.asList( MediaType.APPLICATION_JSON ) );
        HttpEntity<String> request = new HttpEntity<>( requestBody, headers );

        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add( 0, new StringHttpMessageConverter( Charset.forName( "UTF-8" ) ) );
        String uri = new String( "http://" + kodiHost + ":" + kodiPort + "/jsonrpc" );

        LOGGER.debug( "POST: " + uri );
        LOGGER.debug( "============= REQUEST =============" );
        LOGGER.debug( requestBody );
        ResponseEntity<String> response = rt.postForEntity( uri, request, String.class, new Object[]{} );
        LOGGER.debug( "============= RESPONSE =============" );
        LOGGER.debug( response.getBody() );
        return response;
    }
}
