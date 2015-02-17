import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/**
 * Klasse MySqlZugriff soll eine einfache und intuitive Kommunikation mit einem MySQL-Server realisieren. 
 * Derzeit wird nur Port 3306 unterstuetzt! 
 * Noch werden alle Arbeitschritte auf der Konsole protokolliert. 
 * 
 * @author      mike ganshorn
 * 
 * @version     1.0 (2015-02-17)
 */
public class MySqlZugriff
{
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private String[] letzteMetaDaten;
    private String letztesSqlStatement;
    private boolean letztesSqlStatementFehlerfrei;
    private String letzteSqlFehlerMeldung;
    private MySqlTabelle letzteErgebnisTabelle;

    
    /**
     * MySQL_Zugriff Konstruktor. 
     * Versucht, den Datenbank-Treiber zu laden. 
     *
     */
    public MySqlZugriff()
    {
        this.connection = null;
        this.preparedStatement = null;
        this.resultSet = null;

        this.letztesSqlStatement = "";
        this.letzteMetaDaten = null;
        this.letzteSqlFehlerMeldung = "";
        this.letztesSqlStatementFehlerfrei = false;
        this.letzteErgebnisTabelle = new MySqlTabelle();

        try
        {
            this.datenbankTreiberLaden();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
            e.printStackTrace();
        }
    }

    
    /**
     * Methode verbindeMitServer
     *
     * @param   ip          IP-Adresse oder URL des Servers
     * @param   datenbank   Name der zu verwendenen Datenbank
     * @param   benutzer    Benutzer-Name fuer Server-Zugriff
     * @param   passwort    Passwort fuer Server-Zugriff
     * 
     * @return  'true', wenn die Verbindung erfolgreich war, sonst 'false'
     */
    public boolean verbindeMitServer(String ip, String datenbank, String benutzer, String passwort) 
    {
        boolean geklappt = false;

        try
        {
            System.out.println( "Verbindung mit Server steht" );
            this.connection = DriverManager.getConnection( "jdbc:mysql://" + ip + "/" + datenbank + "?" 
                + "user=" + benutzer + "&password=" + passwort );

            geklappt = true;
        }
        catch (Exception e) 
        {
            System.out.println( e.getMessage() );
            e.printStackTrace();
        } 
        finally
        {
            return geklappt;
        }
    }

    
    /**
     * Nimmt ein SQL-SELECT-Statement in Form eines Strings entgegen und fuehrt es aus. 
     * Das Ergebnis der Anfrage wird im Attribut resultSet gespeichert.
     *
     * @param   sql     Das SQL-Statement
     * 
     * @return  'true', wenn das SQL-Statement fehlerfrei war, sonst 'false'. 
     *          Im Falle von 'true' erhaelt man das Ergebnis mit 'nenneLetzteErgebnisTabelle()'
     */
    public boolean sql_select(String sql)
    {
        System.out.println("BEGIN sql_select -----");
        this.letztesSqlStatement = sql;
        this.letztesSqlStatementFehlerfrei = false;
        boolean geklappt_1 = false;
        boolean geklappt_2 = false;
        int anzahlDatensaetze = 0;
        
        try
        {
            System.out.println( "SQL:\t" + sql );
            // Statements ermoeglichen SQL-Anfragen an die Datenbank
            this.preparedStatement = this.connection.prepareStatement( sql );
            System.out.println("PreparedStatement erstellt");
            // Die Antwort auf die SQL-Anfrage speichern
            this.resultSet = this.preparedStatement.executeQuery();
            System.out.println("ResultSet erstellt");
            geklappt_1 = true;
            this.letztesSqlStatementFehlerfrei = true;
        }
        catch (MySQLSyntaxErrorException e) 
        {
            this.letzteSqlFehlerMeldung = e.getMessage();
            System.out.println( this.letzteSqlFehlerMeldung );
            e.printStackTrace();
            return geklappt_1;
        }
        catch ( SQLException e )
        {
            System.out.println( e.getMessage() );
            e.printStackTrace();
            return geklappt_1;
        }
        
        System.out.println("BEGIN Tabelle erzeugen --");
        if ( geklappt_1 )
        {
            try
            {   
                // Ueber die Zeilen der ResultSet-Objekt iterieren
                // der Iterator des ResultSet steht anfaenglich VOR dem ersten Datensatz 
                
                this.resultSet.next();
                System.out.println("naechste Zeile im ResultSet");
                anzahlDatensaetze++;
                MySqlDatensatz datensatz = new MySqlDatensatz();
                
                // MySqlDatensatz-Objekt erstellen (Ueberschrift)
                String[] metaDaten = this.metaDataAusgeben(this.resultSet);
                for ( int z = 1 ; z <= metaDaten.length ; z++ )
                {
                    String ueberschrift = metaDaten[z-1];
                    datensatz.attributHinzufuegen( ueberschrift );
                }
                System.out.println("MySqlDatensatz-Objekt erzeugt");
                this.letzteErgebnisTabelle.datensatzHinzufuegen(datensatz);
                System.out.println("MySqlDatensatz-Objekt in MySqlTabelle eingefuegt");
                
                // MySqlDatensatz-Objekt erstellen (Datensaetze)
                while ( this.resultSet.next() ) 
                {
                    System.out.println("naechste Zeile im ResultSet");
                    anzahlDatensaetze++;
                    datensatz = new MySqlDatensatz();

                    for ( int z = 1 ; z <= metaDaten.length ; z++ )
                    {
                        // AttributWert auslesen und dem Datensatz zufuegen
                        String attributWert = resultSet.getString( z );
                        System.out.println( "Attributwert:\t" + attributWert );
                        datensatz.attributHinzufuegen( attributWert );
                    }
                    System.out.println("MySqlDatensatz-Objekt erzeugt");

                    // MySqlDatensatz-Objekt der MySqlTabelle hinzufuegen
                    this.letzteErgebnisTabelle.datensatzHinzufuegen(datensatz);
                    System.out.println("MySqlDatensatz-Objekt in MySqlTabelle eingefuegt");
                }

                // Cursor des ResultSets wieder an den Anfang setzen
                this.resultSet.beforeFirst();
                System.out.println("ResultSet Cursor zurueck");
                
                geklappt_2 = true;
                this.letzteSqlFehlerMeldung = "";
            }
            catch ( SQLException e)
            {
                this.letzteSqlFehlerMeldung = e.getMessage();
                System.out.println( this.letzteSqlFehlerMeldung );
                e.printStackTrace();
                return geklappt_2;
            }

            
        }

        return geklappt_2;
    }

    
    /**
     * Nimmt ein SQL-Statement, das KEIN SELECT-Statement ist, in Form eines Strings entgegen und fuehrt es aus. 
     *
     * @param   sql   SQL-Statement
     * 
     * @return  Anzahl der betroffenen Datensaetze, -1 bei Syntaxfehler im SQL-Statement
     */
    public int sql_ohne_select(String sql)
    {
        this.letztesSqlStatement = sql;
        int geklappt = -1;

        try
        {
            System.out.println( sql );
            this.preparedStatement = this.connection.prepareStatement( sql );
            this.preparedStatement.executeUpdate();

            geklappt = this.preparedStatement.getUpdateCount();
            this.letzteSqlFehlerMeldung = "";
        }
        catch (MySQLSyntaxErrorException e) 
        {
            this.letzteSqlFehlerMeldung = "";
            System.out.println( this.letzteSqlFehlerMeldung );
            e.printStackTrace();
        }
        finally
        {
            return geklappt;
        }
    }

    
    /**
     * Gibt das letzte SQL-Statement zurueck, damit es auf Fehler untersucht werden kann. 
     *
     * @return Das letzte SQL-Statement
     */
    public String nenneLetztesSqlStatement()
    {
        return this.letztesSqlStatement;
    }

    
    /**
     * Nennt die letzte SQL-Fehlermeldung, falls das letzte SQL-Statement Fehler enthielt.
     *
     * @return  Message der letzten MySQLSyntaxErrorException, wenn das letzte SQL-Statement Fehler enthielt. 
     *          "kein Fehler im letzten SQL-Statement", wenn das letzte SQL-Statement fehlerfrei war. 
     */
    public String nenneLetzteSqlFehlerMeldung()
    {
        if ( this.letztesSqlStatementFehlerfrei )
        {
            return this.letzteSqlFehlerMeldung;
        }
        else
        {
            return "kein Fehler im letzten SQL-Statement";
        }
    }

    
    /**
     * Gibt zurueck, ob das letzte SQL-Statement fehlerfrei war. 
     *
     * @return  'true' bei fehlerfreiem letzten SQL-Statement, sonst 'false' 
     */
    public boolean nenneLetztesSqlStatementFehlerfrei()
    {
        return this.letztesSqlStatementFehlerfrei;
    }
    
    
    
    /**
     * Gibt eine Referenz auf die Ergebnis-Tabelle zurueck, falls das letzte SQL-Statement fehlerfrei war, 
     * und ansonsten eine leere Tabelle (nicht mal mit Ueberschriften). 
     *
     * @return Der Rückgabewert
     */
    public MySqlTabelle nenneLetzteErgebnisTabelle()
    {
        if ( this.letztesSqlStatementFehlerfrei )
        {
            return this.letzteErgebnisTabelle;
        }
        else
        {
            return new MySqlTabelle();
        }
    }
    
    
    
    /**
     * Laedt den JDBC-Treiber fuer MySQL. 
     * 
     * @return  'true', wenn der Treiber geladen werden konnte, sonst 'false' 
     *
     */
    private boolean datenbankTreiberLaden()
    {
        // Laedt den MySQL-Treiber. Jedes Datenbank-System hat seinen eigenen Treiber
        boolean geklappt = false;

        try 
        {
            System.out.println( "Datenbank-Treiber geladen" );
            Class.forName( "com.mysql.jdbc.Driver" );

            geklappt = true;
        }
        catch (Exception e) 
        {
            System.out.println( e.getMessage() );
            e.printStackTrace();
        } 
        finally
        {
            return geklappt;
        }
    }

    
    /**
     * Gibt die Meta-Daten eines ResultSets aus.
     *
     * @param   resultSet   Referenz auf das ResultSet
     * 
     * @return  Array mit den Spalten-Namen. 
     *          Bei gescheitertem SQL-Statement ein Array der Laenge 1 mit dem Text: 
     *          "SQL-Statement lieferte kein Resultat zurueck!"
     */
    private String[] metaDataAusgeben(ResultSet resultSet)
    {
        String[] spaltenNamen;

        try
        {
            int anzahlSpalten = this.resultSet.getMetaData().getColumnCount();
            spaltenNamen = new String[ anzahlSpalten ];

            for  ( int i = 1 ; i<= anzahlSpalten ; i++ )
            {
                spaltenNamen[i-1] = this.resultSet.getMetaData().getColumnName(i);
                System.out.println( "Spalten-Name\t" + spaltenNamen[i-1] );
            }
            
            return spaltenNamen;
        }
        catch (SQLException e)
        {
            System.out.println( e.getMessage() );
            e.printStackTrace();
            spaltenNamen = new String[1];
            spaltenNamen[0] = "SQL-Statement lieferte kein Resultat zurueck!";
            
            return spaltenNamen;
        }
    }


    /**
     * Beendet die Verbindung zum Server
     *
     */
    public void verbindungBeenden() 
    {
        try 
        {
            System.out.println( "Verbindung beendet" );

            if (resultSet != null) 
            {
                resultSet.close();
            }

            if (connection != null) 
            {
                connection.close();
            }
        } 
        catch (Exception e) 
        {
            System.out.println( "Verbindung beenden GESCHEITERT" );
        }
    }

    
    
    /**
     * Nur zum lokalen Testen. Verbindet mit 'localhost' auf Port 3306 mit der Datenbank 'test'. 
     * Der Benutzer ist 'root' und das root-Passwort 'abc123'.
     *
     */
    public void verbinden_test()
    {
        try
        {
            this.verbindeMitServer("localhost", "test", "root", "abc123");
        }
        catch (Exception e)
        {

        }
    }

} 
