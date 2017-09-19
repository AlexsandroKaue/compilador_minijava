import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import Scanner.*;
import Parser.sym;
import Throwables.*;
import java_cup.runtime.Symbol;

public class TestScanner {
    public static void main(String [] args) {
        try {
            // create a scanner on the input file
        	InputStream is = new FileInputStream(new File("origem.txt"));
        	OutputStream os = new FileOutputStream("destino.txt");
            OutputStreamWriter osw = new OutputStreamWriter(os);
        	BufferedWriter bw = new BufferedWriter(osw);
        	scanner s;
        	s = new scanner(System.in);
        	s = new scanner(is); /*Descomente esta linha caso queira fornecer um arquivo como entrada*/
                Symbol t = s.next_token();
                while (t.sym != sym.EOF){ 
                    // print each token that we scan
                    System.out.print(s.symbolToString(t) + " ");
                    bw.write(s.symbolToString(t));
                    bw.write(" ");
                    t = s.next_token(); 
                }
                bw.close();
            System.out.print("\nLexical analysis successfull"); 
        } catch (CompilerException e) {
            // an error in the user's arguments or input, or some
            // other kind of error that we've already handled in the
            // appropriate way (not a bug that the error got here)
            System.err.println(e.getMessage());
        } catch (Exception e) {
            // yuck: some kind of error in the compiler implementation
            // that we're not expecting (a bug!)
            System.err.println("Unexpected internal compiler error: " + 
                        e.toString());
            // print out a stack dump
            e.printStackTrace();
        }
   }
}


