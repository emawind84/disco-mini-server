package pmis.common.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.apache.log4j.Logger;

public class Errors {

	private final static Logger logger = Logger.getLogger(Errors.class);
	
	public static void lower( Throwable throwable ) {
		throw wrap( throwable );
	}

	public static RuntimeException wrap( Throwable throwable ) {
		logger.debug("LOWER: ", throwable);
		if( throwable instanceof RuntimeException ) {
			return (RuntimeException)throwable;
		} else {
			RuntimeException ex = new RuntimeException( throwable );
			ex.fillInStackTrace();
			return ex;
		}
	}

	public static Throwable unwrapThrowable( Throwable throwable ) {
		return unfoldThrowable( throwable )[0];
	}

	public static Exception unwrap ( Throwable throwable ) {
		return convert( unwrapThrowable( throwable ) );
	}
	
	public static Throwable[] unfoldThrowable( Throwable throwable ) {

		List<Throwable> folds = new ArrayList<Throwable>();
		Throwable result = throwable;
		folds.add( 0, result );
		
		while ( result instanceof InvocationTargetException || 
				result instanceof UndeclaredThrowableException || 
				result.getCause() != null ) {

			if ( result instanceof InvocationTargetException ) {
				result = ( (InvocationTargetException) result ).getCause();
				
			} else if ( result instanceof UndeclaredThrowableException ) {
				result = ( (UndeclaredThrowableException) result ).getUndeclaredThrowable();

			} else {
				result = result.getCause();
			}
			folds.add( 0, result );
		}
		return folds.toArray( new Throwable[ folds.size() ] );
		
	}
	
	public static Exception[] unfold( Throwable throwable) {
		Throwable[] throwables = unfoldThrowable( throwable );
		Exception[] exceptions = new Exception[ throwables.length ];
		for( int i = 0; i < throwables.length; i++ ) {
			exceptions[ i ] = convert( throwables[ i ] );
		}
		return exceptions;
	}
	
	public static Exception convert( Throwable throwable ) {
		if ( throwable instanceof Exception ) {
			return (Exception) throwable;
		} else {
			return new Exception( throwable );
		}
	}
	
	public static void printf( String message, Object... args ) {
		throw message( message, args );
	}

	public static RuntimeException message( String message, Object... args ) {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter( builder );
		formatter.format( message, args );
		return new RuntimeException( builder.toString() );
		
	}
	
}
