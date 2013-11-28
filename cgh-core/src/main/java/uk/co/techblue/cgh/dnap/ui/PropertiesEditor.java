package uk.co.techblue.cgh.dnap.ui;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.DIALOG_TITLE;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.techblue.cgh.dnap.annotation.Property;
import uk.co.techblue.cgh.dnap.configuration.IConfiguration;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;
import uk.co.techblue.cgh.dnap.util.SignalProcessorHelper;
import uk.co.techblue.cgh.dnap.util.UIHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesEditor.
 * 
 * @author dheeraj
 */
public class PropertiesEditor extends Composite {

    /** The table. */
    private Table table;

    /** The configuration. */
    private IConfiguration configuration;

    /** The Constant EDITABLECOLUMN. */
    private static final int EDITABLECOLUMN = 1;
    
    private String keyHeaderName;
    
    private String valueHeaderName;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Create the composite.
     * 
     * @param parent the parent
     * @param style the style
     * @param configuration the configuration
     */
    public PropertiesEditor(final Composite parent, final int style, final IConfiguration configuration, String keyColumnName, String valueColumnName) {
        super(parent, style);
        this.configuration = configuration;
        this.keyHeaderName = keyColumnName;
        this.valueHeaderName = valueColumnName;
        initGui();
        try {
            populateConfigurations();
        } catch (CGHProcessorException cghe) {
            logger.error("", cghe);
            MessageDialog.openError(getShell(), DIALOG_TITLE, cghe.getLocalizedMessage());
        }
    }

    /**
     * Inits the gui.
     */
    private void initGui() {
        setLayout(new GridLayout(1, false));
        UIHelper.createHeader(this, "Configuration Editor");
        createBody();
        createFooter(UIHelper.createFooter(this));
    }
    
    /**
     * Populate configurations.
     * 
     * @throws CGHProcessorException the cGH processor exception
     */
    public void populateConfigurations() throws CGHProcessorException {
        Properties props = null;
        props = SignalProcessorHelper.getProperties(configuration.getClass());
        for (final Field classField : configuration.getClass().getDeclaredFields()) {
            if (classField.isAnnotationPresent(Property.class)) {
                final Property property = classField.getAnnotation(Property.class);
                final String propertyName = property.name();
                final String propertyDisplayName = property.displayName();
                final String propertyValue = props.getProperty(propertyName);
                TableItem item = new TableItem(table, SWT.NONE);
                item.setData(propertyName);
                item.setData("displayName", propertyDisplayName);
                item.setText(new String[] { propertyDisplayName, propertyValue });
            }
        }
    }

    /**
     * Creates the footer.
     * 
     * @param cmpFooter the cmp footer
     */
    private void createFooter(final Composite cmpFooter) {
        Button btnSave = new Button(cmpFooter, SWT.NONE);
        btnSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String errorMessage = validateConfiguration();
                if(StringUtils.isNotBlank(errorMessage)) {
                    MessageDialog.openError(getShell(), DIALOG_TITLE, errorMessage);
                    return;
                }
                saveConfigurations();
                cmpFooter.getShell().close();
            }
        });
        btnSave.setText("Save");

        Button btnStartWatcherService = new Button(cmpFooter, SWT.NONE);
        btnStartWatcherService.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                cmpFooter.getShell().close();
            }
        });
        btnStartWatcherService.setText("Cancel");
    }

    private String validateConfiguration() {
        String message = "";
        TableItem[] tableItems = table.getItems();
        for (final TableItem tableItem : tableItems) {
            if (StringUtils.isBlank(tableItem.getText(EDITABLECOLUMN))) {
                message += "Invalid value for field '" +tableItem.getData("displayName").toString() + "' \n";
            }
        }
        return message;
    }

    /**
     * Save configurations.
     */
    protected void saveConfigurations() {
        Properties configurationProperties = new Properties();
        TableItem[] tableItems = table.getItems();
        for (final TableItem tableItem : tableItems) {
            configurationProperties.setProperty(tableItem.getData().toString(), tableItem.getText(EDITABLECOLUMN));
        }
        try {
            SignalProcessorHelper.storePropertiesFile(configurationProperties, configuration);
        } catch (IOException ioe) {
            logger.error("An error occurred while saving the configuration.", ioe);
            MessageDialog.openError(getShell(), DIALOG_TITLE, "An error occurred while saving the configuration.");
        }
    }

    /**
     * Creates the body.
     */
    protected void createBody() {
        table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
        GridData gd_table = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_table.heightHint = 272;
        gd_table.widthHint = 500;
        table.setLayoutData(gd_table);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn tblclmnKey = new TableColumn(table, SWT.NONE);
        tblclmnKey.setWidth(250);
        tblclmnKey.setText(keyHeaderName);

        TableColumn tblclmnValue = new TableColumn(table, SWT.NONE);
        tblclmnValue.setWidth(250);
        tblclmnValue.setText(valueHeaderName);

        final TableEditor editor = new TableEditor(table);
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;
        editor.minimumWidth = 50;

        table.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Control oldEditor = editor.getEditor();
                if (oldEditor != null)
                    oldEditor.dispose();

                TableItem item = (TableItem) e.item;
                if (item == null)
                    return;

                Text editableItemValue = new Text(table, SWT.NONE);
                editableItemValue.setText(item.getText(EDITABLECOLUMN));
                editableItemValue.addModifyListener(new ModifyListener() {
                    public void modifyText(ModifyEvent me) {
                        Text text = (Text) editor.getEditor();
                        editor.getItem().setText(EDITABLECOLUMN, text.getText());
                    }
                });
                editableItemValue.selectAll();
                editableItemValue.setFocus();
                editor.setEditor(editableItemValue, item, EDITABLECOLUMN);
            }
        });
    }
}
