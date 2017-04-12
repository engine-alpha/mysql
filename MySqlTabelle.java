import java.util.ArrayList;

/**
 * Klasse MySqlTabelle repraesentiert eine Ergebnis-Tabelle nach ener SQL-Abfrage. 
 * Die Zeilen- und Spalten-Nummern beginnen bei 0. 
 * Zeile 0 ist die Ueberschrift. 
 * Alle Werte sind als String gespeichert und werden als String ausgegeben. 
 * Der Benutzer muss die zurueck erhaltenen Werte bei Bedarf SELBST wieder zum richtigen Typ casten !!!
 * 
 * @author      mike ganshorn, manuel hengge
 * 
 * @version     1.1 (2017-04-10)
 */
public class MySqlTabelle
{
    private int anzahlZeilen;   
    private int anzahlSpalten;
    private ArrayList<String> ueberschriften;
    private ArrayList<MySqlDatensatz> zeilen;
    
    

    /**
     * Konstruktor der Klasse MySqlTabelle.
     */
    public MySqlTabelle()
    {
        this.anzahlZeilen = 0;
        this.anzahlSpalten = 0;
        this.zeilen = new ArrayList<MySqlDatensatz>();
        this.ueberschriften = new ArrayList<String>();
    }
    
    
    
    /**
     * Fuegt der Tabelle einen weiteren Datensatz hinzu
     *
     * @param   datensatz   Referenz auf das neue MySqlDatensatz-Objekt
     */
    public void datensatzHinzufuegen( MySqlDatensatz datensatz )
    {
        this.zeilen.add( datensatz );
        this.anzahlZeilen++;
        this.anzahlSpalten = this.zeilen.get(0).nenneAnzahlAttribute();
    }
    
    /**
     * Fuegt einen neuen Attribut-Bezeichner fuer eine weitere Spalte in die ArrayList der Ueberschriften hinzu. 
     * 
     * @param   attribut    Eine neue Spaltenueberschrift
     */
    public void attributHinzufuegen( String attribut ) 
    {
        this.ueberschriften.add( attribut );
        
    }   
    
    /**
     * Gibt alle Spaltenbezeichnungen als einzelnen String zurück. 
     * 
     * @return  Alle Spalteueberschriften als ein TAB-separierter String
     */
    public String gibSpaltenbezeichnungen()
    {
        String spaltenbezeichnungen = "";
        
        for ( int i = 0 ; i<ueberschriften.size() ; i++ )
        {
            spaltenbezeichnungen = spaltenbezeichnungen + ueberschriften.get(i) + "\t";
        }
        
        return spaltenbezeichnungen;
    }

    
    /**
     * Gibt den Zellwert einer bestimmten Zelle zurueck. 
     *
     * @param   zeile   Die Nummer der Zeile (Uberschrift hat Nummer 0)
     * 
     * @param   spalte  Die Nummer der Spalte (beginnend bei 0)
     * 
     * @return  Zellwert an dieser Stelle in der Tabelle
     */
    public String nenneZellwert( int zeile , int spalte )
    {
        if ( zeile >= 0  &&  zeile <= this.anzahlZeilen  &&  spalte >= 0  &&  spalte <= this.anzahlSpalten )
        {
            return this.zeilen.get(zeile).nenneAttributWert(spalte);
        }
        else
        {
            return "NULL";
        }
    }
    
    
    
    /**
     * Gibt die Anzahl an Datensaetzen zurueck. 
     *
     * @return  Anzahl an Datensaetzen der Tabelle (0 , wenn leer, -1 wenn SQL-Statement "verunglueckt")
     */
    public int nenneAnzahlDatensaetze()
    {
        return this.anzahlZeilen;
    }
    
    
    
    /**
     * Nennt die Anzahl der Zeilen dieser Tabelle. 
     * 
     * @return  Anzahl der Zeilen dieser Tabelle. Die Ueberschrift ist Zeile 1. 
     *          Eine Tabelle, die ein leeres Resultat zurueck liefert, hat also die Laenge 1!
     */
    public int nenneAnzahlZeilen()
    {
        return this.anzahlZeilen;
    }
    
    
    
    /**
     * Nennt die Anzahl der Spalten dieser Tabelle. 
     *
     * @return  Anzahl der Spalten dieser Tabelle
     */
    public int nenneAnzahlSpalten()
    {
        if ( this.anzahlZeilen != 0 )
        {
            return this.zeilen.get(0).nenneAnzahlAttribute();
        }
        else
        {
            return 0;
        }
    }
    
    
    
    /**
     * Gibt die ArrayList von Datensaetzen zurück
     * 
     * @return ArrayList mit MySqlDatensatz-Objekten
     */
    public ArrayList<MySqlDatensatz> nenneZeilen()
    {
        return this.zeilen;
    }
}
