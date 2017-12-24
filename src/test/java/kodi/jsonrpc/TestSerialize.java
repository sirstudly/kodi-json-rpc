
package kodi.jsonrpc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

import kodi.jsonrpc.beans.GetMovieDetailsRequest;
import kodi.jsonrpc.config.KodiConfig;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = KodiConfig.class )
public class TestSerialize {

    private static final Logger LOGGER = LoggerFactory.getLogger( TestSerialize.class );

    @Autowired
    private Gson gson;

    @Test
    public void testGetMovieDetailsRequest() {
        LOGGER.info( gson.toJson( new GetMovieDetailsRequest( 12 ) ) );
    }
}
