/** This class is used to throw errors during compilation. */

package Throwables;

public class SemanticsException extends RuntimeException{
	/** A standard interface for reporting an error */
    public SemanticsException(String message) {
	super(message);
    }

    /** An interface to use if the line of the input where the error
     * occurs is known */
    public SemanticsException(String message, int line) {
	super("line " + line + ": " + message);
    }

    /** An interface to use if the line and column of the input where
     * the error occurs is known */
    public SemanticsException(String message, int line, int column) {
	super("line " + line + ", column " + column + ": " + message);
    }

}
