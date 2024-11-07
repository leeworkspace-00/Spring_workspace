package com.myaws.myapp.util;


import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;


public class UploadFileUtiles {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtiles.class);

	public static String uploadFile(String uploadPath,
									String originalName,byte[] fileData	)	
	throws Exception{
		
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() +"_"+originalName;		// 저장되는 파일 이름을 생성한다
        
		String savedPath = calcPath(uploadPath);		
		
		File target = new File(uploadPath+savedPath,savedName);


		FileCopyUtils.copy(fileData,target);
		
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		System.out.println("formatName:"+formatName);
		String uploadedFileName = null;
		
		if(MediaUtils.getMediaType(formatName) != null){
			uploadedFileName = makeThumbnail(uploadPath,savedPath, savedName);
		}else{
			uploadedFileName = makeIcon(uploadPath,savedPath,savedName);
		}
		return uploadedFileName;
	}	

	private  static String makeIcon(String uploadPath,		// 아이콘 생성해주는 메서드 
			String path,
			String fileName)throws Exception{

		String iconName = uploadPath+path+File.separator+fileName;				
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	private static String calcPath(String uploadPath){	// 경로 계산하는 메서드
		
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator+cal.get(Calendar.YEAR);// 연
		
		String monthPath = yearPath+
				File.separator +
				new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);// 월
		
		String datePath = monthPath +
				File.separator +
				new DecimalFormat("00").format(cal.get(Calendar.DATE));	// 일
		makeDir(uploadPath, yearPath, monthPath, datePath);	// 저장할때 날짜별로 저장해줌		
		return datePath;
	}
	
	private static void makeDir(String uploadPath,String...paths){					// 폴더 생성해주는 메서드 
		if(new File(uploadPath+paths[paths.length -1]).exists())
			return;		
		for(String path : paths){			
			File dirPath = new File(uploadPath + path);						
			if (! dirPath.exists()){			
				dirPath.mkdir();				
			}
		}
	}
	
	private static String makeThumbnail(String uploadPath,
			String path,
			String fileName) throws Exception{
		
		BufferedImage sourceImg = 
				ImageIO.read(new File(uploadPath+path,fileName));
		BufferedImage destImg = 
				Scalr.resize(sourceImg, 
						Scalr.Method.AUTOMATIC, 
						Scalr.Mode.FIT_TO_HEIGHT,100);
		
		String thumbnailName = 
				uploadPath + 
				path + 
				File.separator + 
				"s-"+fileName;
		

		
		File newFile = new File(thumbnailName);

		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
	
		boolean flag = ImageIO.write(destImg, formatName.toUpperCase(), newFile);

		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}	
}
