import java.util.ArrayList;


/**
 * Klasse Datensatz repraesentiert einen Datensatz (Zeile) in einer Datenbank-Tabelle. 
 * Alle Attribute werden als String gespeichert und als String wieder ausgegeben. 
 * Der Benutzer muss die zurueck erhaltenen Werte bei Bedarf SELBST wieder zum richtigen Typ casten !!!
 * 
 * @author      mike ganshorn, manuel hengge
 * 
 * @version     1.1 (2017-04-10)
 */
public class MySqlDatensatz
{
    
    private int anzahlAttribute;
    private ArrayList<String> attribute;

    
    
    /**
     * Konstruktor der Klasse Datensatz.
     */
    public MySqlDatensatz()
    {
        this.anzahlAttribute = 0;
        this.attribute = new ArrayList<String>();
    }

    
    /**
     * Fuegt dem Datensatz-Objekt ein weiteres Attribut hinzu. 
     *
     * @param   attributWert    Der Wert des neuen Attributs
     */
    public void attributHinzufuegen( String attributWert )
    {
        this.attribute.add( attributWert );
        this.anzahlAttribute++;
    }
   
    
    /**
     * Gibt den Attributwert zu einem Index zurueck. 
     *
     * @param   index   Der Index (die Nummer) des Attributs, beginnend mit 0
     * 
     * @return  Der Attributwert mit diesr Nummer
     */
    public String nenneAttributWert( int index )
    {
        String attributWert = "NULL";
        if ( index >= 0  &&  index < this.anzahlAttribute )
        {
            attributWert = this.attribute.get( index );
        }
        return attributWert;
    }
    
    
    /**
     * Gibt die Anzahl an enthaltenen Attributen zurueck. 
     *
     * @return  Anzahl der Attribute dieses Datensatzes
     */
    public int nenneAnzahlAttribute()
    {
        return this.anzahlAttribute;
    }
    
    /**
     * Gibt eine komplette Zeile mit Tabulator getrennt als einzelnen String zurück. 
     */
    public String datensatzAlsString()
    {
        String zeile = "";
        for(int i = 0; i<attribute.size();i++){
            zeile = zeile + attribute.get(i)+"\t";
        }
        zeile = zeile + "\n";
        
        return zeile;
    
    }
    
    
}
