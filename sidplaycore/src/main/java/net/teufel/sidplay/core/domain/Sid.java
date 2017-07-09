package net.teufel.sidplay.core.domain;

import java.io.File;
import java.util.List;

public class Sid {

	private File file;
	private String path;
	private String fileName;
	private String title;
	private String author;
	private String release;
	private int numberSubtunes;
	private String preferredModel;
	private List<String> songlengths;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public int getNumberSubtunes() {
		return numberSubtunes;
	}
	public void setNumberSubtunes(int numberSubtunes) {
		this.numberSubtunes = numberSubtunes;
	}
	public String getPreferredModel() {
		return preferredModel;
	}
	public void setPreferredModel(String preferredModel) {
		this.preferredModel = preferredModel;
	}
	public List<String> getSonglengths() {
		return songlengths;
	}
	public void setSonglengths(List<String> songlengths) {
		this.songlengths = songlengths;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public File getFile() {
		return file;
	}
	
	@Override
	public String toString() {
		
		String length = "";
		int no = 1;
		for (String string : this.songlengths) {
			length = length + no + ": " + string + " \n";
			no++;
		}
		
		String result = "File: " + this.fileName + " \n" +
				        "Path: " + this.path + " \n" +
				        "Title: " + this.title  + " \n" +
				        "Author: " + this.author + " \n" +
				        "Release: " +  this.release + " \n" +
				        "Number of subtunes: " + this.numberSubtunes  + " \n" +
				        "Preferred Model: " + this.preferredModel + " \n\n" +
				        "Songlengths: " + " \n" + length +   "  ";
		return result;
	}
	
	
}
