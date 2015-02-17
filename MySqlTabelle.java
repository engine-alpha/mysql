import java.util.ArrayList;

/**
 * Klasse MySqlTabelle repraesentiert eine Ergebnis-Tabelle nach ener SQL-Abfrage. 
 * Die Zeilen- und Spalten-Nummern beginnen bei 0. 
 * Zeile 0 ist die Ueberschrift. 
 * Alle Werte sind als String gespeichert und werden als String ausgegeben. 
 * Der Benutzer muss die zurueck erhaltenen Werte bei Bedarf SELBST wieder zum richtigen Typ casten !!!
 * 
 * @author      mike ganshorn
 * 
 * @version     1.0 (2015-02-17)
 */
public class MySqlTabelle
{
    private int anzahlZeilen;   // Die Ueberschrift ist Zeile 0
    private int anzahlSpalten;
    private ArrayList<MySqlDatensatz> zeilen;
    
    

    /**
     * Konstruktor der Klasse MySqlTabelle.
     */
    public MySqlTabelle()
    {
        this.anzahlZeilen = 0;
        this.anzahlSpalten = 0;
        this.zeilen = new ArrayList<MySqlDatensatz>();
    }
    
    
    
    /**
     * Fuegt der Tabelle einen weiteren Datensatz hinzu
     *
     * @param   datensatz   Referenz auf das neue MySqlDatensatz-Objekt
     */
    public void datensatzHinzufuegen(MySqlDatensatz datensatz)
    {
        this.zeilen.add(datensatz);
        this.anzahlZeilen++;
        this.anzahlSpalten = this.zeilen.get(0).nenneAnzahlAttribute();
    }

    
    
    // Ueberschrift ist Zeile 0 - Datensaetze ab Zeile 1
    /**
     * Gibt den Zellwert einer bestimmten Zelle zurueck. 
     *
     * @param   zeile   Die Nummer der Zeile (Uberschrift hat Nummer 0)
     * @param   spalte  Die Nummer der Spalte (beginnend bei 0)
     * 
     * @return  Zellwert an dieser Stelle in der Tabelle
     */
    public String nenneZellwert(int zeile, int spalte)
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
     * Gibt die Anzahl an Datensaetzen zurueck. Die Ueberschrift wird NICHT als Datensatz gezaehlt!
     *
     * @return  Anzahl an Datensaetzen der Tabelle (0 , wenn leer, -1 wenn SQL-Statement "verunglueckt")
     */
    public int nenneAnzahlDatensaetze()
    {
        return this.anzahlZeilen - 1;
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
}
