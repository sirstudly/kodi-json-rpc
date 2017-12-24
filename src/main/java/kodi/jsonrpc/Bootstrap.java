
package kodi.jsonrpc;

import java.io.IOException;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import kodi.jsonrpc.config.KodiConfig;
import kodi.jsonrpc.services.KodiJsonRpcService;

public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger( Bootstrap.class );

    public static void main( String argv[] ) {
        TimeZone.setDefault( TimeZone.getTimeZone( "Europe/London" ) );

        try (AbstractApplicationContext context = new AnnotationConfigApplicationContext( KodiConfig.class );) {
            KodiJsonRpcService service = context.getBean( KodiJsonRpcService.class ); // bootstrap
            service.exportLibrary()
                    .map( f -> {
                        service.populateMovieDetails( f );
                        return f;
                    } )
                    .forEach( m -> {
                        if ( m.isForeignTitle() && false == m.isAlreadyFormatted() ) {
                            try {
                                service.updateMovieTitle( m.getMovieId(), m.getTitle() + " (" + m.getOriginalTitle() + ")" );
                            }
                            catch ( IOException ex ) {
                                throw new RuntimeException( ex );
                            }
                        }
                        else {
                            LOGGER.info( "Skipping " + m.getTitle() );
                        }
                    } );
        }
    }

}
