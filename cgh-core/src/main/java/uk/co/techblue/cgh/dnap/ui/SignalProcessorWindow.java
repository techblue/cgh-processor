package uk.co.techblue.cgh.dnap.ui;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.APPLICATION_LOG_FILE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.ARCHIVE_DIR_PATH;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.CGH_LOG_PATH;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.DB_PASSWORD;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.DIALOG_TITLE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.TOGGLE_STATE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.WATCH_DIR_PATH;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.techblue.cgh.dnap.annotation.Property;
import uk.co.techblue.cgh.dnap.configuration.CsvConfiguration;
import uk.co.techblue.cgh.dnap.configuration.DbConfiguration;
import uk.co.techblue.cgh.dnap.configuration.GeneralConfiguration;
import uk.co.techblue.cgh.dnap.configuration.IConfiguration;
import uk.co.techblue.cgh.dnap.configuration.SystemConfiguration;
import uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants;
import uk.co.techblue.cgh.dnap.db.DatabaseSchemaSynchronizer;
import uk.co.techblue.cgh.dnap.db.connection.ConnectionProviderImpl;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;
import uk.co.techblue.cgh.dnap.progressobserver.ProgressObserver;
import uk.co.techblue.cgh.dnap.progressobserver.UIProgressObserver;
import uk.co.techblue.cgh.dnap.util.SignalProcessorHelper;
import uk.co.techblue.cgh.dnap.util.UIHelper;
import uk.co.techblue.cgh.dnap.watcher.WatcherService;

// TODO: Auto-generated Javadoc
/**
 * The Class SignalProcessorWindow.
 * 
 * @author dheeraj
 */
public class SignalProcessorWindow extends ApplicationWindow {

    /** The txt watch dir. */
    private Text txtWatchDir;

    /** The txt archive dir. */
    private Text txtArchiveDir;

    /** The grp database settings. */
    private Group grpDatabaseSettings;

    /** The watcher service. */
    private WatcherService watcherService;

    /** The toggle state. */
    private static boolean toggleState;

    /** The ui progress observer. */
    private ProgressObserver uiProgressObserver;

    private Button btnStopWatcherService;

    private Button btnStartWatcherService;

    private Button btnApplyChanges;

    private Button btnArchivedFolder;

    private Button btnWatchedDirectory;

    private Button btnCsvColumnSettings;

    private Button btnGeneralSettings;

    private Button btnValidateConnection;

    private Button btnUpdateSchema;
    
    private Tray tray = null;
    
    private TrayItem item = null;
    
    private Menu menu = null;
    
    private MenuItem openMenuItem = null;
    
    private MenuItem exitMenuItem = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(SignalProcessorWindow.class);

    /**
     * Create the application window.
     */
    public SignalProcessorWindow() {
        super(null);
        addMenuBar();
        try {
            this.watcherService = new WatcherService(2 * 1000);
        } catch (CGHProcessorException cghe) {
            LOGGER.error("", cghe);
            MessageDialog.openError(getShell(), DIALOG_TITLE, cghe.getLocalizedMessage());
        }
    }

    /**
     * Create contents of the application window.
     * 
     * @param parent the parent
     * @return the control
     */
    @Override
    protected Control createContents(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NONE);

        GridLayout containerLayout = new GridLayout();
        containerLayout.marginHeight = 0;
        containerLayout.marginWidth = 0;
        containerLayout.horizontalSpacing = 0;

        container.setBackground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
        container.setLayout(containerLayout);

        UIHelper.createHeader(container, DIALOG_TITLE);

        createBody(container);

        createFooter(UIHelper.createFooter(container));

        configureShellClosedListener();

        changeControlState(true);

        return container;
    }

    /**
     * Configure shell closed listener.
     */
    private void configureShellClosedListener() {
        final Shell shell = getShell();
        shell.addShellListener(new ShellAdapter() {
            @Override
            public void shellClosed(ShellEvent e) {
                CloseActionDialog closeDialog = new CloseActionDialog(getShell(), toggleState);
                if (closeDialog.open() == 0) {
                    toggleState = closeDialog.isMinimizeToTray();
                    saveSystemConfiguration();
                    if (toggleState) {
                        e.doit = false;
                        configureTrayItem(getShell());
                    } else {
                        FileUtils.deleteQuietly(new File(FileUtils.getUserDirectoryPath(),
                                SignalProcessorConstants.APP_RUNNING_INDICATOR));
                        System.exit(0);
                    }
                }
            }
        });
    }

    /**
     * Configure progress console.
     * 
     * @param parent the parent
     */
    private void configureProgressConsole(final Composite parent) {
        CTabFolder progressTabFolder = new CTabFolder(parent, SWT.BORDER | SWT.FLAT | SWT.SINGLE);
        progressTabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        progressTabFolder.setSingle(true);
        progressTabFolder.setSimple(false);
        progressTabFolder.setTabHeight(22);
        final ProgressConsole progressConsole = new ProgressConsole(progressTabFolder, SWT.BORDER);
        progressConsole.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        GridData gd_progressLogWindow = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
        progressTabFolder.setLayoutData(gd_progressLogWindow);
        CTabItem progressTabItem = new CTabItem(progressTabFolder, SWT.NONE);
        progressTabItem.setText("Progress Logs");
        progressTabItem.setControl(progressConsole);
        progressTabFolder.setFocus();
        uiProgressObserver = new UIProgressObserver(progressConsole);
    }

    /**
     * Configure tray item.
     * 
     * @param shell the shell
     */
    private void configureTrayItem(final Shell shell) {
        ToolTip tip = new ToolTip(shell, SWT.BALLOON | SWT.ICON_INFORMATION);
        Image image;
        if (tray == null) {
            LOGGER.info("The system tray is not available");
        } else {
            tip.setMessage(DIALOG_TITLE + " is minimised to system tray.");
            tip.setText("Notification");
            item.setToolTip(tip);
            item.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    shell.setVisible(true);
                    shell.setFocus();

                }
            });
            
            openMenuItem.setText("Open");
            openMenuItem.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event event) {
                    shell.setVisible(true);
                    shell.setFocus();
                }
            });
            exitMenuItem.setText("Exit");
            exitMenuItem.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event event) {
                    FileUtils.deleteQuietly(new File(FileUtils.getUserDirectoryPath(),
                            SignalProcessorConstants.APP_RUNNING_INDICATOR));
                    System.exit(0);
                }
            });

            item.addListener(SWT.MenuDetect, new Listener() {
                public void handleEvent(Event event) {
                    menu.setVisible(true);
                }
            });

            image = SWTResourceManager.getImage(SignalProcessorWindow.class, "/images/favicon.ico");
            item.setImage(image);
            item.setToolTipText("CGH Array processor running");
        }
        tip.setVisible(true);
        shell.setVisible(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.ApplicationWindow#createMenuManager()
     */
    protected MenuManager createMenuManager() {
        MenuManager menuBar = new MenuManager("");
        MenuManager fileMenu = new MenuManager("File");
        MenuManager viewMenu = new MenuManager("View");
        MenuManager helpMenu = new MenuManager("Help");
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        fileMenu.add(new ExitAction());
        viewMenu.add(new ViewLogFileAction());
        helpMenu.add(new AboutAction());
        return menuBar;
    }

    /**
     * The Class ExitAction.
     */
    private class ExitAction extends Action {

        /**
         * Instantiates a new exit action.
         */
        public ExitAction() {
            setText("Exit");
            setToolTipText("Exit the application");
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.action.Action#run()
         */
        public void run() {
            CloseActionDialog closeDialog = new CloseActionDialog(getShell(), toggleState);
            if (closeDialog.open() == 0) {
                toggleState = closeDialog.isMinimizeToTray();
                saveSystemConfiguration();
                if (toggleState) {
                    configureTrayItem(getShell());
                } else {
                    FileUtils.deleteQuietly(new File(FileUtils.getUserDirectoryPath(),
                            SignalProcessorConstants.APP_RUNNING_INDICATOR));
                    System.exit(0);
                }
            }
        }
    }

    /**
     * The Class ViewLogFileAction.
     */
    private class ViewLogFileAction extends Action {

        /**
         * Instantiates a new view log file action.
         */
        public ViewLogFileAction() {
            setText("View Logs");
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.action.Action#run()
         */
        public void run() {
            Program.launch(FileUtils.getTempDirectoryPath() + CGH_LOG_PATH);
        }
    }

    /**
     * The Class AboutAction.
     */
    private class AboutAction extends Action {

        /**
         * Instantiates a new about action.
         */
        public AboutAction() {
            setText("About");
            setToolTipText("About Signal extration Processor");
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.action.Action#run()
         */
        public void run() {
            openAboutDialog();
        }
    }

    /**
     * Open about dialog.
     */
    private void openAboutDialog() {
        new AboutDialog(getShell()).open();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.ApplicationWindow#canHandleShellCloseEvent()
     */
    protected boolean canHandleShellCloseEvent() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.ApplicationWindow#close()
     */
    public boolean close() {
        return false;
    }

    /**
     * Creates the footer.
     * 
     * @param cmpFooter the cmp footer
     */
    private void createFooter(Composite cmpFooter) {
        btnApplyChanges = new Button(cmpFooter, SWT.NONE);
        btnApplyChanges.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String errorMessage = validateInput();
                if (StringUtils.isNotBlank(errorMessage)) {
                    MessageDialog.openError(getShell(), DIALOG_TITLE, errorMessage);
                    return;
                }
                saveConfigurations();
            }
        });
        btnApplyChanges.setText("Apply Changes");

        btnStartWatcherService = new Button(cmpFooter, SWT.NONE);
        btnStartWatcherService.setText("Start Watcher Service");
        btnStartWatcherService.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    String errorMessage = validateInput();
                    if (StringUtils.isNotBlank(errorMessage)) {
                        MessageDialog.openError(getShell(), DIALOG_TITLE, errorMessage);
                        return;
                    }
                    changeControlState(false);
                    watcherService.setProgressObserver(uiProgressObserver);
                    watcherService.startWatchService(txtWatchDir.getText());
                } catch (CGHProcessorException cghe) {
                    LOGGER.error("", cghe);
                    MessageDialog.openError(getShell(), DIALOG_TITLE, cghe.getLocalizedMessage());
                }
            }
        });

        btnStopWatcherService = new Button(cmpFooter, SWT.NONE);
        btnStopWatcherService.setText("Stop Watcher Service");
        btnStopWatcherService.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    watcherService.stopWatchService();
                    changeControlState(true);
                } catch (CGHProcessorException cghe) {
                    LOGGER.error("", cghe);
                    MessageDialog.openError(getShell(), DIALOG_TITLE, cghe.getLocalizedMessage());
                }
            }
        });
    }

    private void changeControlState(boolean enable) {
        btnWatchedDirectory.setEnabled(enable);
        btnArchivedFolder.setEnabled(enable);
        btnCsvColumnSettings.setEnabled(enable);
        btnGeneralSettings.setEnabled(enable);
        Control[] controls = grpDatabaseSettings.getChildren();
        for (Control control : controls) {
            if (control.getClass().equals(Text.class)) {
                control.setEnabled(enable);
            }
        }
        btnValidateConnection.setEnabled(enable);
        btnApplyChanges.setEnabled(enable);
        btnStartWatcherService.setEnabled(enable);
        btnStopWatcherService.setEnabled(!enable);
        btnUpdateSchema.setEnabled(enable);
    }

    private String validateInput() {
        return validateDbConfiguration() + validateSystemConfiguration();
    }

    private String validateDbConfiguration() {
        String message = "";
        Control[] controls = grpDatabaseSettings.getChildren();
        for (Control control : controls) {
            if (control.getClass().equals(Text.class)) {
                if (StringUtils.isBlank(((Text) control).getText())) {
                    message += "Invalid value for field, '" + control.getData("displayName").toString() + "' \n";
                }
            }
        }
        return message;
    }

    private String validateSystemConfiguration() {
        String message = "";
        if (StringUtils.isBlank(txtWatchDir.getText())) {
            message = "Invalid path of watch directory \n";
        }
        if (StringUtils.isBlank(txtArchiveDir.getText())) {
            message += "Invalid path of archive directory \n";
        }
        return message;
    }

    /**
     * Creates the body.
     * 
     * @param container the container
     */
    private void createBody(final Composite container) {

        Composite cmpBody = new Composite(container, SWT.BORDER);
        cmpBody.setLayout(new GridLayout());
        cmpBody.setLayoutData(new GridData(GridData.FILL_BOTH));
        cmpBody.setBackground(container.getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Composite cmpDirDialogs = new Composite(cmpBody, SWT.NONE);
        cmpDirDialogs.setLayout(new GridLayout(3, false));
        GridData gd_cmpDirDialogs = new GridData(GridData.FILL_HORIZONTAL);
        cmpDirDialogs.setLayoutData(gd_cmpDirDialogs);
        cmpDirDialogs.setBackground(cmpBody.getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
        cmpDirDialogs.setBackgroundMode(SWT.INHERIT_DEFAULT);

        Label lblWatchedFolder = new Label(cmpDirDialogs, SWT.NONE);
        lblWatchedFolder.setText("Watch Folder: ");

        txtWatchDir = new Text(cmpDirDialogs, SWT.BORDER);
        txtWatchDir.setEnabled(false);
        txtWatchDir.setEditable(false);
        txtWatchDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        btnWatchedDirectory = new Button(cmpDirDialogs, SWT.NONE);
        btnWatchedDirectory.setImage(SWTResourceManager.getImage(SignalProcessorWindow.class, "/images/settings.ico"));
        btnWatchedDirectory.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                txtWatchDir.setText(StringUtils.trimToEmpty(openDirectoryDialog(container.getShell(), txtWatchDir.getText())));
            }
        });

        Label lblArchivedFolder = new Label(cmpDirDialogs, SWT.NONE);
        lblArchivedFolder.setText("Archive Folder: ");

        txtArchiveDir = new Text(cmpDirDialogs, SWT.BORDER);
        txtArchiveDir.setEnabled(false);
        txtArchiveDir.setEditable(false);
        txtArchiveDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        btnArchivedFolder = new Button(cmpDirDialogs, SWT.NONE);
        btnArchivedFolder.setImage(SWTResourceManager.getImage(SignalProcessorWindow.class, "/images/settings.ico"));
        btnArchivedFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                txtArchiveDir.setText(StringUtils.trimToEmpty(openDirectoryDialog(container.getShell(), txtArchiveDir.getText())));
            }
        });

        final Composite cmpPropertiesControls = new Composite(cmpBody, SWT.NONE);
        GridLayout gl_cmpPropertiesControls = new GridLayout(2, false);
        cmpPropertiesControls.setLayout(gl_cmpPropertiesControls);
        GridData gd_cmpPropertiesControls = new GridData(GridData.FILL_HORIZONTAL);
        cmpPropertiesControls.setLayoutData(gd_cmpPropertiesControls);
        cmpPropertiesControls.setBackground(container.getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Label lblCsvColumnName = new Label(cmpPropertiesControls, SWT.NONE);
        lblCsvColumnName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblCsvColumnName.setText("Click on the settings button on the right to change CSV column name mappings ");
        lblCsvColumnName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnCsvColumnSettings = new Button(cmpPropertiesControls, SWT.NONE);
        btnCsvColumnSettings.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IConfiguration configuration = new CsvConfiguration();
                openPropertiesEditor(configuration, "Field", "Column name in TSV file");
            }
        });
        btnCsvColumnSettings.setImage(SWTResourceManager.getImage(SignalProcessorWindow.class, "/images/settings.ico"));

        Label lblGeneralSettings = new Label(cmpPropertiesControls, SWT.NONE);
        lblGeneralSettings.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblGeneralSettings.setText("Click on the settings button on the right to change general properties ");
        lblGeneralSettings.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        btnGeneralSettings = new Button(cmpPropertiesControls, SWT.NONE);
        btnGeneralSettings.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IConfiguration configuration = new GeneralConfiguration();
                openPropertiesEditor(configuration, "Field", "Name");
            }
        });
        btnGeneralSettings.setImage(SWTResourceManager.getImage(SignalProcessorWindow.class, "/images/settings.ico"));

        populateSystemConfiguration();

        createDbSettingControls(cmpBody);

        configureProgressConsole(cmpBody);
    }

    /**
     * Creates the db setting controls.
     * 
     * @param cmpBody the cmp body
     */
    protected void createDbSettingControls(final Composite cmpBody) {
        grpDatabaseSettings = new Group(cmpBody, SWT.NONE);
        grpDatabaseSettings.setText("Database Configuration");
        GridLayout gl_grpDatabaseSettings = new GridLayout(2, false);
        grpDatabaseSettings.setLayout(gl_grpDatabaseSettings);
        GridData gd_grpDatabaseSettings = new GridData(GridData.FILL_HORIZONTAL);
        grpDatabaseSettings.setLayoutData(gd_grpDatabaseSettings);
        grpDatabaseSettings.setBackground(cmpBody.getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));

        populateDbConfiguration();

        btnValidateConnection = new Button(grpDatabaseSettings, SWT.PUSH);
        btnValidateConnection.setText("Validate Settings");
        btnValidateConnection.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String message = validateDbConfiguration();
                if (StringUtils.isNotBlank(message)) {
                    MessageDialog.openError(getShell(), DIALOG_TITLE, message);
                    return;
                }
                checkDbConnection();
            }

        });

        btnUpdateSchema = new Button(grpDatabaseSettings, SWT.PUSH);
        btnUpdateSchema.setText("Update Schema");        
        btnUpdateSchema.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String message = validateDbConfiguration();
                if (StringUtils.isNotBlank(message)) {
                    MessageDialog.openError(getShell(), DIALOG_TITLE, message);
                    return;
                }
                saveDbConfiguration();
                updateSchema(getShell(), true);
            }

        });
    }

    private void checkDbConnection() {
        saveDbConfiguration();
        Connection connection = null;
        try {
            connection = ConnectionProviderImpl.getInstance().acquire();
            if (connection != null && !connection.isClosed()) {
                MessageDialog.openInformation(getShell(), DIALOG_TITLE,
                        "DB Configurations are valid. Connection established successfully.");
            }
        } catch (Exception exception) {
            LOGGER.error("", exception);
            MessageDialog.openError(getShell(), DIALOG_TITLE, exception.toString());
        } finally {
            ConnectionProviderImpl.getInstance().release(connection);
        }
    }

    /**
     * Populate system configuration.
     */
    private void populateSystemConfiguration() {
        Properties props = null;
        try {
            props = SignalProcessorHelper.getProperties(SystemConfiguration.class);
        } catch (CGHProcessorException cghe) {
            LOGGER.error("", cghe);
            MessageDialog.openError(getShell(), DIALOG_TITLE, cghe.getLocalizedMessage());
        }

        if (props == null || props.isEmpty()) {
            return;
        }

        for (final Field classField : SystemConfiguration.class.getDeclaredFields()) {
            if (classField.isAnnotationPresent(Property.class)) {
                final Property property = classField.getAnnotation(Property.class);
                final String propertyName = property.name();
                if (WATCH_DIR_PATH.equals(propertyName)) {
                    txtWatchDir.setText(props.getProperty(WATCH_DIR_PATH));
                } else if (ARCHIVE_DIR_PATH.equals(propertyName)) {
                    txtArchiveDir.setText(props.getProperty(ARCHIVE_DIR_PATH));
                } else if (TOGGLE_STATE.equals(propertyName)) {
                    toggleState = BooleanUtils.toBoolean(props.getProperty(TOGGLE_STATE));
                }
            }
        }
    }

    /**
     * Populate db configuration.
     */
    private void populateDbConfiguration() {
        Properties props = null;
        try {
            props = SignalProcessorHelper.getProperties(DbConfiguration.class);
        } catch (CGHProcessorException cghe) {
            LOGGER.error("", cghe);
            MessageDialog.openError(getShell(), DIALOG_TITLE, cghe.getLocalizedMessage());
        }

        for (final Field classField : DbConfiguration.class.getDeclaredFields()) {
            if (classField.isAnnotationPresent(Property.class)) {
                final Property property = classField.getAnnotation(Property.class);
                final String propertyName = property.name();
                final boolean editable = property.editable();
                final String propertyDisplayName = property.displayName();

                Label lblDb = new Label(grpDatabaseSettings, SWT.NONE);
                lblDb.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                lblDb.setText(propertyDisplayName + ":  ");
                Text txtDb;
                if (propertyName.equals(DB_PASSWORD)) {
                    txtDb = new Text(grpDatabaseSettings, SWT.BORDER | SWT.PASSWORD);
                } else {
                    txtDb = new Text(grpDatabaseSettings, SWT.BORDER);
                }
                txtDb.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                txtDb.setData("displayName", propertyDisplayName);
                txtDb.setData(propertyName);
//                if (propertyName.equals(DB_DRIVER_PROP_NAME)) {
//                    txtDb.setText(DATABASE_DRIVER);
//                }
                if (props != null) {
                    final String propertyValue = props.getProperty(propertyName);
                    txtDb.setText(StringUtils.defaultString(propertyValue));
                }
                if (!editable) {
                    txtDb.setEditable(editable);
                }
            }
        }
    }

    /**
     * Open directory dialog.
     * 
     * @param shell the shell
     * @return the string
     */
    private String openDirectoryDialog(final Shell shell, final String path) {
        DirectoryDialog dirDialog = new DirectoryDialog(shell);
        dirDialog.setText("Select directory");
        dirDialog.setFilterPath(path);
        String destPath = dirDialog.open();
        if (StringUtils.isBlank(destPath)) {
            return path;
        } else {
            return destPath;
        }
    }

    /**
     * Open properties editor.
     * 
     * @param configuration the configuration
     * @param valueHeaderName
     * @param keyHeaderName
     */
    private void openPropertiesEditor(final IConfiguration configuration, String keyHeaderName, String valueHeaderName) {
        Shell shell = new Shell(getShell(), SWT.APPLICATION_MODAL | SWT.TITLE | SWT.CLOSE);
        if (configuration instanceof CsvConfiguration) {
            shell.setText("Datafile Column Mapping Editor");
        } else if (configuration instanceof GeneralConfiguration) {
            shell.setText("General Configuration Editor");
        }
        shell.setLayout(new FormLayout());
        PropertiesEditor propertiesEditor = new PropertiesEditor(shell, SWT.NONE, configuration, keyHeaderName, valueHeaderName);
        UIHelper.show(propertiesEditor);
    }

    /**
     * Save configurations.
     */
    private void saveConfigurations() {
        saveDbConfiguration();
        saveSystemConfiguration();
    }

    /**
     * Save db configuration.
     */
    private void saveDbConfiguration() {
        try {
            saveDbProperties();
        } catch (IOException ioe) {
            LOGGER.error("An error occurred while saving the database configuration.", ioe);
            MessageDialog.openError(getShell(), DIALOG_TITLE, "An error occurred while saving the database configuration.");
        }
    }

    /**
     * Save system configuration.
     */
    private void saveSystemConfiguration() {
        try {
            saveSystemProperties();
        } catch (IOException ioe) {
            LOGGER.error("An error occurred while saving the system configuration.", ioe);
            MessageDialog.openError(getShell(), DIALOG_TITLE, "An error occurred while saving the system configuration.");
        }
    }

    /**
     * Save system properties.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void saveSystemProperties() throws IOException {
        Properties systemProperties = new Properties();
        systemProperties.setProperty(WATCH_DIR_PATH, txtWatchDir.getText());
        systemProperties.setProperty(ARCHIVE_DIR_PATH, txtArchiveDir.getText());
        systemProperties.setProperty(TOGGLE_STATE, BooleanUtils.toStringTrueFalse(toggleState));
        SignalProcessorHelper.storePropertiesFile(systemProperties, new SystemConfiguration());
    }

    /**
     * Save db properties.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void saveDbProperties() throws IOException {
        Properties dbProperties = new Properties();
        Control[] controls = grpDatabaseSettings.getChildren();
        for (Control control : controls) {
            if (control.getClass().equals(Text.class)) {
                dbProperties.setProperty(control.getData().toString(), ((Text) control).getText());
            }
        }
        SignalProcessorHelper.storePropertiesFile(dbProperties, new DbConfiguration());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.ApplicationWindow#createStatusLineManager()
     */
    @Override
    protected StatusLineManager createStatusLineManager() {
        StatusLineManager statusLineManager = new StatusLineManager();
        statusLineManager.setMessage("set Messages");
        return statusLineManager;
    }

    /**
     * Launch the application.
     * 
     * @param args the arguments
     */
    public static void main(String args[]) {
        System.out.println("Running on: OS-" + System.getProperty("os.name") + " Arch-" + System.getProperty("os.arch"));
        System.out.println("Logging to location: "
                + FilenameUtils.separatorsToSystem(SignalProcessorHelper.getSystemTemporaryPath() + APPLICATION_LOG_FILE));
        Shell shell = new Shell();
        File file = new File(FileUtils.getUserDirectoryPath(), SignalProcessorConstants.APP_RUNNING_INDICATOR);
        if (file.exists()) {
            MessageDialog.openError(shell, DIALOG_TITLE, "You cannot start another instance of the processor.");
            shell.dispose();
            return;
        }
        SignalProcessorWindow window = null;
        try {
            window = new SignalProcessorWindow();
            window.setBlockOnOpen(true);
            window.createRunningIndicatorFile();
        } catch (Exception e) {
            LOGGER.error("An error occurred while starting the Signal processor", e);
            MessageDialog.openError(window.getShell(), DIALOG_TITLE, "An error occurred while starting the Signal processor \n"+e.toString());
        }
        configureUncaughtExceptionHandler(window.getShell());
        window.open();
        String errorMsg = window.validateDbConfiguration();
        if(StringUtils.isBlank(errorMsg)){
            window.updateSchema(shell, false);
        }
        Display.getCurrent().dispose();
    }

    private static void configureUncaughtExceptionHandler(final Shell shell) {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                FileUtils.deleteQuietly(new File(FileUtils.getUserDirectoryPath(),
                        SignalProcessorConstants.APP_RUNNING_INDICATOR));
                MessageDialog.openError(shell, DIALOG_TITLE, "An error occurred while starting the Signal processor \n"+e.toString());
            }
        });
    }

    private void updateSchema(Shell shell, boolean showDialog) {
        try {
            DatabaseSchemaSynchronizer.updateDatabaseSchema();
            if (showDialog) {
                MessageDialog.openInformation(shell, DIALOG_TITLE, "Schema updated successfully.");
            }
        } catch (CGHProcessorException cghe) {
            LOGGER.error("An error occurred while starting the Signal processor", cghe);
            MessageDialog.openError(shell, DIALOG_TITLE, "An error occurred while starting the Signal processor \n"+cghe.toString());
            return;
        }
    }

    private void createRunningIndicatorFile() throws IOException {
        File file = new File(FileUtils.getUserDirectoryPath(), SignalProcessorConstants.APP_RUNNING_INDICATOR);
        try {
            FileUtils.touch(file);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Configure the shell.
     * 
     * @param shell the shell
     */
    @Override
    protected void configureShell(final Shell shell) {
        int width = 0;
        int height = 0;
        if (SWT.getPlatform() == "gtk") {
            width = 710;
            height = 850;
        } else if (SWT.getPlatform() == "win32") {
            width = 500;
            height = 720;
        }
        super.configureShell(shell);
        shell.setText(DIALOG_TITLE);
        if (width > 0 && height > 0) {
            shell.setSize(width, height);
        }
        shell.setImage(SWTResourceManager.getImage(SignalProcessorWindow.class, "/images/favicon.ico"));
        UIHelper.setDialogLocation(shell);
        tray = shell.getDisplay().getSystemTray();
        item = new TrayItem(tray, SWT.NONE);
        menu = new Menu(shell, SWT.POP_UP);
        openMenuItem = new MenuItem(menu, SWT.PUSH);
        exitMenuItem = new MenuItem(menu, SWT.PUSH);
        shell.setFocus();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#setShellStyle(int)
     */
    protected void setShellStyle(int style) {
        super.setShellStyle(SWT.TITLE | SWT.CLOSE);
    }

 
}
