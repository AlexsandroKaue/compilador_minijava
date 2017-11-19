import Scanner.*;
import Semantics.ClassSymbolTable;
import Parser.*;
import AST.*;
import AST.Visitor.*;
import Throwables.*;
import java_cup.runtime.Symbol;

import java.util.*;

public class TestParser {
    public static void main(String [] args) {
        try {
            // create a scanner on the input file
            scanner s = new scanner(System.in);
            parser p = new parser(s);
            Symbol root;
            root = p.parse();
            /*List<Statement> program = (List<Statement>)root.value;
            for (Statement statement: program) {
                statement.accept(new PrettyPrintVisitor());
				System.out.print("\n");
            }*/
            Program program = (Program)root.value;
            ClassSymbolTable main = new ClassSymbolTable();
            //program.accept(new BuildClassSymbolTable(main));
           //program.accept(new TypeCheckVisitor(main));
            program.accept(new PrettyPrintVisitor());
 			System.out.print("\n");
            System.out.print("\nParsing successfull"); 
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