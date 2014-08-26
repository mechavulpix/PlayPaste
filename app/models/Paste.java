package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.config.GlobalProperties;

@Entity 
public class Paste extends Model {
    
    @Id
    @Column(columnDefinition = "CHAR(8)")
    public String id;
    
    @Column(columnDefinition = "TEXT")
    public String pasteData;
    
    @Basic(optional=true)
    @Column(columnDefinition = "CHAR(8)")
    public String key;
    
    public static Finder<String,Paste> find = new Finder<String,Paste> (
        String.class, Paste.class
    );
    
    
    /**
     *  Constructor for creating a new paste object
     *
     *  @param pasteText the test of the paste we are creating
     *  @param isSecure boolean flag, set if this paste will be created with a
     *      random key as well
     *  @return Returns an instances of a newly created Paste
    */
    public Paste(String pasteText, boolean isSecure) {
        // Generate an Id for the paste
        this.id = genKey();
        this.pasteData = pasteText;
        
        // If this paste isSecure, then we need to generate a key for it
        if ( isSecure ) {
            this.key = genKey();
        }
        
        // Otherwise, we'll leave it null
        else {
            this.key = null;
        }
        
        Ebean.save(this);
    }
    
    
    
    
    /**
     *  psuedo-constructor for grabing a Paste from the database
     *
     *  @param pasteId id of the Paste to pull from the database
     *  @param pasteKey the security key for this paste, or null if there is
     *      none
     *  @return Returns an instances of the Paste with the specified id
    */
    public static Paste getPaste(String pasteId, String pasteKey) {
        Paste requestedPaste = null;
        
        // Attempt to look up the paste
        requestedPaste = Paste.find.byId(pasteId);
        
        // If we have a null key, then check that the key for this paste is null
        if ( pasteKey == null && requestedPaste.key != null ) {
            // if not, pretend we didn't find a paste
            requestedPaste = null;
        }
            
        // otherwise, make sure that the keys match
        else if ( pasteKey != null && !requestedPaste.key.equals(pasteKey)) {
            // if they keys don't match, pretend like the paste doesn't exist
            requestedPaste = null;
        }
        
        return requestedPaste;
    }
    
    /* Internal Functions */
    /**
     *  Internal function to generate a random 8-character key
     *
     *  @return A new 8-character key
    */
    private String genKey() {
        Random keygen = new Random();
        StringBuilder key = new StringBuilder(8);
        
        // Loop to create 8 characters
        for ( int counter = 0; counter < 8; counter++ ) {
            key.append( (char) (keygen.nextInt(26) + 97) );
        }
        
        return key.toString();
    }
    
}
