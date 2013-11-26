package uk.co.techblue.cgh.dnap.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;

import uk.co.techblue.cgh.dnap.progressobserver.ProgressObserver.LogLevel;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgressConsole.
 * 
 * @author dheeraj
 */
public class ProgressConsole extends Composite {

    /** The txt logs. */
    private Text txtLogs;

    /**
     * Instantiates a new progress console.
     * 
     * @param parent the parent
     * @param style the style
     */
    public ProgressConsole(final Composite parent, final int style) {
        super(parent, style);
        setLayout(new FillLayout(SWT.HORIZONTAL));
        txtLogs = new Text(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
        txtLogs.setEditable(false);
    }

    /**
     * Insert text.
     * 
     * @param message the message
     * @param logLevel the log level
     */
    public void insertText(final String message, final LogLevel logLevel) {
        getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String logs = txtLogs.getText() + formatter.format(new Date()) + " " + logLevel.name() + ": " + message
                        + "\r\n";
                txtLogs.setText(logs);
                txtLogs.setTopIndex(txtLogs.getText().length());
            }
        });
    }

    /**
     * Clear.
     */
    public void clear() {
        getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                txtLogs.setText("");
            }
        });
    }

}
