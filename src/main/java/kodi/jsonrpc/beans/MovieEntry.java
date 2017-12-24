
package kodi.jsonrpc.beans;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.SerializedName;

public class MovieEntry {

    @SerializedName( "movieid" )
    private int movieId;

    @SerializedName( "label" )
    private String label;

    @SerializedName( "originaltitle" )
    private String originalTitle;

    @SerializedName( "title" )
    private String title;

    @SerializedName( "sorttitle" )
    private String sortTitle;

    @SerializedName( "imdbnumber" )
    private String imdbNumber;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId( int movieId ) {
        this.movieId = movieId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle( String originalTitle ) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getSortTitle() {
        return sortTitle;
    }

    public void setSortTitle( String sortTitle ) {
        this.sortTitle = sortTitle;
    }

    public String getImdbNumber() {
        return imdbNumber;
    }

    public void setImdbNumber( String imdbNumber ) {
        this.imdbNumber = imdbNumber;
    }

    /**
     * Returns true iff title != originalTitle
     * 
     * @return title is different to original
     */
    public boolean isForeignTitle() {
        return false == StringUtils.equals( getTitle(), getOriginalTitle() );
    }

    /**
     * Returns true iff title matches pattern: text (some additional text).
     * 
     * @return if we've already altered the title
     */
    public boolean isAlreadyFormatted() {
        return getTitle().matches( "^[^(]+\\([^)]+\\)$" );
    }

}
