package net.teufel.sidplay.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.teufel.sidplay.core.domain.Sid;
import net.teufel.sidplay.core.domain.SidNode;


public class SidUtil {

	public static final String HVSC_HOME = "/Users/marcteufel/Desktop/C64Music";
	
	public static List<Sid> readSids(SidNode sidNode, String type) throws IOException {
		
		List<Sid> result = new ArrayList<>();
		
		readPaths(type).forEach(p -> {
			
			String sidFileName = p.getFileName().toString();
			String absolutePath = p.toAbsolutePath().toString();
			String path = absolutePath.replaceAll(HVSC_HOME, "");
			
			try {
				Sid sid = readMetadata(absolutePath);
				sid.setFileName(sidFileName);
				sid.setPath(path);
				sid.setFile(p.toFile());
				sid.setSonglengths(getSonglength(sidNode, sid.getPath()));
				result.add(sid);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			
		});
		
		return result;
		
	}
	
	public static List<Path> readPaths(String type) throws IOException {
		SidFileVisitor visitor = new SidFileVisitor();
		//Files.walkFileTree(Paths.get(HVSC_HOME + "/" + type), visitor);
		Files.walkFileTree(Paths.get(HVSC_HOME + "/DEMOS/S-Z"), visitor);
		System.out.println("Number of Directories:" + visitor.getDirectoryCount());
		System.out.println("Number of Files:" + visitor.getFileCount());
		System.out.println("Number of Paths in Result:" + visitor.getFileList().size());
		return visitor.getFileList();
	}
	
	public static Sid readMetadata(String filePath) throws IOException  {
		
		Path fileLocation = Paths.get(filePath);
		byte[] filedata = Files.readAllBytes(fileLocation);
		
		Sid sidMetaData = new Sid();
		
		sidMetaData.setTitle(extractString(filedata, 0x16));
		sidMetaData.setAuthor(extractString(filedata, 0x36));
		sidMetaData.setRelease(extractString(filedata, 0x56));
		sidMetaData.setNumberSubtunes(filedata[0xF]);
		
		int preferredSidModel = 6581;
		if ( (filedata[0x77]&0x30)>=0x20 ) {
			preferredSidModel = 8580;
		}
		
		sidMetaData.setPreferredModel(Integer.toString(preferredSidModel));
	
		return sidMetaData;
		
	}
	
	
	public static SidNode readSonglengths() {
		SidNode sidNode = new SidNode(0, '#');
		List<String> lines = new ArrayList<String>();
		int i = 1;
		String fileName =  HVSC_HOME + "/DOCUMENTS/Songlengths.txt";
		
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(lines::add);
			while(i < lines.size()) {
				sidNode.archive(lines.get(i).replaceAll("; ", ""), i+1, lines.get(i+1));
				i+=2;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sidNode;
		
	}
	
	public static List<String> getSonglength(SidNode sidNode, String searchValue) {
		String line = sidNode.searchInf(searchValue);
		return Arrays.asList(  ( line.split("=")[1] ).split(" ") );
	}
		
	private static String extractString(byte[] data, int start) throws UnsupportedEncodingException {
		byte[] result = getArray(new byte[0], 32) ;
        int flag=1; 
        for(int i=0; i<32; i++) { 
            if(flag != 0) {
                flag=result[i]=data[start+i]; 
            } else {
                flag=result[i]=0; 
            }
        }
        return new String(result, "ISO-8859-1");
	}
	
	private static byte[] getArray(byte[] item, int numberOfSpaces) {
	    byte[] result = new byte[item.length + numberOfSpaces];
	    Arrays.fill(result, 0, numberOfSpaces, (byte) 32);
	    System.arraycopy(item, 0, result, numberOfSpaces, item.length);        
	    return result;
	}
	
}
