package com.example;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import net.teufel.sidplay.core.dao.DataSourceFactory;
import net.teufel.sidplay.core.dao.SidDaoJdbc;
import net.teufel.sidplay.core.domain.Type;

public class HelloWorld extends AbstractEntryPoint {
    public void createContents( Composite parent ) {
    	
    	SidDaoJdbc sidDao = new SidDaoJdbc(DataSourceFactory.erzeugeDataSource());
    	
    	String text = "";
    	for (Type type : sidDao.getTypes()) {
			text = text + " " + type.getType();
		}
    	
        Label label = new Label( parent, SWT.NONE );
        label.setText( text);
    }
}