import java.util.ArrayList;

public class Test extends MySqlZugriff 
{
    
    MySqlTabelle all;
    MySqlTabelle pop;
    MySqlTabelle rock;

    
    public Test() 
    {
        super();
        super.verbindeMitServer( "localhost" , "enginealpha" , "enginealpha" , "eatest" );
    }
    
    
    public void selectAll()
    {
        super.sql_select( "SELECT * FROM music" );
        all = super.nenneLetzteErgebnisTabelle();
    }
    
    
    public void getPop()
    {
        super.sql_select( "SELECT * FROM music WHERE genre LIKE '%pop%'" );
        pop = super.nenneLetzteErgebnisTabelle();   
    }
    
    
    public void getRock()
    {
        super.sql_select("SELECT * FROM music WHERE genre LIKE '%rock%'");
        rock = super.nenneLetzteErgebnisTabelle();   
    }
    
    
    public void printRock()
    {
        System.out.println( rock.gibSpaltenbezeichnungen() );
        ArrayList<MySqlDatensatz> rockZeilen = rock.nenneZeilen();
        
        for ( int i = 0 ; i<rock.nenneAnzahlDatensaetze() ; i++ )
        {
            System.out.println( rockZeilen.get(i).datensatzAlsString() );
        }
    }
    

}
