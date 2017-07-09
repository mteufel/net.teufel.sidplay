package net.teufel.sidplay.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SidFileVisitor implements FileVisitor<Path> {

    private List<Path> fileList= new ArrayList<Path>();
    private int fileCount = 0;
    private int directoryCount = 0;
  
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        directoryCount++;
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    		if (file.toString().endsWith(".sid")) {
    			fileList.add(file);
    		}
        fileCount++;
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getFileList() {
		return fileList;
	}

    public int getFileCount() {
		return fileCount;
	}
    
    public int getDirectoryCount() {
		return directoryCount;
	}
   
}
