package profitsoft.service;

import java.io.File;

public class FileWorker {
    protected File file;

    public void setFile(File file) {
        this.file = file;
    }
    public void setFile(String filePath) {
        this.file = new File(filePath);
    }
}
