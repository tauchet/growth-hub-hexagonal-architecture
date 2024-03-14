package com.university.notesystem.infrastructure.configuration.beans;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.logging.Logger;

import static com.university.notesystem.infrastructure.application.NoteSystemApplication.ADAPTERS_ROUTES;
import static com.university.notesystem.infrastructure.application.NoteSystemApplication.USECASES_ROUTE;

public class BeansImportSelector implements ImportSelector {


    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        String[] useCaseClasses = ScannerClasses.scannerClasses(USECASES_ROUTE);
        String[] adapterClasses = ScannerClasses.scannerClasses(ADAPTERS_ROUTES);
        String[] totalScanner = new String[useCaseClasses.length + adapterClasses.length];
        System.out.println(totalScanner.length);
        System.arraycopy(useCaseClasses, 0, totalScanner, 0, useCaseClasses.length);
        System.arraycopy(adapterClasses, 0, totalScanner, useCaseClasses.length, adapterClasses.length);
        Logger.getLogger(BeansImportSelector.class.getName()).info("Imported Beans: " + String.join("\n", totalScanner));
        return totalScanner;
    }


}
