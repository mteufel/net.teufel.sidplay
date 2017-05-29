package com.example;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class HelloWorld extends AbstractEntryPoint {
    public void createContents( Composite parent ) {
        Label label = new Label( parent, SWT.NONE );
        label.setText( "Hello RAP World!!!" );
    }
}