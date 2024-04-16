package com.green.bookstore.common.file;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/*이 컨트롤러는 파일 다운로드 및 이미지 처리 작업을 수행하기 위해 HttpServletResponse 객체의 출력 스트림을 사용하여 클라이언트에게 직접 데이터를 전송하는 역할.
파일 다운로드 기능에서는 클라이언트가 요청한 파일을 찾아서 바이트 단위로 읽어와서 클라이언트에게 전송하고,
썸네일 생성 기능에서는 Thumbnails 라이브러리를 이용해 원본 이미지의 썸네일을 생성하여 응답으로 반환함.*/
@Controller
public class FileDownloadController {
    private static String CURR_IMAGE_REPO_PATH = "C:\\shopping\\file_repo";

    @RequestMapping("/download")
    protected void download(@RequestParam("fileName") String fileName, @RequestParam("goods_id") String goods_id, HttpServletResponse response) throws Exception {
        //  fileName과 goods_id라는 요청 파라미터를 받고, HttpServletResponse 객체를 이용해 클라이언트에게 응답을 보냄.

        OutputStream out = response.getOutputStream(); //클라이언트로 데이터를 보내기 위한 출력 스트림. HttpServletResponse가 제공함.
        String filePath = CURR_IMAGE_REPO_PATH + "\\" + goods_id + "\\" + fileName;
        // 저장된 파일의 전체 경로를 구성. 파일이 저장된 디렉토리의 경로, 상품 ID, 파일 이름을 조합하여 만들어짐.
        File image = new File(filePath); //지정된 경로에 있는 파일에 대한 File 객체를 생성.
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"); // 브라우저에서 공백을 '+'로 변환하는 것을 방지

        response.setHeader("Cache-Control", "no-cache"); //HTTP 응답 헤더에 캐시를 방지하기 위한 설정을 추가
        response.addHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\""); //다운로드될 파일의 이름을 설정하는 HTTP 응답 헤더를 추가
        FileInputStream in = new FileInputStream(image); //파일의 내용을 읽어들이기 위해 FileInputStream 객체를 생성
        byte[] buffer = new byte[1024 * 8]; //데이터를 담을 바이트 배열을 선언. 이 배열은 한 번에 최대 8KB의 데이터를 읽어들일 수 있음.
        while(true) { // 파일 내용 끝까지 읽어들이는 반복문.
            int count = in.read(buffer); // 파일로부터 데이터를 읽어 바이트 배열에 저장합니다. 읽어들인 데이터의 바이트 수를 반환.
            if(count==-1)
                break; //파일의 끝에 도달했다면 반복문을 종료
            out.write(buffer,0,count); //읽어들인 데이터를 클라이언트에게 전송
        }
        in.close();
        out.close();

    }

    @RequestMapping("/thumbnails.do")
    protected void thumbnails(@RequestParam("fileName") String fileName, @RequestParam("goods_id") String goods_id, HttpServletResponse response) throws Exception {
    // 썸네일을 생성할 원본 이미지 파일의 이름(fileName)과 상품 ID(goods_id)를 요청 파라미터로 받고, HttpServletResponse 객체를 통해 클라이언트에게 응답을 보냄.
        OutputStream out = response.getOutputStream();
        String filePath = CURR_IMAGE_REPO_PATH + "\\" + goods_id + "\\" + fileName;
        File image = new File(filePath);

        if(image.exists()) { //원본 이미지 파일이 실제로 존재하는지 확인
            Thumbnails.of(image).size(121, 154).outputFormat("png").toOutputStream(out);
        } //Thumbnails 라이브러리를 사용해 썸네일 이미지를 생성하고, 크기를 121x154로 설정한 후 png 포맷으로 클라이언트에게 전송
//        byte[] buffer = new byte[1024*8];
//        out.write(buffer);
        // 위의 두 줄은, 이미 png 포맷으로 클라이언트에 전송이 끝났음으로 download 메서드와 달리 필요 없음.
        out.close();
    }
}
