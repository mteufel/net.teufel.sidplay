package net.teufel.sidplay.core.domain;

import java.util.ArrayList;
import java.util.List;

public class SidNode {

	private char chr = ' ';
	private int depth = -1;
	private int lineNum = -1;
	private String inf = " ";
	private List<SidNode> children = new ArrayList<SidNode>();
	
	public char getChr() {
		return chr;
	}

	public void setChr(char chr) {
		this.chr = chr;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}

	public SidNode(int depth, char chr){
		this.depth = depth;
		this.chr = chr;
	}

	// returns the line number when matched
	public int searchNum(String file) {
		int ret = -1;
		if(file.length()-1==this.depth) {
			ret = lineNum;
		}
		else {
			for (int i = 0; i <  this.children.size(); i++) {
				if(file.charAt(this.depth)==this.children.get(i).getChr()) {
					ret = this.children.get(i).searchNum(file);
				}
			}
		}
		return ret;
				
	}
	
	// returns the next line after the match
	public String searchInf(String file) {
		String ret = " ";
		if(file.length()-1==this.depth) {
			ret = inf;
		}
		else {
			for (int i = 0; i <  this.children.size(); i++) {
				if(file.charAt(this.depth)==this.children.get(i).getChr()) {
					ret = this.children.get(i).searchInf(file);
				}
			}
		}
		return ret;
				
	}
	
	public void archive(String file, int line, String inf) {
		if(file.length()-1==this.depth) {
			this.lineNum = line;
			this.inf = inf;
		}
		else {
			boolean notFound = true;
			for (int i = 0; i <  this.children.size(); i++) {
				if(file.charAt(this.depth)==this.children.get(i).getChr()) {
					notFound = false;
					this.children.get(i).archive(file, line, inf);
				}
			}
			if(notFound) {
				SidNode child = new SidNode(this.depth+1,file.charAt(this.depth));
				this.children.add(child);
				child.archive(file, line, inf);
			}
		}
		
	}
}
