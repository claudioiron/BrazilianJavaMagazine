package br.com.javamagazine.client.gui;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.ws.rs.InternalServerErrorException;

import br.com.javamagazine.client.exceptions.AgendaServerException;


public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Component parent;

    public ExceptionHandler(){}

    public ExceptionHandler(final Component parentComponent){
        this.parent = parentComponent;
    }

    public static void setUncaughtExceptionHandler(final Component parent) {
        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler(parent));
    }

    @Override
	public void uncaughtException(final Thread t, final Throwable e) {
        handleException(parent, e);
    }

    public static void handleException(final Component parent,  final Throwable e) {
        final StringWriter result = new StringWriter();
        e.printStackTrace(new PrintWriter(result));
        e.printStackTrace();

        if(e instanceof AgendaServerException || e instanceof InternalServerErrorException) {
            JOptionPane.showMessageDialog(parent, e.getMessage(), "Mensagem de erro proveniente do servidor", JOptionPane.ERROR_MESSAGE);
        	return;
        }

        JOptionPane.showMessageDialog(parent, e.getMessage(), "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
    }

}
