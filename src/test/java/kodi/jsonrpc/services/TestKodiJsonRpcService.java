
package kodi.jsonrpc.services;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kodi.jsonrpc.beans.MovieEntry;
import kodi.jsonrpc.config.KodiConfig;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = KodiConfig.class )
public class TestKodiJsonRpcService {

    private static final Logger LOGGER = LoggerFactory.getLogger( TestKodiJsonRpcService.class );

    @Autowired
    private KodiJsonRpcService service;

    @Test
    public void testUpdateTitle() throws Exception {
        service.updateMovieTitle( 3416, "Tomorrow (Demain)" );
        LOGGER.info( "DONE" );
    }

    @Test
    public void testGetMovieDetails() throws Exception {
        MovieEntry film = new MovieEntry();
        film.setMovieId( 3416 );
        service.populateMovieDetails( film );
        LOGGER.info( ToStringBuilder.reflectionToString( film ) );
    }

    @Test
    public void testRemoveMovie() throws Exception {
        service.removeMovie( 3398 );
    }

    @Test
    public void testRefreshVideoLibrary() throws Exception {
        service.refreshVideoLibrary();
        LOGGER.info( "DONE" );
    }

}
