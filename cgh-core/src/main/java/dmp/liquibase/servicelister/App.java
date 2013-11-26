package dmp.liquibase.servicelister;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import liquibase.servicelocator.DefaultPackageScanClassResolver;
import liquibase.servicelocator.PackageScanFilter;
import liquibase.util.StringUtils;

/**
  * Hello world!
  *
  */
public class App {

    private List<String> packagesToScan = new ArrayList<String>();

    PackageScanFilter MatchAllPackagesScanFilter = new PackageScanFilter() {
        @Override
        public boolean matches(Class<?> type) {
            return true;
        }
    };

    public void run() {
        String packagesToScanSystemProp = System.getProperty("liquibase.scan.packages");
        if ((packagesToScanSystemProp != null) &&
                ((packagesToScanSystemProp = StringUtils.trimToNull(packagesToScanSystemProp)) != null)) {
            for (String value : packagesToScanSystemProp.split(",")) {
                addPackageToScan(value);
            }
        }

        if (packagesToScan.isEmpty()) {
            addPackageToScan("liquibase.change");
            addPackageToScan("liquibase.database");
            addPackageToScan("liquibase.parser");
            addPackageToScan("liquibase.precondition");
            addPackageToScan("liquibase.serializer");
            addPackageToScan("liquibase.sqlgenerator");
            addPackageToScan("liquibase.executor");
            addPackageToScan("liquibase.snapshot");
            addPackageToScan("liquibase.logging");
            addPackageToScan("liquibase.ext");
            addPackageToScan("liquibase.lockservice");
        }

        Set<Class<?>> classes =
            new DefaultPackageScanClassResolver().findByFilter(
                MatchAllPackagesScanFilter, 
                packagesToScan.toArray(new String[packagesToScan.size()])
            );

        for (Class<?> aclass : classes) {
            System.out.println(aclass.getName());
        }
    }

    void addPackageToScan(String packageName) {
        packagesToScan.add(packageName);
    }

    public static void main(String[] args) {
        new App().run();
    }

}