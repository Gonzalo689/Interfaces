package org.example;

import javafx.scene.Node;
import javafx.scene.control.Tab;

import java.io.File;

public class FileTab extends Tab {
    private File file;

    public FileTab(File filee,  Node var2){
        super(filee.getName(), var2);
        this.file = filee;
    }

    public File getFile() {
        return file;
    }
}
