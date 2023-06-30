package com.library.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;


// 공통모듈 (File _ 업로드와 다운로드) 

public class FileUtil {
	public static void download(HttpServletRequest req, HttpServletResponse resp, String saveDirectory, String originalFilename, String saveFilename) {
		// 업로드 파일이 저장된 경로
		//String saveDirectory = "C:/upload";
		
		// 원본 파일 이름 
		/*
		 * String originalFilename = req.getParameter("oName"); 
		 * // 저장된 파일 이름 String
		 * saveFilename = req.getParameter("sName");
		 */
		
		System.out.print("originalFilename : " +originalFilename + "<br>");
		System.out.print("saveFilename : " + saveFilename + "<br>");
		
		
		try{
			 // 실제 파일을 찾기 위해 저장된 파일의 경로(saveDirectory)와 파일 이름(saveFilename)을 결합하여 File 객체를 생성
			File file = new File(saveDirectory, saveFilename);
			System.out.print("file : " + file);
		
			// 파일 입력 스트림 생성  ( 파일로부터 데이터를 읽어오기 위해 사용되는 클래스 )
			InputStream inStream = new FileInputStream(file);
			
			
			// 한글 파일명 깨짐 방지 
			String client = req.getHeader("User-Agent");
										//클라이언트가 어떤 브라우저인지를 식별하는 용도 ("User-Agent")
			if(client.indexOf("WOW64") == -1){
				originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
			}else{
				originalFilename = new String(originalFilename.getBytes("KSC5601"), "ISO-8859-1");
			}
			
			// 파일 다운로드용 응답 헤더 설정
			resp.reset();
			
			// 파일 다운로드 창을 띄우기 위한 콘텐츠 타입을 지정 
			// octet-stream은 8비트 단위의 바이너라 데이터를 의미 
			resp.setContentType("application/octet-stream");
			
			// 원본 파일 이름으로 다운로드 받을 수 있게 설정하는 부분
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + originalFilename + "\"");
			resp.setHeader("Content-Length", "" + file.length());
			
			// 출력 스트림을 초기화 해주고 나서 outputStream을 출력해줘야함 -> 이미 jsp를 열어둔 상태에서 outputStream을 하게되면 오류 발생
			// 기존 out객체를 비우고 새로 쓸 준비를 함 
			/*
			 * out.clear(); out = pageContext.pushBody(); 
			 * // response 내장 객체로부터 새로운 출력 스트림을
			 *
			 */
			
			OutputStream outStream = resp.getOutputStream();
			
			//출력 스트림에 파일 내용 출력 
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;
			 // 생성한 파일 입력 스트림에서 파일을 읽어오는데 길이가 0보다 크면 출력해 
			while((readBuffer = inStream.read(b)) > 0 ){
				outStream.write(b, 0, readBuffer);
			}
			
			// 입/출력 스트림 닫음
			inStream.close();
			outStream.close();
				
			
		}catch(FileNotFoundException e){
			JSFunction.alertBack(resp, "파일을 찾을 수 없습니다.");
		}	
		catch(Exception e){
			JSFunction.alertBack(resp, "파일 다운로드 중 오류가 발생하였습니다.");
		}
	}
	
	/*
	 * 파일업로드
	 *  (multipart/form-data 요청)처리
	 * @return
	 * */
	public static MultipartRequest uploadFile(HttpServletRequest req, String saveDirectory, int maxPostSize) {
		try {
			// 파일 업로드 
			return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8");
		} catch (IOException e) {
			// 업로드 실패 
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 파일 삭제 
	 * */
	public static void deleteFile(String saveDirectory , String filename) {
		File file = new File(saveDirectory + File.separator +  filename);
			// 파일이 존재하면 파일을 제거한다. 
		if(file.exists()) {
			file.delete();
		}
	}
	// 파일 이름을 변경하는 메서드! 
	// (파일의 경로와 파일이름을 매개변수로 받아서 파일의 이름을 변경하고 변경된 이름을 반환) 
	public static String fileNameChange(String saveDirectory, String fileName) {
		
		//  첨부파일의 확장자 
		String ext = fileName.substring(fileName.lastIndexOf("."));
		
		String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
		  				
		 // 기존파일의 확장자를 제외한 파일이름만 출력 (0부터 마지막 .까지)
		String ofileName = fileName.substring(0, fileName.lastIndexOf("."));
		
		 //  - 새로운 파일 : newFileName
		String newFileName = ofileName + "_" + now + ext;
		System.out.println("기존파일명 : " + fileName);
		System.out.println("신규파일명 : " + newFileName);
		
		File oldFile = new File(saveDirectory + File.separator + fileName);
		File newFile = new File(saveDirectory + File.separator + newFileName);
		
		oldFile.renameTo(newFile);
		
		return newFileName;
	}
	public FileUtil() {

	}

}
