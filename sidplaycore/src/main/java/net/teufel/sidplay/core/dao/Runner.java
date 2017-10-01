package net.teufel.sidplay.core.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import net.teufel.sidplay.core.domain.Sid;
import net.teufel.sidplay.core.domain.SidNode;
import net.teufel.sidplay.core.domain.Type;
import net.teufel.sidplay.util.SidUtil;

public class Runner {

	public static void main(String[] args) throws IOException {
		
		SidNode songlengths = SidUtil.readSonglengths();
		SidDaoJdbc sidDao = new SidDaoJdbc(DataSourceFactory.erzeugeDataSource());
		List<Type> types = sidDao.getTypes();
//
//		types.forEach(type -> {
//			
//			try {
//				System.out.println("START [" + type.getType() + "]: " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
//				List<Sid> sids = SidUtil.readSids(songlengths, type.getType());
//				
//				sids.forEach(sid -> {
////					System.out.println(sid.toString());
//					sidDao.insertSid(type, sid);
//				});
//				System.out.println("END [" + type.getType() + "]: " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));	
//			} catch (IOException e) {
//				System.err.println(e.getMessage());
//			}
//		});



		Type type = types.get(0);
		List<Sid> sids = SidUtil.readSids(songlengths, type.getType());
		
		sids.forEach(sid -> {
			sidDao.insertSid(type, sid);
		});
		
	}
	
	
}
