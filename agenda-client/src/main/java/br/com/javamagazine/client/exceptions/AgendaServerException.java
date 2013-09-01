package br.com.javamagazine.client.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

import br.com.javamagazine.client.AgendaError;

public class AgendaServerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final AgendaError error;

    public AgendaServerException(final AgendaError error) {
        super(error.getMessage());
        this.error = error;
    }

    @Override
    public void printStackTrace(final PrintStream s) {
        s.println("Client stack trace: ");
        super.printStackTrace(s);
        s.println("Server stack trace: ");
        printServerStackTrace(s);
    }

    @Override
    public void printStackTrace(final PrintWriter s) {
        s.println("Server stack trace: ");
        super.printStackTrace(s);
        s.println("Server stack trace: ");
        printServerStackTrace(s);
    }

    public void printServerStackTrace() {
        printServerStackTrace(System.err);
    }

    private void printServerStackTrace(final PrintWriter s) {
        s.println(this.error.getStackTrace());
    }

    private void printServerStackTrace(final PrintStream s) {
        s.println(this.error.getStackTrace());
    }
}
